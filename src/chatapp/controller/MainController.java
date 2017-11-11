package chatapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chatapp.model.IMainModel2CRMVCAdapter;
import chatapp.model.IMainModel2MainViewAdapter;
import chatapp.model.MainModel;
import chatapp.view.IMainView2MainModelAdapter;
import chatapp.view.MainView;
import common.IChatRoom;
import common.IUser;

/**
 * chat room controller.
 *
 */
public class MainController {
	
	private MainView<IUser, IChatRoom> view;
	private MainModel model;
	/**
	 * A map of chatRoom and chatRoomController.
	 */
	private Map<IChatRoom, ChatRoomController> chatRoomControllerMap = new HashMap<>();
	
	/**
	 * Constructor.
	 */
	public MainController() {
		
		view = new MainView<>(new IMainView2MainModelAdapter<IUser, IChatRoom>() {

			@Override
			public void startServer(String userName, String userPort) {
				model.startServer(userName, userPort);
			}

			@Override
			public void makeChatRoom(String chatRoomName, String receiverPort) {
				model.makeChatRoom(chatRoomName, receiverPort);
			}

			@Override
			public void connectToIP(String ipAddress) {
				model.connectToIP(ipAddress);
			}

			@Override
			public void inviteUser(IUser host) {
				model.inviteUser(host);
			}

			@Override
			public void requestChatRoomList(IUser user) {
				model.requestChatRooms(user);
			}

			@Override
			public void exit() {
				model.exit();
			}

			@Override
			public void joinChatRoom(IChatRoom chatRoom, String receiverPort) {
				model.joinChatRoom(chatRoom, receiverPort);
			}
			
		});
		
		model = new MainModel(new IMainModel2MainViewAdapter() {
			
			@Override
			public void appendInfo(String text) {
				view.appendInfo(text);
			}

			@Override
			public void listConnectedUsers(List<IUser> users) {
				view.listConnectedHosts(users);
			}

			@Override
			public void listChatRooms(Iterable<IChatRoom> chatRoomLIst) {
				view.listChatRooms(chatRoomLIst);
			}

			@Override
			public IMainModel2CRMVCAdapter createChatRoomMVC(IUser currentUserStub, IChatRoom chatRoom, String receiverPort) {
				ChatRoomController chatRoomController = new ChatRoomController(currentUserStub, chatRoom, model, view, receiverPort);
				chatRoomControllerMap.put(chatRoom, chatRoomController);
				chatRoomController.start();
				return new IMainModel2CRMVCAdapter() {
					@Override
					public void exitChatRoom() {
						chatRoomController.exitChatRoom();
					}
				};
			}
			
			@Override
			public void removeChatRoomMVC(IChatRoom chatRoom) {
				view.removeChatRoomView(chatRoom);
				chatRoomControllerMap.remove(chatRoom);
			}

			@Override
			public void selectChatRoom(IChatRoom chatRoom) {
				view.seletChatRoom(chatRoom);
			}

			@Override
			public void displayIP(String localAddress) {
				view.displayIP(localAddress);
			}
		});
	}
	
	/**
	 * start the controller.
	 */
	public void start() {
		view.start();
		model.start();
	}
	
	/**
	 * @param args input arguments.
	 */
	public static void main(String[] args) {
		MainController controller = new MainController();
		controller.start();
	}
}
