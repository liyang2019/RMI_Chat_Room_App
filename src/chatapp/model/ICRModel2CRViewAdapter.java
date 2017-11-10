package chatapp.model;

import javax.swing.ImageIcon;

import common.IUser;

/**
 * Chat room model to chat room view adapter.
 *
 */
public interface ICRModel2CRViewAdapter {

	/**
	 * Append text to chat room view.
	 * @param text is the text to append to chat room view.
	 * @param name The name of the person sending the text.
	 */
	void appendMessage(String text, String name);

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
