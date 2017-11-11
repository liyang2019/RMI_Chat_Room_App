package chatapp.model.object.chatroom;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import common.DataPacketChatRoom;
import common.IChatRoom;
import common.IReceiver;

/**
 * A concrete chat room object.
 */
public class ChatRoom implements ICustomChatRoom {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2535466474280705182L;

	/**
	 * Chat room name.
	 */
	private final String chatRoomName;
	
	/**
	 * The chat room's host(creater) name.
	 */
	private final String hostName;
	
	/**
	 * Chat room's UUID.
	 */
	private final UUID uuid = UUID.randomUUID();
	
	/**
	 * The data packet receivers in this chat room.
	 */
	private Set<IReceiver> receivers = new HashSet<IReceiver>();
	
	/**
	 * Constructor.
	 * @param chatRoomName is the name of the chat room.
	 * @param hostName is the chat room's host(creater) name.
	 */
	public ChatRoom(String chatRoomName, String hostName) {
		this.hostName = hostName;
		this.chatRoomName = chatRoomName;
	}
	
	@Override
	public String getName() {
		return chatRoomName;
	}
	
	@Override
	public Collection<IReceiver> getIReceiverStubs() {
		return receivers;
	}
	
	@Override
	public <T> void sendPacket(DataPacketChatRoom<T> data) {

		IReceiver sender = data.getSender();
		// create a new thread to let the receiver to receive.
//		(new Thread() {
//			public void run() {
				for (IReceiver rcvr : receivers) {
					// don not send to self
					if (!sender.equals(rcvr)) {
						try {
							System.out.println(data + " sending to receiver: " + rcvr);
							rcvr.receive(data);
						} catch (RemoteException e) {
							System.out.println("remote receiver " + rcvr + " receive data packet error");
							e.printStackTrace();
						}
					}
				}
//			}
//		}).start(); // start the new thread
	}
	
	
	@Override
	public String toString() {
		return hostName + "'s " + this.getName();
	}
	
	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public boolean addIReceiverStubLocally(IReceiver receiverStub) {
		return receivers.add(receiverStub);
	}

	@Override
	public boolean removeIReceiverStubLocally(IReceiver receiverStub) {
		return receivers.remove(receiverStub);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof IChatRoom)) return false;
		IChatRoom that = (IChatRoom) obj;
		return this.getUUID().equals(that.getUUID());
	}
	
	@Override
	public int hashCode() {
		return getUUID().hashCode();
	}
}
