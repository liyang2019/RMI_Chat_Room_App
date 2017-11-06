package model.cmd;


import common.IAddReceiver;

/**
 * When one user wants to join a chat room,
 * he will send data packet with data type TypeJoinChatRoom to all the receivers in the chat room, and executed by remote users
 * to update the informations about this user.
 *
 */
public class AddReceiverType implements IAddReceiver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4908365933653793207L;
	
	/**
	 * Join chat room type singleton object.
	 */
	public static final AddReceiverType Singleton = new AddReceiverType();
	
	private AddReceiverType() {}
		
}