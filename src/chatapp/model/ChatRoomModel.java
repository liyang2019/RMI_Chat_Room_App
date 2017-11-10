package chatapp.model;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import chatapp.model.datapacket.data.AddReceiverData;
import chatapp.model.datapacket.data.RemoveReceiverData;
import chatapp.model.object.user.ProxyUser;
import common.DataPacketChatRoom;
import common.IAddReceiverType;
import common.IChatRoom;
import common.IReceiver;
import common.IRemoveReceiverType;
import common.IUser;

/**
 * The chat room model in the chat room mini MVC.
 *
 */
public class ChatRoomModel {
	
	/**
	 * Chat room model to chat room view adapter.
	 */
	private ICRModel2CRViewAdapter _viewAdapter;
	/**
	 * Chat room model to main model adapter.
	 */
	private ICRModel2MainModelAdapter _mainModelAdapter;
	/**
	 * The chat room object for this chat room model.
	 */
	private IChatRoom chatRoom;
	/**
	 * The receiver for this chat room, receives data packets.
	 */
	private IReceiver receiver;
	/**
	 * The receiverStub for this chat room, receives data packets.
	 */
	private IReceiver receiverStub;
	
	/**
	 * Constructor.
	 * @param receiver is receiver for this chat room, receives data packets.
	 * @param receiverStub is receiverStub for this chat room, receives data packets.
	 * @param chatRoom is the chat room object for this chat room model.
	 * @param _mainModelAdapter is the chat room model to main model adapter.
	 * @param _viewAdapter is the chat room model to chat room view adapter.
	 */
	public ChatRoomModel(IReceiver receiver, IReceiver receiverStub, IChatRoom chatRoom, ICRModel2CRViewAdapter _viewAdapter, ICRModel2MainModelAdapter _mainModelAdapter) {
		this.receiver = receiver;
		this.receiverStub = receiverStub;
		this.chatRoom = chatRoom;
		this._viewAdapter = _viewAdapter;
		this._mainModelAdapter = _mainModelAdapter;
	}

	/**
	 * User exit this chat room, doing:
	 * Send message to the chat room to tell remote receivers the quit information.
	 * Send remove receiver data packet to all other receivers in the chat room to remove the receiver.
	 * Tell the main model to remove the chat room in user and to remove the chat room mini MVC.
	 */
	public void exitChatRoom() {
		chatRoom.sendPacket(new DataPacketChatRoom<String>(String.class, receiverStub + " quit this chat room. ", receiverStub));
		removeIReceiverStub(receiverStub);
		_mainModelAdapter.removeChatRoom(chatRoom);
	}
	
	/**
	 * Add a receiver to this chat room. 
	 * The implementation should inform others in the chat room and then add the 
	 * receiver in the local chat room by calling addIReceiverStubLocally .
	 * @param receiverStub the receiver to add
	 * @return False if the receiver is already in the chat room; True if successfully add the receiver
	 */
	public boolean addIReceiverStub(IReceiver receiverStub) {
		chatRoom.sendPacket(new DataPacketChatRoom<IAddReceiverType>(IAddReceiverType.class, new AddReceiverData(receiverStub), this.receiverStub));
		return chatRoom.addIReceiverStubLocally(receiverStub);
	}
	
	/**
	 * Remove a receiver from this chat room. 
	 * The implementation should remove the receiver in the local chat room and then 
	 * inform others in the chat room.
	 * @param receiverStub the receiver to remove
	 * @return False if the receiver is actually not in the chat room; True if successfully remove the receiver
	 */
	public boolean removeIReceiverStub(IReceiver receiverStub) {
		chatRoom.sendPacket(new DataPacketChatRoom<IRemoveReceiverType>(IRemoveReceiverType.class, new RemoveReceiverData(receiverStub), this.receiverStub));
		return chatRoom.removeIReceiverStubLocally(receiverStub);
	}

	/**
	 * Send text to the chat room in this chat room model.
	 * @param text is sent to the chat room in this chat room model.
	 */
	public void sendText(String text) {
		chatRoom.sendPacket(new DataPacketChatRoom<String>(String.class, text, receiverStub));
	}

	/**
	 * Send file to the chat room in this chat room model.
	 * @param file is sent to the chat room in this chat room model.
	 */
	public void sendFile(File file) {
		chatRoom.sendPacket(new DataPacketChatRoom<File>(File.class, file, receiverStub));
	}

	/**
	 * Send emoji to the chat room in this chat room model.
	 * @param emoji is sent to the chat room in this chat room model.
	 */
	public void sendEmoji(ImageIcon emoji) {
		chatRoom.sendPacket(new DataPacketChatRoom<ImageIcon>(ImageIcon.class, emoji, receiverStub));
	}

	/**
	 * start the chat room model
	 */
	public void start() {
		listUsers();
	}
	
	/**
	 * Refresh the user list to list users.
	 */
	public void listUsers() {
		List<IUser> users = new ArrayList<>();
		for (IReceiver rcvr : chatRoom.getIReceiverStubs()) {
			try {
				if (this.receiverStub.equals(rcvr)) {
					users.add(this.receiver.getUserStub());
				} else {
					users.add(new ProxyUser(rcvr.getUserStub()));
				}
			} catch (RemoteException e) {
				System.out.println("failed to get user stubs from receiver: " + rcvr);
				e.printStackTrace();
			}
		}
		_viewAdapter.listUsers(users);
	}

	/**
	 * Remove a receiverStub from this chat room.
	 * @param receiverStub is the receiverStub to remove from this chat room.
	 */
	public void removeReceiver(IReceiver receiverStub) {
		chatRoom.removeIReceiverStubLocally(receiverStub);
		listUsers();
	}

	/**
	 * Add a receiverStub to this chat room.
	 * @param receiverStub is the receiverStub to add to this chat room.
	 */
	public void addReceiver(IReceiver receiverStub) {
		chatRoom.addIReceiverStubLocally(receiverStub);
		listUsers();
	}

	/**
	 * Get chat room object from chat room model.
	 * @return chat room object in chat room model.
	 */
	public IChatRoom getChatRoom() {
		return chatRoom;
	}
}
