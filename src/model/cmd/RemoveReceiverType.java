package model.cmd;


import common.IRemoveReceiver;

/**
 * When one user wants to leave a chat room,
 * he will send this data packet with data type TypeLeaveChatRoom to all the receivers in the chat room, and executed by remote users
 * to remove the informations about this user.
 *
 */
public class RemoveReceiverType implements IRemoveReceiver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8932933419548854132L;
	/**
	 * Leave chat room type singleton object.
	 */
	public static final RemoveReceiverType Singleton = new RemoveReceiverType();
	
	private RemoveReceiverType() {}

}
