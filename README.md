# SmileTalk
For the app to work, first start by running the command: mongod in the server app to start the DB.

Then run the command: npm i express body-parser cors custom-env mongoose jsonwebtoken ws in the server app to run the server.

Lastly, run the app (thorugh android studio's emulator or a real device)

The app has 5 Screens:

1. Sign in screen, with the ability to sign in a new user, navigate to the login and the settings screens.

2. Log in screen, with the ability to log in to the messages screen, and to navigate back to the sign in screen.

3. Messages screen, displaying the messages of the logged in user with the following functionalities:

   clicking a chat will navigate to it's chat in the chat screen.

   long click a chat will open a window asking to delete it.

   clicking the + button will open a window asking to add a new chat.

   clicking the logout will open a window asking to logout and go back to the log in screen.

4. Chat screen, displaing the chosen chat, a user can send a message in this chat, or click < to go back to the messages screen.

   A delete chat button is also in this screen. 

5. Setting screen with the ability to enable Dark Mode and to change the ip of the server which holds and sends the data, and < to go backto the sign in screen.

To go back to the previous screen, you can also press the phone's back button.
