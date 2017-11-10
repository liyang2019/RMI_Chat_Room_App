package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * IReceiver defines a receiver in a chat room, which corresponds to a user (More accurately, a remote user's chat room) in the remote. 
 * Similar to IUser, IReceiver is NOT transmitted to anyone. ONLY the STUB of this Remote object is ever transmitted.
 */
public interface IReceiver extends Remote {
	
	/**
	 * Receive a data packet. If it is an unknown data packet or possible error happens, 
	 * the receiver asynchronously sends a data packet back to the sender.
	 * @param data the received data packet
	 * @throws RemoteException if a network error occurs
	 * @param <T> The type of the data being held.
	 */
	public <T> void receive(DataPacketChatRoom<T> data) throws RemoteException;

	/**
	 * Get the user stub corresponding to the IReceiver interface
	 * @return a user stub
	 * @throws RemoteException if a network error occurs
	 */
	public IUser getUserStub() throws RemoteException;
	
	/**
	 * Get the UUID of this receiver.
	 * @return the UUID of this receiver.
	 * @throws RemoteException if a network error occurs.
	 */
	public UUID getUUID() throws RemoteException;
}
