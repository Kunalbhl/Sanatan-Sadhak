# Sanatan Sadhak

Android app for daily Sanatan Dharma practice: Panchang, Japa and mantras, meditation, the Vedic library, temple darshan, AI Mitra, community and videos.

## Setup
1. Open the project in Android Studio (JDK 17+ and Android SDK 36).
2. Copy `.env.example` to `.env` and fill in `GEMINI_API_KEY`, `YOUTUBE_API_KEY` and `PANCHANG_API_KEY`. The app still runs without them, using offline fallbacks.
3. Add `app/google-services.json` from the Firebase console to turn on login, community sync and cloud data. Without it, the app runs in local-only mode.
4. Run the `app` configuration.

## Structure
- `app/src/main/java/com/example/`
  - `MainActivity.kt`: the app shell (top bar, bottom bar, screen switching, notifications)
  - `ui/screens/`: one folder per feature (home, panchang, sadhana, knowledge, community, video, profile, admin)
  - `ui/viewmodel/`: ViewModels. `SadhakViewModel` owns navigation (`setScreen`, `navigateBack`).
  - `ui/designsystem/`, `ui/theme/`: shared components and themes
  - `data/`: repositories, Room database, Firebase, Gemini, YouTube and Panchang services
  - `workers/`: daily notification worker
- `public/`: Firebase Hosting site (privacy policy)
- `firestore.rules`: Firestore security rules

## Screen names
`setScreen()` accepts: Home, Knowledge, Community, Bhakti, BhaktiTools, Videos, InstagramUpdates, Chat, Calendar, Profile, Admin. Loose aliases also work (for example "Panchang", "Counter" or "Bell"). Unknown names are ignored, so they never open a blank page.
