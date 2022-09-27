# Instagram clone
The purpose of this course project is to develop a client-server application that is analogous to the popular Instagram application and has similar functionality. The application allows you to add photos to your personal account, view publications of other users of the application. It is possible to edit profile information, delete publications, view profiles of other users.

The mobile application was developed for Android OS in Java language. The server is developed on ASP.NET Core WEB API. Relational database MS SQL Server is used to store user data.

The full functionality of the application is described below:
- Create a password to enter the application;
- User registration;
- User data entry;
- User authorization;
- View publications of other users;
- View profiles of other users;
- Implementation of access to directories with photos on the device;
- Receive notifications upon successful publication;
- Adding a photo with a description to the server;
- View your profile;
- Editing your profile;
- View your publication;
- Deleting your post.

The functionality of the application is shown below:

On startup, a preview screen opens:

<img src="./Assets/previewScreen.png" width="200" height="400">
<br>

In the case of the first launch of the application, it is proposed to create a password to enter the application, which will be used at each launch. If the passwords do not match, the confirmation during creation generates an error message:
<img src="./Assets/addPin.png" width="200" height="400">
<br>

The following password entry form is displayed each time you log into the application. If an incorrect password is entered, a corresponding message is displayed, otherwise it is reported that the password was entered correctly and the user gets access to the application:

<img src="./Assets/incorrectPasscode.png" width="200" height="400">
<img src="./Assets/correctPasscode.png" width="200" height="400">
<br>

The user authorization form opens. <br>_Screenshots are disabled on this screen in order to avoid cases of theft of authorization data (during testing, the prohibition was disabled for screen sharing). Also, there is a handler for pressing the "Back" button (onBackPressed), which, when triggered, ends the application session._<br>
If the user has an account, then he enters authorization data in the appropriate fields, otherwise you need to click on the blue inscription under the login button to go to the Registration page:

<img src="./Assets/login.png" width="200" height="400">
<br>

_On this screen, there is a ban on creating screenshots to avoid cases of theft of authorization data (for the duration of testing, the ban was disabled for screen sharing)._<br>
To register, you must enter your email, come up with a password and a unique Username used in the application. The server will check if a user with the same name exists and display an appropriate message if the names match.
<img src="./Assets/addUser.png" width="200" height="400">
<img src="./Assets/loadingAdUser.png" width="200" height="400">
<br>

Next, the user goes to the page for entering personal information. Entering this information is optional. After sending, registration is considered successful:

<img src="./Assets/addUserData.png" width="200" height="400">
<img src="./Assets/successRegistr.png" width="200" height="400">
<br>

_On this screen there is a handler for pressing the "Back" button (onBackPressed), when triggered, the application session ends._<br>
When a user logs in, a feed of publications is displayed to the user. The feed is updated by dragging down. After loading, user posts are displayed:
<img src="./Assets/loadingFeed.png" width="200" height="400">
<img src="./Assets/feed.png" width="200" height="400">
<br>

_On this screen, there is a ban on creating screenshots in order to avoid cases of theft of pictures in the user's gallery (for the duration of testing, the ban was disabled for screen sharing)._<br>
To add a new publication, press the middle button in the bottom menu. A screen will open displaying the images in the directories of the device. The screenshots show images in various folders. Folders are selected from the drop-down menu at the top of the screen:

<img src="./Assets/openGallery.png" width="200" height="400">
<img src="./Assets/galleryOtherFolder.png" width="200" height="400">
<br>

Also, it is possible to upload photos directly from the camera:
<img src="./Assets/camera.png" width="200" height="400">
<br>

After selecting a photo, you will be given the option to add a description to the photo. After clicking on the "Share" button in the upper right corner, the publication data is sent to the server:
<img src="./Assets/publicationDescription.png" width="200" height="400">
<br>

And the user receives a notification in case of successful publication. The updated feed is shown below:

<img src="./Assets/notification.png" width="200" height="400">
<img src="./Assets/newFeed.png" width="200" height="400">
<br>

The user has the opportunity to visit his profile. It displays his personal information, publications, their number, and also has a button for editing the profile and exiting the profile:

<img src="./Assets/profile.png" width="200" height="400">
<br>

Clicking on a post in your profile opens a screen with the post, allowing you to view the photo in more detail, as well as view the description of the photo.

<img src="./Assets/openedPublication.png" width="200" height="400">
<br>

On the publish screen, there is a button in the upper right corner to delete this post. When you click on it, a dialog box appears asking you to confirm the deletion:

<img src="./Assets/delete.png" width="200" height="400">
<br>

This screenshot shows a profile that has no publications. When you open such a profile, a dialog box appears prompting you to add a photo:

<img src="./Assets/emptyProfile.png" width="200" height="400">
<br>

When you switch to the profile editing window, text fields with the current data entered are provided. When you change your data and save the user data will be updated:

<img src="./Assets/editProfile.png" width="200" height="400">
<br>

Also, from the feed you can get to the profiles of other users. To do this, click on the profile icon in the user's publication and a window with the profile of the selected user will open:

<img src="./Assets/otherUserProf.png" width="200" height="400">
<br>

User passwords are stored in the database in a hashed form based on the RFC2898 algorithm:

<img src="./Assets/hash.png">
<br>

The project uses obfuscation using Proguard. Below are two screenshots. The first one shows the project tree without obfuscation, the second one, therefore, after applying obfuscation:
<img src="./Assets/before.png">

After obfuscation, there is a decrease in the amount of memory occupied by the application, and the code is rendered unreadable, as evidenced by the presence of folders with ambiguous names:

<img src="./Assets/after.png">


_Prohibition of taking screenshots is done by placing the following code in the Activity code file:_

```getWindow().setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE);```

_Using the application session management functionality is as follows:_
- _Determining whether the user is authorized, and depending on this, he opens either the authorization screen or the news feed immediately. SharedPreferences._ is used for this.
- _End the application session when pressing the "Back" button_

The following requirements have been implemented:
- User registration
- Sending images to the server
- Adding a description to a post
- Authorization when entering the application
- Send notifications

As well as:
- Minimum number of screens 8+
- Protecting the application by entering a pin code
- Use password protection when stored in the database
- Application session management
- Prohibition of taking screenshots
- Obfuscation

