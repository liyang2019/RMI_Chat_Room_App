package model;

import common.IChatRoom;

/**
 * Chat room model to main model adapter.
 *
 */
public interface IChatRoomModel2ModelAdapter {

	/**
	 * Exit a chat room.
	 * @param chatRoom is the chat room to exit.
	 */
	void exitChatRoom(IChatRoom chatRoom);

}
