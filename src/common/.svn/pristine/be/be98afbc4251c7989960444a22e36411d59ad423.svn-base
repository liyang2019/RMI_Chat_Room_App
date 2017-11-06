package common;

import java.io.Serializable;
import java.util.UUID;
/**
 * A generalized chat room interface that defines a set of operations of a chat room.
 * A chat room is a FULLY SERIALIZABLE object that is wholly transmitted from one machine to another machine
 * A chat room holds a set of IReceiver stubs.
 */
public interface IChatRoom extends Serializable{
	
	/**
	 * Get the name of the chat room
	 * @return The name of the chat room
	 */
	public String getName();
	
	/**
	 * Get UUID of this chat room, which is an unique identifier for this chat room
	 * @return UUID of this chat room
	 */
	public UUID getUUID();
	
	/**
	 * Get all receivers' stubs in the chat room at the moment the method is called
	 * @return All the receivers' stubs in the chat room at the moment. 
	 */
	public Iterable<IReceiver> getIReceiverStubs();
	
	/**
	 * Send a data packet to everyone else in the chat room.
	 * For a preferred implementation, this method should send the data packet asynchronously,
	 * i.e. this method delegates the sending process into a new thread and returns without 
	 * getting the returned status from the receivers. 
	 * @param data The data packet to be sent
	 */
	public <T> void sendPacket(DataPacketChatRoom<T> data, final IVoidLambdaDP... cmds);
	
	
	/**
	 * Add a receiver to this chat room.
	 * The implementation should  inform others in the chat room and 
	 * then add the receiver in the local chat room by calling addIReceiverStubLocally .
	 * @param receiver the receiver to add
	 * @return False if the receiver is already in the chat room; True if successfully add the receiver
	 */
	public boolean addIReceiverStub(IReceiver receiver);
	
	/**
	 * Remove a receiver from this chat room.
	 * The implementation should remove the receiver in the local chat room and then inform others in the chat room.
	 * @param receiver the receiver to remove
	 * @return False if the receiver is actually not in the chat room; True if successfully remove the receiver
	 */
	public boolean removeIReceiverStub(IReceiver receiver);
	
	/**
	 * Add a receiver in the local chat room without interacting with remote peers
	 * @param receiver the receiver to add
	 * @return False if the receiver is already in the chat room; True if successfully add the receiver
	 */
	public boolean addIReceiverStubLocally(IReceiver receiver);
	
	/**
	 * Remove a receiver in the local chat room without interacting with remote peers
	 * @param receiver the receiver to remove
	 * @return False if the receiver is actually not in the chat room; True if successfully remove the receiver
	 */
	public boolean removeIReceiverStubLocally(IReceiver receiver);
	
}
