#!/usr/bin/env python3
"""Builds the Sanatan Sadhak website into ../public.

Edit the page bodies in website/pages/*.html, then run:  python3 website/build.py
- Shared head, header (logo, nav, EN|हिं toggle) and footer live here.
- {{motif:NAME POS}} inserts an animated line-art background (see motifs.py).
- {{UPDATED}} inserts the legal "last updated" date.
"""
import hashlib
import json
import pathlib
import re

from motifs import render as motif

ROOT = pathlib.Path(__file__).resolve().parent
PUBLIC = ROOT.parent / "public"
SITE = "https://sanatan-sadhak.chaloone.com"
UPDATED = "16 November 2026"

# (output, fragment, title, description, nav key, scripts)
PAGES = [
    ("index.html", "home.html", "Sanatan Sadhak · Daily Sadhana, Japa, Live Panchang & AI Mitra",
     "Sanatan Sadhak is a free Android companion for daily sadhana: japa counter, mantras, live Panchang with Lahiri ayanamsa, temples, Vedic library and the Mitra guide.",
     "home", ["astro", "scene", "panchang", "updates", "forms"]),
    ("features.html", "features.html", "Features · Everything inside Sanatan Sadhak",
     "Every feature of Sanatan Sadhak explained with screenshots and steps: japa, mantras, aarti bell, dhyana, sankalpa, Panchang, temples, knowledge hub, AI Mitra and more.",
     "features", []),
    ("guides.html", "guides.html", "Guides · How to use Sanatan Sadhak",
     "Step-by-step guides: getting started, setting a sankalpa, using the japa counter, reading the Panchang and asking AI Mitra.",
     "guides", []),
    ("panchang.html", "panchang.html", "Today's Panchang · Live tithi, nakshatra, Rahu Kaal",
     "Live Vedic Panchang for your city: tithi, nakshatra, yoga, karana, sunrise, sunset, Rahu Kaal and Abhijit muhurat, calculated with Lahiri ayanamsa.",
     "panchang", ["astro", "panchang"]),
    ("updates.html", "updates.html", "What's new · Sanatan Sadhak updates",
     "Release notes and highlights for every Sanatan Sadhak update.", "updates", ["updates"]),
    ("ideas.html", "ideas.html", "Ideas & feedback · Help shape Sanatan Sadhak",
     "Suggest a feature, report a bug or share feedback, and see what is planned for Sanatan Sadhak.", "ideas", ["forms", "updates"]),
    ("privacy.html", "privacy.html", "Privacy Policy · Sanatan Sadhak",
     "How the Sanatan Sadhak app and website collect, use, store and delete your data.", "", []),
    ("terms.html", "terms.html", "Terms of Use · Sanatan Sadhak",
     "The terms that apply when you use the Sanatan Sadhak app and website.", "", []),
    ("delete-account.html", "delete-account.html", "Delete Your Account & Data · Sanatan Sadhak",
     "Delete your Sanatan Sadhak account and data from the app, or request deletion online.", "", ["forms"]),
    ("contact.html", "contact.html", "Contact & Support · Sanatan Sadhak",
     "Get help with Sanatan Sadhak, report content or send feedback.", "contact", ["forms"]),
    ("404.html", "404.html", "Page not found · Sanatan Sadhak", "This page could not be found.", "", []),
]

NAV = [
    ("home", "/", "nav.home", "Home"),
    ("features", "/features", "nav.features", "Features"),
    ("guides", "/guides", "nav.guides", "Guides"),
    ("panchang", "/panchang", "nav.panchang", "Panchang"),
    ("updates", "/updates", "nav.updates", "What's new"),
    ("ideas", "/ideas", "nav.ideas", "Ideas"),
    ("contact", "/contact", "nav.contact", "Contact"),
]

SCRIPT_FILES = {
    "astro": "js/astro.js", "scene": "js/scene.js", "panchang": "js/panchang-ui.js",
    "updates": "js/updates.js",
}


def h(name):
    return hashlib.md5((PUBLIC / name).read_bytes()).hexdigest()[:8]


def jsonld(path, title, desc, url):
    org = {"@type": "Organization", "name": "K. Tech Services", "url": "https://k-tech.chaloone.com",
           "email": "support@chaloone.com", "founder": {"@type": "Person", "name": "Kunal Pareek"}}
    data = [{"@context": "https://schema.org", "@type": "WebPage", "name": title, "description": desc, "url": url,
             "isPartOf": {"@type": "WebSite", "name": "Sanatan Sadhak", "url": SITE + "/"}}]
    if path == "index.html":
        data.append({
            "@context": "https://schema.org", "@type": "MobileApplication", "name": "Sanatan Sadhak",
            "operatingSystem": "Android", "applicationCategory": "LifestyleApplication",
            "description": desc, "url": SITE + "/", "image": SITE + "/img/icon-512.png",
            "inLanguage": ["en", "hi"], "offers": {"@type": "Offer", "price": "0", "priceCurrency": "INR"},
            "publisher": org,
            "screenshot": [f"{SITE}/img/screens/{n}.webp" for n in ["home", "japa", "sadhana", "temples", "knowledge"]],
        })
    return "\n".join(f'<script type="application/ld+json">{json.dumps(d, ensure_ascii=False)}</script>' for d in data)


def head(path, title, desc, scripts):
    url = SITE + ("/" if path == "index.html" else "/" + path.replace(".html", ""))
    robots = '<meta name="robots" content="noindex">' if path == "404.html" else ""
    js = [f'<script defer src="/js/i18n.js?v={h("js/i18n.js")}"></script>',
          f'<script defer src="/js/site.js?v={h("js/site.js")}"></script>']
    for s in scripts:
        if s == "forms":
            js.append(f'<script type="module" src="/js/forms.js?v={h("js/forms.js")}"></script>')
        else:
            js.append(f'<script defer src="/{SCRIPT_FILES[s]}?v={h(SCRIPT_FILES[s])}"></script>')
    js_s = "\n  ".join(js)
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>{title}</title>
  <meta name="description" content="{desc}">
  {robots}
  <link rel="canonical" href="{url}">
  <meta name="theme-color" content="#fff8ec">
  <meta property="og:type" content="website">
  <meta property="og:site_name" content="Sanatan Sadhak">
  <meta property="og:title" content="{title}">
  <meta property="og:description" content="{desc}">
  <meta property="og:url" content="{url}">
  <meta property="og:image" content="{SITE}/img/og.png">
  <meta property="og:image:width" content="1200"><meta property="og:image:height" content="630">
  <meta property="og:locale" content="en_IN"><meta property="og:locale:alternate" content="hi_IN">
  <meta name="twitter:card" content="summary_large_image">
  <link rel="icon" type="image/png" sizes="32x32" href="/img/icon-32.png">
  <link rel="icon" type="image/png" sizes="48x48" href="/img/icon-48.png">
  <link rel="apple-touch-icon" href="/img/icon-192.png">
  <link rel="manifest" href="/site.webmanifest">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Inter:wght@400;500;600;700;800&family=Noto+Sans+Devanagari:wght@400;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="/styles.css?v={h("styles.css")}">
  {js_s}
  {jsonld(path, title, desc, url)}
</head>"""


def header(active):
    items = []
    for key, href, i18n, label in NAV:
        cur = ' aria-current="page"' if key == active else ""
        items.append(f'<li><a href="{href}"{cur} data-i18n="{i18n}">{label}</a></li>')
    return f"""<body>
<a class="skip" href="#main">Skip to content</a>
<div class="bg-global" aria-hidden="true">
  <svg class="om" viewBox="0 0 200 200"><circle cx="100" cy="100" r="96" fill="none" stroke="#f28c00" stroke-width="1.5"/><circle cx="100" cy="100" r="82" fill="none" stroke="#f5b301" stroke-width="1.2" stroke-dasharray="3 7"/><text x="100" y="140" text-anchor="middle" font-size="120" font-family="Noto Sans Devanagari, sans-serif" fill="none" stroke="#e65100" stroke-width="1.6">ॐ</text></svg>
</div>
<header class="nav">
  <div class="wrap">
    <a class="brand" href="/"><span class="logo"><img src="/img/icon-48.png" alt="" width="42" height="42"><span class="flame"></span></span><span>Sanatan Sadhak<small>सनातन · साधना · संस्कृति</small></span></a>
    <button class="menu-btn" aria-label="Open menu" aria-expanded="false" aria-controls="nav-links">☰</button>
    <ul class="nav-links" id="nav-links">
      {''.join(items)}
      <li><span class="lang" role="group" aria-label="Language"><button type="button" data-lang="en" aria-pressed="true">EN</button><button type="button" data-lang="hi" aria-pressed="false">हिं</button></span></li>
    </ul>
  </div>
</header>
<main id="main">"""


FOOTER = """</main>
<footer>
  <div class="wrap">
    <div class="cols">
      <div>
        <a class="brand" href="/"><span class="logo"><img src="/img/icon-48.png" alt="" width="42" height="42" loading="lazy"><span class="flame"></span></span><span>Sanatan Sadhak<small>सनातन · साधना · संस्कृति</small></span></a>
        <p style="margin-top:12px;max-width:340px" data-i18n="foot.about">A calm companion for daily sadhana, made with devotion in Rajasthan, India by K. Tech Services.</p>
      </div>
      <div>
        <h4 data-i18n="foot.app">App</h4>
        <ul>
          <li><a href="/features" data-i18n="nav.features">Features</a></li>
          <li><a href="/guides" data-i18n="nav.guides">Guides</a></li>
          <li><a href="/panchang" data-i18n="nav.panchang">Panchang</a></li>
          <li><a href="/updates" data-i18n="nav.updates">What's new</a></li>
          <li><a href="/#early-access" data-i18n="foot.early">Early access</a></li>
        </ul>
      </div>
      <div>
        <h4 data-i18n="foot.help">Help & legal</h4>
        <ul>
          <li><a href="/ideas" data-i18n="nav.ideas">Ideas & feedback</a></li>
          <li><a href="/contact" data-i18n="nav.contact">Contact</a></li>
          <li><a href="/privacy" data-i18n="foot.privacy">Privacy policy</a></li>
          <li><a href="/terms" data-i18n="foot.terms">Terms of use</a></li>
          <li><a href="/delete-account" data-i18n="foot.delete">Delete account</a></li>
        </ul>
      </div>
      <div>
        <h4 data-i18n="foot.maker">Made by</h4>
        <ul>
          <li><a href="https://k-tech.chaloone.com" rel="noopener">K. Tech Services</a></li>
          <li><a href="https://k-tech.chaloone.com/#contact" rel="noopener" data-i18n="foot.build">Build an app like this</a></li>
          <li><a href="mailto:support@chaloone.com">support@chaloone.com</a></li>
        </ul>
      </div>
    </div>
    <div class="bottom">
      <span>© <span data-year>2026</span> K. Tech Services by Kunal Pareek · Udyam-registered MSME, India</span>
      <span class="deva">॥ सर्वे भवन्तु सुखिनः ॥</span>
    </div>
  </div>
</footer>
</body>
</html>
"""


def expand(body):
    body = body.replace("{{UPDATED}}", UPDATED)
    return re.sub(r"\{\{motif:(\w+)(?: (\w+))?\}\}", lambda m: motif(m.group(1), m.group(2) or "right"), body)


def build():
    for out, src, title, desc, nav, scripts in PAGES:
        body = expand((ROOT / "pages" / src).read_text(encoding="utf-8"))
        html = head(out, title, desc, scripts) + "\n" + header(nav) + "\n" + body + "\n" + FOOTER
        (PUBLIC / out).write_text(html, encoding="utf-8")
        print("built", out)

    urls = ["/", "/features", "/guides", "/panchang", "/updates", "/ideas", "/contact", "/privacy", "/terms", "/delete-account"]
    sm = ['<?xml version="1.0" encoding="UTF-8"?>', '<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">']
    sm += [f"  <url><loc>{SITE}{u}</loc></url>" for u in urls]
    sm.append("</urlset>")
    (PUBLIC / "sitemap.xml").write_text("\n".join(sm) + "\n")
    (PUBLIC / "robots.txt").write_text(f"User-agent: *\nAllow: /\nSitemap: {SITE}/sitemap.xml\n")


if __name__ == "__main__":
    build()
