package model;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import common.DataPacketChatRoom;
import common.IChatRoom;
import common.IReceiver;
import common.IUser;
import model.user.ProxyUser;

/**
 * The chat room model in the chat room mini MVC.
 *
 */
public class ChatRoomModel {
	
	/**
	 * Chat room model to chat room view adapter.
	 */
	private IChatRoomModel2ChatRoomViewAdapter _viewAdapter;
	/**
	 * Chat room model to main model adapter.
	 */
	private IChatRoomModel2ModelAdapter _mainModelAdapter;
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
	public ChatRoomModel(IReceiver receiver, IReceiver receiverStub, IChatRoom chatRoom, IChatRoomModel2ChatRoomViewAdapter _viewAdapter, IChatRoomModel2ModelAdapter _mainModelAdapter) {
		this.receiver = receiver;
		this.receiverStub = receiverStub;
		this.chatRoom = chatRoom;
		this._viewAdapter = _viewAdapter;
		this._mainModelAdapter = _mainModelAdapter;
	}

	/**
	 * User exit this chat room.
	 */
	public void exitChatRoom() {
		_viewAdapter.appendMessage("this chat room is quiting...");
		_mainModelAdapter.exitChatRoom(chatRoom);
		chatRoom.removeIReceiverStub(receiverStub);
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
