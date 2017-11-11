package chatapp.model;

import java.util.List;

import common.IChatRoom;
import common.IUser;

/**
 * Main model to main view adapter.
 *
 */
public interface IMainModel2MainViewAdapter {

	/**
	 * Append text in the Info area of main view.
	 * @param text the text to append to the Info area of main view.
	 */
	void appendInfo(String text);
	
	/**
	 * List all the connected users in the main view.
	 * @param users all the connected users.
	 */
	void listConnectedUsers(List<IUser> users);

	/**
	 * List chat rooms in the main view.
	 * @param iterable the chat rooms to list.
	 */
	void listChatRooms(Iterable<IChatRoom> iterable);

	/**
	 * Create a chat room miniMVC. 
	 * @param currentUser the current user stub used to create the chat room mini MVC.
	 * @param chatRoom the chat room object used to create the chat room mini MVC.
	 * @param chatRoomName the name of the chat room.
	 * @param receiverPort The port for the receiver.
	 * @return The main model to chat room mini MVC adapter.
	 */
	IMainModel2CRMVCAdapter createChatRoomMVC(IUser currentUser, IChatRoom chatRoom, String receiverPort);
	
	/**
	 * Remove a chat room miniMVC. 
	 * @param currentUser the current user stub used to remove the chat room mini MVC.
	 * @param chatRoom the chat room object used to remove the chat room mini MVC.
	 * @param chatRoomName the name of the chat room.
	 */
	void removeChatRoomMVC(IChatRoom chatRoom);

	/**
	 * High light an already join chat room in chat room view.
	 * @param chatRoom
	 */
	void selectChatRoom(IChatRoom chatRoom);

	/**
	 * Display IP address in connect to IP panel.
	 * @param localAddress
	 */
	void displayIP(String localAddress);
}
