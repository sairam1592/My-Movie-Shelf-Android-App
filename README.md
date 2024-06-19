# Emerge-Android-Test-Application
- Senior Android Test Project - TOP5 Movies CRUD functionality
- The project consists of a simple mobile app for movie fans to showcase ordered lists of their favorite movies. Users should be able to create multiple "Top 5" lists that will be displayed on their profile.

# List Examples
"All-time Funniest Movies"
"Movies That Bring You To Tears"
"Movies With The Best Twist"
- Each list should be identified by a title and an emoji.

# Features
1. User Authentication
2. Simple sign-up/login/logout auth system (basic username + password will do).
3. Profile TOP5 Lists CRUD Functionality
4. Create, Read, Update, and Delete operations for movie lists.
5. View Another User's TOP5 Profile
6. Ability to view another user's TOP5 Profile by providing their username.

# Development Technologies
1. Kotlin: Language used for development.
2. Jetpack Compose: For building declarative UI.
3. Room Database: For local storage of user credentials.
4. TMDB API: For fetching movie data.
5. Firebase Firestore: For storing user lists and movies.
6. Hilt: For Dependency Injection.
7. Kotlin Coroutines & Flow: For asynchronous programming.

# Requirements
1. Simple sign-up/login/logout auth system (basic username + password will do).
2. Profile TOP5 lists CRUD functionality.
3. Ability to view another user's TOP5 Profile by providing their username.
4. Decisions can be made for caching and persistence needs.

# Features and Implementation

**User Authentication**
1. The authentication system uses Room Database to store user credentials locally.
2. Sign-up, login, and logout functionalities are integrated.

**TMDB Integration**
1. Integrated with TMDB to fetch and display movie data.
2. Uses a secure method to store and access the TMDB API key from secret.properties.

**Firebase Firestore Integration**
1. Stores the user's favorite movie categories and movies.
2. Supports adding, viewing, and deleting categories.

**Search Functionality**
1. Implemented debounce search to fetch movies based on keywords from TMDB.
2. Search for users to view their favorite lists.

**UI and UX**
1. Clean and simple UI using Jetpack Compose.
2. Interactive and responsive design with smooth scrolling in lists.
3. Toast messages to indicate success or failure of operations.

# License
This project is licensed under the MIT License - see the LICENSE file for details.

Contact
For any queries or issues, please contact me at _sairam.a1592@gmail.com_


