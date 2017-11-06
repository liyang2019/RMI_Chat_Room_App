package view;

/**
 * Main view to main model adapter.
 * @param <UserObj> the user generic object.
 * @param <ChatRoomObj> the chat room generic object.
 */
public interface IMainView2ModelAdapter<UserObj, ChatRoomObj> {

	/**
	 * start the server.
	 * @param userName the user name.
	 * @param serverName the server name.
	 */
	void startServer(String userName);

	/**
	 * make a new chat room.
	 * @param chatRoomName the name of the new chat room.
	 */
	void makeChatRoom(String chatRoomName);

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
	 * Request chat rooms list the host created or joined.
	 * @param user a host how created chat rooms.
	 */
	void requestChatRoomList(UserObj user);

	/**
	 * exit the chat room application.
	 */
	void exit();

	/**
	 * Join a chat room.
	 * @param chatRoom the chat room object to join.
	 */
	void joinChatRoom(ChatRoomObj chatRoom);
}
