package chatapp.model;

import common.IChatRoom;
import common.IUser;

/**
 * Chat room model to main model adapter.
 *
 */
public interface ICRModel2MainModelAdapter {

	/**
	 * Exit a chat room doing:
	 * Tell the main model to remove the chat room in user and to remove the chat room mini MVC.
	 * @param chatRoom is the chat room to remove.
	 */
	void removeChatRoom(IChatRoom chatRoom);

	/**
	 * Request chat rooms list a user created or joined.
	 * @param user The user.
	 */
	void requestChatRoomList(IUser user);

}
