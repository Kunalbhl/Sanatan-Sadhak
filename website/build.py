#!/usr/bin/env python3
"""Builds the Sanatan Sadhak website into ../public.

Edit the page bodies in website/pages/*.html (plain HTML fragments) and the
PAGES table below, then run:  python3 website/build.py
Shared head, header and footer live here so every page stays consistent.
"""
import hashlib
import pathlib

ROOT = pathlib.Path(__file__).resolve().parent
PUBLIC = ROOT.parent / "public"
SITE = "https://sanatan-sadhak.chaloone.com"
UPDATED = "16 November 2026"

# (output file, source fragment, title, description, nav key, uses forms.js)
PAGES = [
    ("index.html", "home.html", "Sanatan Sadhak · Daily Sadhana, Japa, Panchang & Mitra",
     "Sanatan Sadhak is a calm Android companion for daily sadhana: japa counter, mantras, Panchang, temple darshan, Vedic library and the Mitra guide.",
     "home", True),
    ("privacy.html", "privacy.html", "Privacy Policy · Sanatan Sadhak",
     "How the Sanatan Sadhak app and website collect, use, store and delete your data.", "privacy", False),
    ("terms.html", "terms.html", "Terms of Use · Sanatan Sadhak",
     "The terms that apply when you use the Sanatan Sadhak app and website.", "terms", False),
    ("delete-account.html", "delete-account.html", "Delete Your Account & Data · Sanatan Sadhak",
     "Delete your Sanatan Sadhak account and data from the app, or request deletion online.", "delete", True),
    ("contact.html", "contact.html", "Contact & Support · Sanatan Sadhak",
     "Get help with Sanatan Sadhak, report content or send feedback.", "contact", True),
    ("404.html", "404.html", "Page not found · Sanatan Sadhak", "This page could not be found.", "", False),
]

NAV = [
    ("home", "/#features", "Features"),
    ("home2", "/#early-access", "Early access"),
    ("privacy", "/privacy", "Privacy"),
    ("terms", "/terms", "Terms"),
    ("contact", "/contact", "Contact"),
]


def asset_hash(name):
    return hashlib.md5((PUBLIC / name).read_bytes()).hexdigest()[:8]


def head(path, title, desc, forms):
    url = SITE + ("/" if path == "index.html" else "/" + path.replace(".html", ""))
    css = asset_hash("styles.css")
    js = asset_hash("js/site.js")
    forms_tag = f'\n  <script type="module" src="/js/forms.js?v={asset_hash("js/forms.js")}"></script>' if forms else ""
    robots = '<meta name="robots" content="noindex">' if path == "404.html" else ""
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>{title}</title>
  <meta name="description" content="{desc}">
  {robots}
  <link rel="canonical" href="{url}">
  <meta name="theme-color" content="#0b0606">
  <meta property="og:type" content="website">
  <meta property="og:site_name" content="Sanatan Sadhak">
  <meta property="og:title" content="{title}">
  <meta property="og:description" content="{desc}">
  <meta property="og:url" content="{url}">
  <meta property="og:image" content="{SITE}/img/icon-512.png">
  <meta property="og:locale" content="en_IN">
  <meta name="twitter:card" content="summary">
  <link rel="icon" type="image/png" sizes="32x32" href="/img/icon-32.png">
  <link rel="icon" type="image/png" sizes="48x48" href="/img/icon-48.png">
  <link rel="apple-touch-icon" href="/img/icon-192.png">
  <link rel="manifest" href="/site.webmanifest">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="/styles.css?v={css}">
  <script defer src="/js/site.js?v={js}"></script>{forms_tag}
</head>"""


def header(active):
    items = []
    for key, href, label in NAV:
        cur = ' aria-current="page"' if key == active else ""
        items.append(f'<li><a href="{href}"{cur}>{label}</a></li>')
    return f"""<body>
<div class="glow" aria-hidden="true"></div>
<a class="hp" href="#main">Skip to content</a>
<header class="nav">
  <div class="wrap">
    <a class="brand" href="/"><img src="/img/icon-48.png" alt="" width="34" height="34">Sanatan Sadhak</a>
    <button class="menu-btn" aria-label="Open menu" aria-expanded="false" aria-controls="nav-links">☰</button>
    <ul class="nav-links" id="nav-links">
      {''.join(items)}
      <li><a class="btn btn-primary btn-sm" href="/#early-access">Get early access</a></li>
    </ul>
  </div>
</header>
<main id="main">"""


FOOTER = """</main>
<footer>
  <div class="wrap">
    <div class="cols">
      <div>
        <a class="brand" href="/"><img src="/img/icon-48.png" alt="" width="34" height="34">Sanatan Sadhak</a>
        <p style="margin-top:12px;max-width:320px">A calm companion for daily sadhana, made with devotion in Rajasthan, India by K. Tech Services.</p>
      </div>
      <div>
        <h4>App</h4>
        <ul>
          <li><a href="/#features">Features</a></li>
          <li><a href="/#how">How it works</a></li>
          <li><a href="/#early-access">Early access</a></li>
          <li><a href="/#faq">FAQ</a></li>
        </ul>
      </div>
      <div>
        <h4>Legal</h4>
        <ul>
          <li><a href="/privacy">Privacy policy</a></li>
          <li><a href="/terms">Terms of use</a></li>
          <li><a href="/delete-account">Delete account</a></li>
        </ul>
      </div>
      <div>
        <h4>Support</h4>
        <ul>
          <li><a href="/contact">Contact us</a></li>
          <li><a href="mailto:support@chaloone.com">support@chaloone.com</a></li>
          <li><a href="https://k-tech.chaloone.com" rel="noopener">K. Tech Services</a></li>
        </ul>
      </div>
    </div>
    <div class="bottom">
      <span>© <span data-year>2026</span> K. Tech Services by Kunal Pareek. All rights reserved.</span>
      <span>Udyam-registered MSME · India</span>
    </div>
  </div>
</footer>
</body>
</html>
"""


def build():
    for out, src, title, desc, nav, forms in PAGES:
        body = (ROOT / "pages" / src).read_text(encoding="utf-8").replace("{{UPDATED}}", UPDATED)
        html = head(out, title, desc, forms) + "\n" + header(nav) + "\n" + body + "\n" + FOOTER
        (PUBLIC / out).write_text(html, encoding="utf-8")
        print("built", out)

    urls = ["/", "/privacy", "/terms", "/delete-account", "/contact"]
    sitemap = ['<?xml version="1.0" encoding="UTF-8"?>',
               '<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">']
    sitemap += [f"  <url><loc>{SITE}{u}</loc></url>" for u in urls]
    sitemap.append("</urlset>")
    (PUBLIC / "sitemap.xml").write_text("\n".join(sitemap) + "\n")
    (PUBLIC / "robots.txt").write_text(f"User-agent: *\nAllow: /\nSitemap: {SITE}/sitemap.xml\n")


if __name__ == "__main__":
    build()
