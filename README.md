# MY MOVIE SHELF ANDROID APP (A Take Home Assessment project for Emerge, Auckland)
- I took this opportunity to use latest Android tools and technologies to build this massive fully functional Android App
- Currently submitted for Review to Google Play Console, awaiting approval and release to Google Play Store
- TOP 5 Movies CRUD functionality
- The project consists of a simple mobile app for movie fans to showcase ordered lists of their favorite movies. Users should be able to create multiple "Top 5" lists that will be displayed on their profile.

# Requirements
1. Simple sign-up/login/logout auth system (basic username + password will do).
2. Profile TOP5 lists CRUD functionality.
3. Ability to view another user's TOP5 Profile by providing their username.
4. Decisions can be made for caching and persistence needs.

# List Examples
"All-time Funniest Movies"
"Movies That Bring You To Tears"
"Movies With The Best Twist"
- Each list should be identified by a title and an emoji.

# Features
1. User Authentication
2. Simple sign-up/login/logout auth system (basic username + password with help of Room DB).
3. Profile TOP5 Lists CRUD Functionality
4. Create, Read, Update, and Delete operations for movie lists.
5. View my multiple Top 5 Lists.
6. Ability to view another user's TOP5 Profile by providing their username.

# Development Technologies
1. Kotlin: Language used for development.
2. Jetpack Compose: For building declarative UI.
3. Room Database: For local storage of user credentials.
4. TMDB API: For fetching movie data.
5. Firebase Firestore: For storing user lists and movies.
6. Hilt: For Dependency Injection.
7. Kotlin Coroutines & Flow: For asynchronous programming.
8. MVVM Clean Architecture with Usecase Approach

# ScreenShots

LOGIN FLOW

https://github.com/sairam1592/Emerge-Android-Test/assets/14980927/8231e332-48e8-4572-81b6-6477fa554568


CREATE A LIST and SEARCH MOVIES FROM TMDB and ADD them to list

https://github.com/sairam1592/Emerge-Android-Test/assets/14980927/ba1345d8-9423-44e6-8ffa-183684f47552


SAVE THE CREATED LIST TO FIREBASE FIRESTORE DB

https://github.com/sairam1592/Emerge-Android-Test/assets/14980927/b0d473c2-3713-4358-9cb7-b23dd6dec2b9


SAMPLE ACCOUNT WITH MULTIPLE TOP 5 LISTS

https://github.com/sairam1592/Emerge-Android-Test/assets/14980927/0742a92f-f0f0-4a5d-85e3-570242a6effd


SEARCH and RETRIVE OTHER USER ACCOUNTS details from FIREBASE FIRESTORE DB

https://github.com/sairam1592/Emerge-Android-Test/assets/14980927/9a4d51bd-27f2-457c-a216-2960dd611c20


DELETE A LIST FROM UI AND FIREBASE DB

https://github.com/sairam1592/Emerge-Android-Test/assets/14980927/f84b7ec1-e91a-4cfb-96d6-b6548742027f


MODIFY YOUR LIST By ADDING, REMOVING Movies to it

https://github.com/sairam1592/Emerge-Android-Test/assets/14980927/a4054407-6e85-4978-9bca-0832cce400db



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
3. Support adding, viewing and deleting movie items to a category.

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


