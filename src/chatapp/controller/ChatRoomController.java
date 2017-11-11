package chatapp.controller;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.ImageIcon;

import chatapp.model.ChatRoomModel;
import chatapp.model.ICRModel2CRViewAdapter;
import chatapp.model.ICRModel2MainModelAdapter;
import chatapp.model.ICmd2CRModelViewAdapter;
import chatapp.model.MainModel;
import chatapp.model.object.receiver.ProxyReceiver;
import chatapp.model.object.receiver.Receiver;
import chatapp.view.ChatRoomView;
import chatapp.view.ICRView2CRModelAdapter;
import chatapp.view.MainView;
import common.IChatRoom;
import common.IComponentFactory;
import common.IReceiver;
import common.IUser;

/**
 * Chat room mini controller.
 */
public class ChatRoomController {

	private ChatRoomView<IUser, IChatRoom> chatRoomView;
	private ChatRoomModel chatRoomModel;

	/**
	 * Constructor.
	 * @param currentUserStub The currentUserStub for this chat room.
	 * @param chatRoom The chat room object of this chat room.
	 * @param model The main chat room model. 
	 * @param view is the main view;
	 * @param receiverPort The port for the receiver.
	 */
	public ChatRoomController(IUser currentUserStub, IChatRoom chatRoom, MainModel model, MainView<IUser, IChatRoom> view, String receiverPort) {
		
		System.out.println("create receiver from user: " + currentUserStub);
		IReceiver receiver = new Receiver(currentUserStub, new ICmd2CRModelViewAdapter() {

			@Override
			public void appendMsg(String text, String name) {
				chatRoomView.appendMessage(text, name);
			}

			@Override
			public void addReceiver(IReceiver rcvr) {
				chatRoomModel.addReceiver(rcvr);
			} 

			@Override
			public void removeReceiver(IReceiver rcvr) {
				chatRoomModel.removeReceiver(rcvr);
			}

			@Override
			public void buildScrollableComponent(IComponentFactory fac, String label) {
				chatRoomView.buildScrollableComponent(fac, label);
			}

			@Override
			public void buildNonScrollableComponent(IComponentFactory fac, String label) {
				chatRoomView.buildNonScrollableComponent(fac, label);
			}

			
		});
		
		// when a user join a chat room, the user create a IReceiver object, and export to stub.
//		int boundPort = PortManager.Singleton.getAvailPort();
		int boundPort = Integer.parseInt(receiverPort);
		IReceiver receiverStub = null;
		try {
			receiverStub = (IReceiver) UnicastRemoteObject.exportObject(receiver, boundPort);
			receiverStub = new ProxyReceiver(receiverStub, receiver.getUserStub().getName(), receiver.getUUID(), chatRoom.toString());
			System.out.println("using port: " + boundPort + " for receiver: " + receiverStub);
		} catch (RemoteException e) {
			System.out.println("fail to join a chat room " + chatRoom + " using port " + boundPort);
			e.printStackTrace();
		}
		
		chatRoomView = new ChatRoomView<IUser, IChatRoom>(
				new ICRView2CRModelAdapter<IUser, IChatRoom>() {

					@Override
					public void exitChatRoom() {
						chatRoomModel.exitChatRoom();
					}

					@Override
					public void sendText(String text) {
						chatRoomModel.sendText(text);
					}

					@Override
					public void sendFile(File file) {
						chatRoomModel.sendFile(file);
					}

					@Override
					public void sendEmoji(ImageIcon img) {
						chatRoomModel.sendEmoji(img);

					}

					@Override
					public IChatRoom getChatRoom() {
						return chatRoomModel.getChatRoom();
					}

					@Override
					public void requestChatRoomList(IUser user) {
						chatRoomModel.requestChatRoomList(user);
					}

				}, currentUserStub + "|" + chatRoom.toString());

		chatRoomModel = new ChatRoomModel(receiver, receiverStub, chatRoom, 
				new ICRModel2CRViewAdapter() {

			@Override
			public void appendMessage(String text, String name) {
				chatRoomView.appendMessage(text, name);
			}

			@Override
			public void listUsers(Iterable<IUser> users) {
				int count = 0;
				for (@SuppressWarnings("unused") IUser user : users) {
					count++;
				}
				IUser[] array = new IUser[count];
				count = 0;
				for (IUser user : users) {
					array[count++] = user;
				}
				chatRoomView.listUsers(array);
			}

			@Override
			public void displayImage(ImageIcon img) {
				chatRoomView.displayImageIcon(img);
			}

		},
				new ICRModel2MainModelAdapter() {

			@Override
			public void removeChatRoom(IChatRoom chatRoom) {
				model.removeChatRoom(chatRoom);
			}

			@Override
			public void requestChatRoomList(IUser user) {
				model.requestChatRooms(user);
			}

		});
		chatRoomModel.addIReceiverStub(receiverStub);
		view.addChatRoomView(chatRoomView);
	}

	/**
	 * start the controller.
	 */
	public void start() {
		chatRoomView.start();
		chatRoomModel.start();
	}

	/**
	 * Exit the chat room, doing:
	 * Call the exit chat room method in chat room model. 
	 */
	public void exitChatRoom() {
		chatRoomModel.exitChatRoom();
	}
}