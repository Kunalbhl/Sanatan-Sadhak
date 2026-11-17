"""Email every new website/app submission (ideas, contact, early access, deletion) to support@chaloone.com.

Runs from GitHub Actions every 15 minutes. Reads Firestore with the deployer service account,
sends one email per new document via Gmail SMTP, then marks it `notified: true` so it's never sent twice.
"""
import json, os, smtplib, subprocess, sys, urllib.request
from email.message import EmailMessage

PROJECT = "sanatan-sadhak"
BASE = f"https://firestore.googleapis.com/v1/projects/{PROJECT}/databases/(default)/documents"
COLLECTIONS = {"suggestions": "Idea / feedback", "contact_messages": "Contact message",
               "early_access": "Early access sign-up", "deletion_requests": "Account deletion request"}
TO = os.environ.get("NOTIFY_TO", "support@chaloone.com")
TOKEN = subprocess.check_output(["gcloud", "auth", "print-access-token"], text=True).strip()


def call(url, method="GET", body=None):
    req = urllib.request.Request(url, method=method, data=json.dumps(body).encode() if body else None,
                                 headers={"Authorization": f"Bearer {TOKEN}", "Content-Type": "application/json"})
    with urllib.request.urlopen(req) as r:
        return json.load(r)


def val(v):
    for k in ("stringValue", "timestampValue", "integerValue", "doubleValue"):
        if k in v: return str(v[k])
    if "booleanValue" in v: return "Yes" if v["booleanValue"] else "No"
    if "mapValue" in v: return json.dumps({k: val(x) for k, x in v["mapValue"].get("fields", {}).items()}, ensure_ascii=False)
    if "arrayValue" in v: return ", ".join(val(x) for x in v["arrayValue"].get("values", []))
    return ""


smtp = smtplib.SMTP_SSL("smtp.gmail.com", 465)
smtp.login(os.environ["SMTP_USER"], os.environ["SMTP_PASSWORD"])
sent = 0
for col, label in COLLECTIONS.items():
    page = ""
    while True:
        data = call(f"{BASE}/{col}?pageSize=200" + (f"&pageToken={page}" if page else ""))
        for doc in data.get("documents", []):
            f = {k: val(v) for k, v in doc.get("fields", {}).items()}
            if f.get("notified") == "Yes":
                continue
            msg = EmailMessage()
            who = f.get("name") or f.get("email") or "Someone"
            msg["Subject"] = f"[Sanatan Sadhak] {label} from {who}"
            msg["From"] = os.environ["SMTP_USER"]
            msg["To"] = TO
            if "@" in f.get("email", ""):
                msg["Reply-To"] = f["email"]
            first = ["type", "screen", "subject", "name", "email", "phone", "message", "reason", "consent", "createdAt", "source", "page"]
            lines = [f"{k}: {f[k]}" for k in first if k in f] + [f"{k}: {v}" for k, v in f.items() if k not in first]
            msg.set_content(f"New {label.lower()} on Sanatan Sadhak\n\n" + "\n".join(lines) +
                            f"\n\nFirestore: {col}/{doc['name'].rsplit('/', 1)[1]}")
            smtp.send_message(msg)
            call(f"https://firestore.googleapis.com/v1/{doc['name']}?updateMask.fieldPaths=notified", "PATCH",
                 {"fields": {"notified": {"booleanValue": True}}})
            sent += 1
        page = data.get("nextPageToken")
        if not page:
            break
smtp.quit()
print(f"Sent {sent} notification(s)")
