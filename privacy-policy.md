# Privacy Policy for Sanatan Sadhak

**Last Updated:** August 14, 2026  
**Application Name:** Sanatan Sadhak  
**Application ID:** `com.aistudio.sanatansadhak.ksdfgh`  
**Publisher:** K. Tech Services  
**Contact Email:** support@chaloone.com

---

## 1. Introduction

**Sanatan Sadhak** is a devotional and spiritual companion application designed to support devotees in their daily spiritual practice (Sadhana), Vedic calendar calculations (Panchang), Japa chanting, meditation, scripture reading, and spiritual contemplation.

We respect your privacy. This Privacy Policy transparently describes what information we collect, how it is used, how it is stored, and the choices available to you regarding your data.

---

## 2. Information Collected Through Authentication

When you use optional account features, we authenticate you via **Google Sign-In** using Android Jetpack Credential Manager and Firebase Authentication.

When you sign in, we collect:
- **Name:** Used to personalize your devotional profile within the app.
- **Email Address:** Used as your unique account identifier for session management and cloud data synchronization.
- **Firebase User Identifier (UID):** A secure, randomized alphanumeric identifier used to associate your posts, favorites, and cloud data with your account.

*Note: You may use core features—including the Vedic Panchang, Japa Mala Counter, Meditation Timer, Scripture Library, and Devotional Audio—without signing in.*

---

## 3. Community Functionality & User-Generated Content

If you choose to participate in the community section (Satsang), we store:
- **Devotional Posts & Reflections:** Text, thoughts, and spiritual reflections you voluntarily publish.
- **Comments & Likes:** Public interactions with other devotees' posts.
- **Reports & Moderation Flags:** Information submitted when reporting inappropriate content to keep the community respectful.

*Devotional posts and comments you publish in the community are visible to other registered users of the app.*

---

## 4. Sadhana, Japa & Reading Progress Data

To help track your personal spiritual journey, the application records:
- **Japa Counts & Target Malas:** Number of chants and completed malas.
- **Meditation Sessions:** Duration and timestamps of meditation sessions.
- **Daily Sadhana Streaks & Karma Logs:** Self-reported positive actions and gratitude logs.
- **Reading Progress & Bookmarks:** Current reading chapter in scriptures (Bhagavad Gita, Upanishads, etc.) and saved favorite verses.
- **User Preferences:** Preferred language (Hindi or English), audio preferences, and theme settings.

*This data is primarily persisted locally on your device in an encrypted Room SQLite database and synchronized with your Firebase account if signed in.*

---

## 5. Location Information

The application requests access to **Approximate (Coarse) and Precise (Fine) Location**:
- **Why Location is Needed:** Vedic astrological calculations (such as Sunrise, Sunset, Tithi start/end times, and Rahu Kalam) depend strictly on local solar coordinates (latitude and longitude). Location is also used in the Temple Finder feature to show nearby pilgrimage sites and estimate geographical distance.
- **Transmission & Storage:** Location coordinates are processed **on-device** to compute local solar ephemeris values and search local temple coordinates. Your real-time location is **never sold, broadcast, or stored on our servers**.
- **Permission Control:** Location permission is entirely optional. If you decline location access, the application automatically falls back to standard regional coordinates without crashing or blocking functionality.

---

## 6. Panchang & Astronomical API Services

- **Astronomical Calculations:** The application utilizes on-device Vedic astronomical algorithms based on Lahiri Ayanamsha (Chitra Paksha) for daily Panchang computations.
- **Remote Ephemeris Sources:** Where available and enabled, ephemeris data requests may query verified Vedic astrological APIs to confirm high-precision planetary coordinates. These requests transmit only geographical coordinates and target dates without personal user identifiers.

---

## 7. Sadhak Mitra (AI Spiritual Companion)

- **AI Inquiries:** When you interact with **Sadhak Mitra**, your typed questions and prompts are transmitted securely via Google Gemini API to generate spiritual, philosophical, and reflective responses.
- **Important Scriptural Disclaimer:** Sadhak Mitra is an **AI-assisted spiritual companion** designed for contemplation and guidance. AI-generated responses **must not be regarded as authoritative scripture, formal Vedic injunctions, or living guru/acharya guidance**. For authoritative ritual procedures, please consult traditional acharyas and primary Sanskrit texts.
- **Data Safety:** Prompts submitted to Sadhak Mitra do not include personal account details.

---

## 8. Devotional Audio & Media

- **Bundled Sacred Audio:** Devotional stotras (Hanuman Chalisa, Shiva Tandav, Gayatri Mantra, Mahamrityunjaya, Vishnu Sahasranamam) and ambient sounds are packaged locally within the application or streamed from authorized public sources.
- **Audio Preferences:** Your mute/unmute state, ambient sound selections, and volume preferences are stored strictly on your local device.

---

## 9. Third-Party Service Providers (Firebase & Cloud)

We use trusted infrastructure providers to operate the app:
- **Firebase Authentication (Google LLC):** For user authentication and identity verification.
- **Cloud Firestore (Google LLC):** For storing user profiles and community discussions.
- **Google Gemini API (Google LLC):** For processing Sadhak Mitra AI conversational requests.

*All network communication is conducted over encrypted channels (HTTPS / TLS 1.3).*

---

## 10. Local Data Persistence (Room Database)

The application utilizes an on-device **Room SQLite Database** to ensure fast offline access to your Japa history, Sadhana logs, gratitude entries, and bookmarks. This data remains on your physical device until you log out or delete your account.

---

## 11. Data Sharing & Sale

- **No Sale of Data:** We **do not sell, rent, or trade** your personal information to third parties or data brokers.
- **No Third-Party Advertising Trackers:** We do not embed third-party advertising SDKs or tracking pixels for behavioral targeting.

---

## 12. Advertising Policy

**Sanatan Sadhak does not contain advertisements.** There are no banner ads, interstitial video ads, or sponsored marketing promotions within the application.

---

## 13. Security & Account Isolation

- All cloud requests use industry-standard **HTTPS/TLS encryption** in transit.
- User data in Cloud Firestore is strictly scoped to authenticated user accounts, ensuring one user cannot access or modify another user's personal Sadhana logs or private data.

---

## 14. Data Retention & Account Deletion

We retain your cloud account data only for as long as your account remains active.

You have full control over your data:
- **In-App Account Deletion:** You can delete your account at any time by navigating to **Profile → Account Settings → Delete Account**.
- **What Deletion Does:**
  1. Permanently deletes your Firebase Authentication record.
  2. Permanently deletes your user document and profile from Cloud Firestore.
  3. Purges all local Room database entries, Japa logs, favorites, and cached data on your device.
  4. Resets all app preferences and logs you out completely.

For web-based deletion requests, please refer to our [Account Deletion Information Page](account-deletion.md).

---

## 15. Children's Privacy

Sanatan Sadhak is designed for general audiences and spiritual seekers aged 13 and older. We do not knowingly collect personally identifiable information from children under 13. If you believe that a child has provided us with personal data, please contact us at `support@chaloone.com` so we can promptly remove it.

---

## 16. User Rights

Depending on your jurisdiction, you may have the right to:
- Access the personal information we hold about you.
- Request correction of inaccurate information.
- Request complete deletion of your account and associated data.
- Withdraw consent for location or notification permissions via Android system settings.

---

## 17. Changes to This Privacy Policy

We may update this Privacy Policy from time to time to reflect improvements in app functionality or regulatory updates. Any changes will be posted on this page with an updated revision date.

---

## 18. Contact Us

If you have any questions, concerns, or requests regarding this Privacy Policy or data practices, please contact us at:

**K. Tech Services**  
**Email:** `support@chaloone.com`  
**Website:** `https://sanatan-sadhak.chaloone.com`
