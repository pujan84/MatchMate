# MatchMate

MatchMate is a simple Android app that fetches and displays user profiles from the Random User API. It allows you to browse through potential matches, accept or decline them, and view your history even when you're offline.

### What's inside
* **MVVM Architecture:** Uses standard ViewModel and LiveData to handle UI logic.
* **Offline Support:** Everything is cached locally using Room. If the API call fails or you're offline, you can still see the matches you've already loaded.
* **Pagination:** Implements manual paging. As you scroll to the end of the list, it fetches the next 10 users using a fixed seed to keep the list consistent.
* **API Integration:** Hits `https://randomuser.me/api/` via Retrofit.
* **Image Loading:** Uses Glide for profile pictures.
* **Animations:** Basic slide-up animations for the list items to make the transition smoother.

### Tech Stack
* Kotlin
* Room (Database)
* Retrofit & Gson (Network)
* Glide (Images)
* KSP (Processing)
* Material 3

### Building and Running
The project uses **AGP 9.3.1** and requires **JDK 17**.

1. Open in Android Studio.
2. Let Gradle sync.
3. Build the project and generate APK
3. Run on a device/emulator (API 24+).

### Development Notes
The Accept/Decline status is stored in the local SQLite database. The "seed" used for the API is `matchmate`, ensuring that the same set of users is returned in the same order every time you page.
