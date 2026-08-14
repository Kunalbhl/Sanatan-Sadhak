# Security Documentation

## Authentication
- Primary authentication: Firebase Authentication (Email/Password).
- Offline fallback: Room Database stores the user profile locally.

## Authorization
- Roles: `Guest`, `Regular`, `SuperUser`.
- `SuperUser` role provides administrative privileges.
- Local authorization checks the user's role before performing admin actions in the UI.
- Firestore Security Rules enforce backend authorization, ensuring that a user cannot modify another user's post, and that only Admins can perform admin-level writes.

## Firestore Structure
- `/users/{uid}`: Stores user profile data. `role` field determines administrative access.
- `/community_posts/{postId}`: Community posts created by users.
- `/community_comments/{commentId}`: Comments on community posts.

## Security Rules
- `firestore.rules` enforces that users can only create and update their own data.
- Users cannot modify the `role` field on their own `/users/{uid}` document.
- Only Admins (`SuperUser`) can delete posts created by other users.

## Data Ownership
- Community posts and comments belong to the author.
- User profiles belong to the authenticated user.

## Offline Behavior & Sync
- Room database is the primary local cache for offline-first behavior.
- Auth falls back to Room if Firebase is unavailable due to network issues.
- `CommunityRepository` uses Firebase as the primary remote source when online, while still persisting to Room.

## Privacy Boundaries
- Private Sadhana data, Japa history, and Journals remain purely local in Room to respect user privacy.
- Only public Community interactions and public profile metadata are synced to Firestore.
