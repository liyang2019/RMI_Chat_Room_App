package controller;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.ImageIcon;

import common.IChatRoom;
import common.ICmd2ModelAdapter;
import common.IComponentFactory;
import common.IReceiver;
import common.IUser;
import model.ChatRoomModel;
import model.IChatRoomModel2ChatRoomViewAdapter;
import model.IChatRoomModel2ModelAdapter;
import model.MainModel;
import model.receiver.ProxyReceiver;
import model.receiver.Receiver;
import util.PortManager;
import view.ChatRoomView;
import view.IChatRoomView2ModelAdapter;
import view.MainView;

/**
 * Chat room mini controller.
 *
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
	 */
	public ChatRoomController(IUser currentUserStub, IChatRoom chatRoom, MainModel model, MainView<IUser, IChatRoom> view) {
		
		System.out.println("create receiver from user: " + currentUserStub);
		IReceiver receiver = new Receiver(currentUserStub, new ICmd2ModelAdapter() {

			@Override
			public void appendMsg(String text) {
				chatRoomView.appendMessage(text);
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
			public void displayImage(ImageIcon img) {
				chatRoomView.displayImageIcon(img);
			}

			@Override
			public void buildComponent(IComponentFactory fac) {
				// TODO Auto-generated method stub
			}
		});
		
		// when a user join a chat room, the user create a IReceiver object, and export to stub.
		int boundPort = PortManager.Singleton.getAvailPort();
		IReceiver receiverStub = null;
		try {
			receiverStub = (IReceiver) UnicastRemoteObject.exportObject(receiver, boundPort);
			receiverStub = new ProxyReceiver(receiverStub, receiver.getUserStub().getName(), receiver.getUUID(), chatRoom.toString());
			chatRoom.addIReceiverStub(receiverStub);
			System.out.println("using port: " + boundPort + " for receiver: " + receiverStub);
		} catch (RemoteException e) {
			System.out.println("fail to join a chat room " + chatRoom + " using port " + boundPort);
			e.printStackTrace();
		}
		
		chatRoomView = new ChatRoomView<IUser, IChatRoom>(
				new IChatRoomView2ModelAdapter<IChatRoom>() {

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

				}, currentUserStub + "|" + chatRoom.toString());

		chatRoomModel = new ChatRoomModel(receiver, receiverStub, chatRoom, 
				new IChatRoomModel2ChatRoomViewAdapter() {

			@Override
			public void appendMessage(String text) {
				chatRoomView.appendMessage(text);
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
				new IChatRoomModel2ModelAdapter() {

			@Override
			public void exitChatRoom(IChatRoom chatRoom) {
				model.exitChatRoom(chatRoom);
			}

		});
		
		view.addChatRoomView(chatRoomView);

	}

	/**
	 * start the controller.
	 */
	public void start() {
		chatRoomView.start();
		chatRoomModel.start();
	}
}