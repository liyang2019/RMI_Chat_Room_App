# RMI_Chat_Room_App


This is a chat room application using java RMI, with a decentralized architecture.
Users can create a chat room, join other people's chat room.
After doing this, the user will create a chat room view on the local machine.
The following is how to use the application.
=====================================================================
On the user panel
1. Input a user name and a user port used to export the user object to remote stub.
2. Press the Log In button, then a user has been created.
3. Input a chat room name and a chat room port for receiving messages of this chat room.
4. Press the Make button, then a new chat room will be made.
5. Input an IP address.
6. Press the Connect button, then will connect to a remote IP, and all users on that IP will show in the list.
7. Select a user, press Request button, all the chat rooms the user has joined in show in the right list.
8. Select a chat room, press Join button, will join that chat room, a chat room view for that chat room will appear.
=====================================================================
On the chat room panel
In the chat room, user can send text, image, files... A list of users in the chat room will show on the left.

send text:
input some text in the lower text area, then press Send Text button.

send File:
Press Send File button, choose a file to send(currently only support sending the file name).
When the user received a file, a new component with label being the sender's name will show.
In this component, there is a label to display the file name. And the user can press the Save button,
and to choose a path to save the file. The path will be displayed on the panel.

send Image:
Press Send Image button, choose a image to send. When the user received a image, the user will display the
image in the chat window with maximum height 150 and save aspect ratio.

In the left user list panel in the chat room panel, by selecting a user and press the Request button on the top,
the chat rooms the selected user has joined will be listed in the user panel's joined chat room list.

To leave a chat room, press the Exit Room button above the user list panel. By Exit the whole system, the user will
exit all joined chat rooms automatically.
