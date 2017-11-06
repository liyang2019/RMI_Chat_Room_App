package model.receiver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.IReceiver;
import common.IUser;
import common.ReturnStatus;

/**
 * Receiver remote stub proxy object.
 *
 */
public class ProxyReceiver implements IReceiver, Serializable {
	

	private static final long serialVersionUID = 6295382999572277390L;
	private IReceiver receiver;
	private UUID uuid; // // to store the receiver's uuid, no need get it remotely;
	private String userName; // to store the user name of receiver's user, no need get it remotely;
	private String chatRoomName; // to store the chat room name for toSting();
	
	/**
	 * Constructor.
	 * @param receiver a receiver remote stub.
	 * @param userName user name.
	 * @param uuid user's uuid.
	 * @param chatRoomName is the chat room name of this receiver.
	 */
	public ProxyReceiver(IReceiver receiver, String userName, UUID uuid, String chatRoomName) {
		this.receiver = receiver;
		this.userName = userName;
		this.uuid = uuid;
		this.chatRoomName = chatRoomName;
	}

	@Override
	public <T> ReturnStatus receive(DataPacketChatRoom<T> data) throws RemoteException {
		return receiver.receive(data);
	}

	@Override
	public IUser getUserStub() throws RemoteException {
		return receiver.getUserStub();
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Overridden equals() method to compare UUID's
	 * @return  Equal if UUID's are equal.  False otherwise.
	 */
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (!(obj instanceof IReceiver)) return false;
		IReceiver that = (IReceiver) obj;
		try {
			return this.uuid.equals(that.getUUID());
		} catch (RemoteException e) {
			System.err.println("Failed to get other user's uuid, the user is: " + obj);
			e.printStackTrace();
		}
		return false;
	}		

	/**
	 * Overridden hashCode() method to create a hashCode from that is hashCode of the UUID since
	 * equality is based on equality of UUID.
	 * @return a hashCode of the UUID.	
	 */
	@Override
	public int hashCode(){
		return uuid.hashCode();
	}
	
	@Override
	public String toString() {
		return "receiver of [user: " + userName + ", chat room: " + chatRoomName + "]"; 
	}

	@Override
	public DataPacketAlgoCmd<?> getCmd(Class<?> id) throws RemoteException {
		return receiver.getCmd(id);
	}
}
