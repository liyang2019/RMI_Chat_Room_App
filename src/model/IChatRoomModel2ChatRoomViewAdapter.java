package model;

import javax.swing.ImageIcon;

import common.IUser;

/**
 * Chat room model to chat room view adapter.
 *
 */
public interface IChatRoomModel2ChatRoomViewAdapter {

	/**
	 * Append text to chat room view.
	 * @param text is the text to append to chat room view.
	 */
	void appendMessage(String text);

	/**
	 * List users in chat room view.
	 * @param users are listed in chat room view.
	 */
	void listUsers(Iterable<IUser> users);

	/**
	 * Add image to the model.
	 * @param img is the image to add to the model.
	 */
	public void displayImage(ImageIcon img);

}
