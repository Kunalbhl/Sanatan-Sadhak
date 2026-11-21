#!/usr/bin/env python3
"""Generates pages/features.html from the FEATURES table (run before build.py)."""
import html
import pathlib

ROOT = pathlib.Path(__file__).resolve().parent

# id, icon, title, tag, summary, steps, screen (webp name) or mock key, motif
FEATURES = [
    ("home", "🏠", "Home & daily sadhana", "Every day",
     "Your day starts with a greeting, today's date, the Panchang at a glance, quick actions and the shloka of the day.",
     ["Open the app. The banner greets you with the deity of the day.", "Read today's tithi, nakshatra, sunrise, sunset and Rahu Kaal in the Panchang card.",
      "Tap a Quick Action: Japa Mala, Dhyan, My Mantra, Aarti or Chants.", "Scroll to Daily Shloka & Wisdom to read the meaning or tap to listen."], "home", "om"),
    ("japa", "📿", "Japa & mantra counter", "Core tool",
     "A big, calm tap target for counting chants, with mala tracking, daily targets and streaks.",
     ["Open Sadhana → Bhakti Tools → Mantra Counter.", "Choose a mantra from Select Mantra. Its Sanskrit text appears above the counter.",
      "Tap the circle for each chant. There's a soft vibration at every 108.", "Switch to Japa Timer to chant for a set time instead.", "Your mala count and day streak (🔥) update automatically."], "japa", "mala"),
    ("mantras", "🎶", "Mantra library, My Mantras & chant player", "Listen & chant",
     "Classical chants with lyrics and meaning. Add your own mantras, keep a calm, seamless Om chant playing in the background, and open a mantra's recitation on YouTube.",
     ["Open Sadhana → Prayers and search for any chalisa, aarti or stotram.", "Open a chant to read its lyrics and meaning and press play.",
      "Use the audio queue to line up several tracks.", "Tap Add Personal Prayer to save your own mantra in My Mantras.", "Turn on Global Background Chant in Profile → App Preferences for ambient Om."], "sadhana", "lotus"),
    ("aarti", "🔔", "Aarti bell", "Ritual",
     "Ring a sacred temple bell during your aarti. It sounds and vibrates, and every ring is logged.",
     ["Open Bhakti Tools → Aarti Bell.", "Tap the bell to ring it during your aarti.", "See your ring history in Bell Ring Logs, and clear it any time."], "mock:bell", "temple"),
    ("dhyana", "🧘", "Dhyana meditation", "Peace",
     "A breath and meditation timer with ambient Om, flute or temple bells, and goals to keep you consistent.",
     ["Open Sadhana → Dhyana Sadhana.", "Pick a duration and an ambient sound, and set the volume.", "Press Start. The screen goes calm and focused.", "Finish to log the session towards your meditation goal."], "mock:dhyana", "lotus"),
    ("sankalpa", "🙏", "Sankalpa & Gayatri Anushthan", "Commitment",
     "Take a vow (sankalpa) with a target and duration, follow guided anushthan, and track each day.",
     ["Open Sadhana → Dashboard → Active Sankalpa.", "Choose a sankalpa, for example 11 malas a day for 40 days, or Gayatri Anushthan.",
      "Log each day's practice. Progress and streak show on the dashboard.", "Complete it, or start a new one when you're ready."], "mock:sankalpa", "devi"),
    ("karma", "🌱", "Karma & vow tracker, gratitude, journal", "Reflect",
     "Log good deeds and seva, write daily gratitude, and keep a personal spiritual journal. Karma points reward consistency.",
     ["Open Bhakti Tools → Karma Tracker and tap Log Deed.", "Open Gratitude and write three things you're thankful for.",
      "Use the Personal Spiritual Journal for longer reflections.", "Watch Karma Points and the Karma Streak grow on Home and Profile."], "mock:karma", "lotus"),
    ("pooja", "🪔", "Pooja vidhi & prayers", "Guidance",
     "Step-by-step puja procedures with samagri lists, plus lyrics for aartis, chalisas and stotras.",
     ["Open Knowledge → Pooja & Vidhi, or a deity page.", "Read the samagri list and gather the items.", "Follow each numbered step of the vidhi.", "Open Prayer Lyrics to sing along."], "mock:pooja", "temple"),
    ("panchang", "🗓️", "Panchang & Hindi calendar", "Live",
     "Every tithi, nakshatra, yoga and karana that falls in the day, each with its start and end time, plus sunrise and sunset, Rahu Kaal, Yamaganda, Gulika, Abhijit, Brahma Muhurta, special yogas, Choghadiya, festivals and named Ekadashis for your location. It's calculated in the sidereal zodiac with Lahiri ayanamsa.",
     ["Tap the calendar icon at the top, or View Full Panchang on Home.", "Set your city or use GPS (Current GPS / Custom Location).",
      "Read the Detailed Panchang. When a day holds two tithis you see both, with from and until times.", "Check the Muhurtas, special yogas and the Day Choghadiya.", "Open the Monthly Calendar for festivals and vrats such as Ekadashi.",
      "You can also try the <a href=\"/panchang\">live Panchang on this website</a>."], "mock:panchang", "panchang"),
    ("knowledge", "📜", "Knowledge hub / Vedic library", "Learn",
     "Hundreds of texts on the Vedas, Upanishads, Itihasa, deities, festivals, temples, saints, yoga and puja, in English and Hindi. New and updated pages are picked up automatically each day, and every article links to its source (Wikipedia, CC BY-SA, or an approved contributor).",
     ["Open Knowledge from the bottom bar.", "Filter by category (including Deities, Festivals, Temples and Saints) or by sacred deity.", "Sort by difficulty, format or newest.",
      "Read an article and bookmark it. Your reading progress is saved."], "knowledge", "scripture"),
    ("temples", "🛕", "Temple finder", "Darshan",
     "A sacred mandir directory: the 12 Jyotirlingas, Char Dham and city temples, with distance, story, directions and sharing.",
     ["Open Bhakti Tools → Temple Finder.", "Pick a reference city (Varanasi, Vrindavan, Ayodhya, Haridwar) or use your location.",
      "Filter by deity or search by name.", "Open a temple for its story, then tap Directions or Share."], "temples", "mahakaal"),
    ("mitra", "✨", "AI Mitra", "Ask",
     "A gentle AI companion for questions on granths, rituals, deities and dharma. Answers are short, with something to try today and links into the app.",
     ["Tap Ask AI Mitra on Home or open Chat.", "Pick a suggested prompt or type your question in English or Hindi.",
      "Tap the chips under an answer to jump to Panchang, Knowledge or Mantras.", "Mitra is an AI: for rituals and important decisions, consult a qualified acharya."], "mock:mitra", "ganesha"),
    ("community", "👥", "Community", "Satsang",
     "Share reflections, like and comment, and report anything inappropriate. A respectful space for sadhaks.",
     ["Sign in, then open Community.", "Write a post or reflection and publish it.", "Like and comment on others' posts.", "Use Report on any post that breaks the community rules."], "mock:community", "nandi"),
    ("videos", "▶️", "Videos", "Watch",
     "A sacred video hub of darshan, aarti, katha and festival specials from curated YouTube channels, with favourites.",
     ["Open Videos from the bottom bar.", "Browse All Videos, Festival Specials or Favorites, or search.", "Tap a video to play it in the app.", "Save the ones you love to Favorites."], "videos", "krishna"),
    ("widget", "🔔", "Home-screen widget & festival alerts", "Stay on time",
     "A Panchang widget for your home screen and gentle reminders the evening before and the morning of Ekadashi, Pradosh, Purnima, Amavasya, Sankashti and major festivals. Dates come from the same Panchang engine as the app.",
     ["Long-press your home screen, choose Widgets and add Sanatan Sadhak's Panchang.", "Set your city once in the app's Panchang so alerts use your sunrise.",
      "Open Profile → Notifications to switch festival alerts on or off.", "Alerts say when a date may differ by a day in your region."], "mock:updates", "panchang"),
    ("contribute", "✍️", "Contribute & review", "Community knowledge",
     "Trusted devotees can add articles to the Knowledge hub. Every submission stays private until a Moderator or admin approves it, and only approved articles reach other users.",
     ["Open Profile → Contribute & Review and request access.", "An administrator grants the Contributor or Moderator role. Roles are never self-assigned.",
      "Contributors write an article with a source link and submit it.", "Moderators approve or reject it with a note. You cannot approve your own submission.", "Approved articles appear in Knowledge for everyone, labelled as community contributions."], "mock:community", "lotus"),
    ("updates", "📣", "Divine updates", "Stay informed",
     "Festival posts, announcements and new-content alerts in one feed, with in-app notifications.",
     ["Tap the bell icon at the top to see alerts.", "Mark alerts as read, or clear them all.", "Open Divine Updates for the full feed of posts."], "mock:updates", "hanuman"),
    ("profile", "🌗", "Profile, language & themes", "Personal",
     "Your devotee profile, stats and settings: English or Hindi, light or dark, notifications and account controls.",
     ["Open Profile from the bottom bar and sign in.", "Tap EN | हि at the top to switch language at any time.",
      "Open App Preferences & Theme for light or dark mode, sounds and the background chant.", "Edit your name and photo, or delete your account."], "profile", "om"),
    ("guest", "🕊️", "Guest mode", "No account needed",
     "Use Japa, Panchang, meditation, the library, temples and audio without signing in. Sign in later to sync.",
     ["Open the app and tap Not Now on the sign-in prompt.", "Use any tool. Your progress stays on this phone.",
      "Community and Mitra will ask you to sign in.", "Sign in later to sync your progress."], "profile", "lotus"),
]

MOCKS = {
    "bell": '<div class="mbar">🔔 Aarti Bell</div><div style="font-size:5rem;text-align:center;margin:20px 0" class="m-swing">🔔</div><div class="mcard"><b>Bell ring logs</b>108 rings today · last at 6:42 AM</div><div class="mcard"><b>Tip</b>Ring at the start and end of aarti</div>',
    "dhyana": '<div class="mbar">🧘 Dhyana Sadhana</div><div style="margin:18px auto;width:150px;height:150px;border-radius:50%;display:grid;place-items:center;background:radial-gradient(circle,#fff1d6,#ffd28a);font-size:2rem;font-weight:800;color:#7a1c1c">10:00</div><div class="mcard"><b>Ambient</b>🕉️ Om · 🎶 Flute · 🔔 Bells</div><div class="mcard"><b>Goal</b>15 min daily · 4 day streak</div>',
    "sankalpa": '<div class="mbar">🙏 Active Sankalpa</div><div class="mcard"><b>Gayatri Anushthan</b>24,000 japa · 40 days</div><div class="mcard"><b>Today</b>11 / 11 malas ✓</div><div class="mcard"><b>Progress</b><div style="height:8px;border-radius:8px;background:#fde1b0;margin-top:6px"><div style="width:62%;height:100%;border-radius:8px;background:linear-gradient(90deg,#f28c00,#e65100)"></div></div>Day 25 of 40</div>',
    "karma": '<div class="mbar">🌱 Karma Tracker</div><div class="mcard"><b>Karma points</b>✨ 340 · 🔥 12 day streak</div><div class="mcard"><b>Log deed</b>Fed a cow · Helped a neighbour</div><div class="mcard"><b>Gratitude</b>Family, health, satsang</div>',
    "pooja": '<div class="mbar">🪔 Pooja Vidhi</div><div class="mcard"><b>Samagri</b>Diya · Ghee · Kumkum · Akshat · Flowers</div><div class="mcard"><b>Step 1</b>Achamana &amp; sankalpa</div><div class="mcard"><b>Step 2</b>Ganesh vandana</div><div class="mcard"><b>Step 3</b>Panchopachara puja</div>',
    "panchang": '<div class="mbar">🗓️ Detailed Panchang</div><div class="mcard"><b>Tithi</b>Saptami · Shukla</div><div class="mcard"><b>Nakshatra</b>Shravana · Pada 2</div><div class="mcard"><b>Rahu Kaal</b>8:04 – 9:24 AM</div><div class="mcard"><b>Choghadiya</b>Amrita · Kala · Shubha …</div>',
    "mitra": '<div class="mbar">✨ AI Mitra</div><div class="bubble me">What is Nishkama Karma?</div><div class="bubble">Doing your duty without clinging to its result, as the Gita teaches (2.47). <b>Try today:</b> do one kind act expecting nothing back.</div><div class="mcard" style="text-align:center">📜 Read Bhagavad Gita</div>',
    "community": '<div class="mbar">👥 Community</div><div class="mcard"><b>Radha · 2h</b>Completed my first 40-day sankalpa 🙏</div><div class="mcard"><b>Arjun · 5h</b>Kartik deepdaan at the ghat today 🪔</div><div class="mcard">♥ 24 · 💬 6 · Report</div>',
    "updates": '<div class="mbar">📣 Divine Updates</div><div class="mcard"><b>Festival</b>Dev Uthani Ekadashi: vrat timings</div><div class="mcard"><b>New</b>Hanuman Chalisa audio added</div><div class="mcard"><b>Darshan</b>Live aarti video added</div>',
}


def shot(key, title):
    if key.startswith("mock:"):
        return f'<div class="mock" role="img" aria-label="{html.escape(title)} screen preview">{MOCKS[key[5:]]}</div>'
    return f'<div class="phone"><img src="/img/screens/{key}.webp" alt="{html.escape(title)} screen in Sanatan Sadhak" width="317" height="677" loading="lazy"></div>'


def main():
    nav = "".join(f'<a class="chip" href="#{fid}">{icon} {html.escape(t.split(" & ")[0].split(",")[0])}</a>' for fid, icon, t, *_ in FEATURES)
    out = [f'''<section class="page-hero">
  {{{{motif:om right}}}}
  <div class="wrap"><span class="eyebrow">{len(FEATURES)} features · step-by-step walkthroughs</span>
    <h1 data-i18n="page.features">Every feature of Sanatan Sadhak</h1>
    <p class="lead" data-i18n="page.features.sub">Every option, screen and process, explained step by step.</p></div>
</section>
<nav class="feature-nav" aria-label="Jump to feature"><div class="wrap">{nav}</div></nav>
<div class="wrap">''']
    for fid, icon, title, tag, summary, steps, key, mot in FEATURES:
        pos = "left" if len(out) % 2 else "right"
        out.append(f'''<article class="feature reveal" id="{fid}">
  {{{{motif:{mot} {pos}}}}}
  <div><span class="badge">{html.escape(tag)}</span>
    <h2 style="margin-top:10px">{icon} {html.escape(title)}</h2>
    <p class="lead">{html.escape(summary)}</p>
    <ol class="steps-list">{''.join(f"<li>{s}</li>" for s in steps)}</ol>
  </div>
  <div class="shot">{shot(key, title)}</div>
</article>''')
    out.append('''</div>
<section><div class="wrap"><div class="band reveal"><div><h2>Want to see it step by step?</h2><p>Follow our guides with numbered screenshots.</p></div><div><a class="btn btn-ghost" href="/guides">Open the guides</a></div></div></div></section>''')
    (ROOT / "pages" / "features.html").write_text("\n".join(out), encoding="utf-8")
    print("wrote pages/features.html")


if __name__ == "__main__":
    main()
