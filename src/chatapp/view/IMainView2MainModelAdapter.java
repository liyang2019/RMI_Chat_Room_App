package chatapp.view;

/**
 * Main view to main model adapter.
 * @param <UserObj> the user generic object.
 * @param <ChatRoomObj> the chat room generic object.
 */
public interface IMainView2MainModelAdapter<UserObj, ChatRoomObj> {

	/**
	 * Start the server.
	 * @param userName The user name.
	 * @param userPort The port for the user.
	 */
	void startServer(String userName, String userPort);

	/**
	 * Make a new chat room.
	 * @param chatRoomName The name of the new chat room.
	 * @param receiverPort The port for the receiver.
	 */
	void makeChatRoom(String chatRoomName, String receiverPort);

	/**
	 * connect to an ip address.
	 * @param ipAddress the ip address to connect.
	 */
	void connectToIP(String ipAddress);

	/**
	 * invite a user to a chat room.
	 * @param user the user to invite.
	 */
	void inviteUser(UserObj user);

	/**
	 * Request chat rooms list a user created or joined.
	 * @param user The user.
	 */
	void requestChatRoomList(UserObj user);

	/**
	 * exit the chat room application.
	 */
	void exit();

	/**
	 * Join a chat room.
	 * @param chatRoom the chat room object to join.
	 * @param receiverPort The port for the receiver.
	 */
	void joinChatRoom(ChatRoomObj chatRoom, String receiverPort);
}
