
## Website (sanatan-sadhak.chaloone.com)
- Source: `website/pages/*.html` (page bodies) + `website/build.py` (shared head/header/footer, sitemap). Assets in `public/`.
- Build: `python3 website/build.py` → writes `public/*.html`.
- Forms (early access, contact, account deletion) save to Cloud Firestore collections `early_access`, `contact_messages`, `deletion_requests`; rules in `firestore.rules` allow create-only.
- Deploy: `firebase deploy --only hosting,firestore:rules --project sanatan-sadhak`
