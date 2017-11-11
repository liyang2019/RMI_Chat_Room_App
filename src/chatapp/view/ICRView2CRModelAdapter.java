package chatapp.view;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * The chat room mini view to chat room mini model adapter.
 * @param <UserObj> the user generic object.
 * @param <ChatRoomObj> chat room generic object.
 */
public interface ICRView2CRModelAdapter<UserObj, ChatRoomObj> {

	/**
	 * Exit this chat room.
	 */
	void exitChatRoom();

	/**
	 * Send text to this chat room. All users' corresponding chat room will receive the text.
	 * @param text is sent to this chat room.
	 */
	void sendText(String text);

	/**
	 * Send file to this chat room. All users' corresponding chat room will receive the file.
	 * @param file is sent to this chat room.
	 */
	void sendFile(File file);

	/**
	 * Send emoji to this chat room. All users' corresponding chat room will receive the emoji.
	 * @param img is sent to this chat room.
	 */
	void sendEmoji(ImageIcon img);

	/**
	 * Get chat room object from model.
	 * @return chat room generic object.
	 */
	ChatRoomObj getChatRoom();

	/**
	 * Request chat rooms list a user created or joined.
	 * @param user The user.
	 */
	void requestChatRoomList(UserObj user);
	
}
