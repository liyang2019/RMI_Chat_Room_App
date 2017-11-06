package model.user;

import java.rmi.RemoteException;

import common.IChatRoom;
import common.IUser;

/**
 * The ICustomUser interface implements the IUser common interface.
 *
 */
public interface ICustomUser extends IUser {
	
	/**
	 * User joins a chat room. This method only add the chat room into user's chat room set.
	 * @param chatRoom is the chat room the user want to join.
	 * @return return false if the chat room in the user's chat room list.
	 * @throws RemoteException  if a network error occurs.
	 */
	public boolean joinChatRoom(IChatRoom chatRoom) throws RemoteException;

	/**
	 * User exits a chat room. This method only remove the chat room into user's chat room set.
	 * @param chatRoom is the chat room the user want to exit.
	 * @return return false if the chat room not in the user's chat room list.
	 * @throws RemoteException  if a network error occurs.
	 */
	public boolean exitChatRoom(IChatRoom chatRoom)  throws RemoteException;

}
