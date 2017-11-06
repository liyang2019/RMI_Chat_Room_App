package model;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import common.IChatRoom;
import common.IUser;
import model.chatroom.ChatRoom;
import model.user.ICustomUser;
import model.user.ProxyCustomUser;
import model.user.ProxyUser;
import model.user.User;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import util.PortManager;

/**
 * chat room model.
 *
 */
public class MainModel {
	
	/**
	 * Main model to main view adapter.
	 */
	private IMainModel2ViewAdapter _mainViewAdapter;
	
	private Consumer<String> outputCmd = s -> MainModel.this._mainViewAdapter.appendInfo((s) + "\n");
	private IRMIUtils rmiUtils = new RMIUtils(outputCmd);
	private Registry localRegistry;
	
	private List<IUser> users; // the users on all the connected remote IPs.
	private ICustomUser currentUser; // current local user object
	private ICustomUser currentUserStub; // current local user remote stub proxy object
	
	/**
	 * constructor.
	 * @param _mainViewAdapter the local model to view adapter.
	 */
	public MainModel(IMainModel2ViewAdapter _mainViewAdapter) {
		this._mainViewAdapter = _mainViewAdapter;
		users = new ArrayList<>();
	}

	/**
	 * Start the server. Create a user, export to user stub, bind the user stub to the register.
	 * @param userName is the user name of this host.
	 */
	public void startServer(String userName) {
		try {
			currentUser = new User(userName, _mainViewAdapter);
			int boundPort = PortManager.Singleton.getAvailPort();
			currentUserStub = (ICustomUser) UnicastRemoteObject.exportObject(currentUser, boundPort);
			currentUserStub = new ProxyCustomUser(currentUserStub, currentUser.getName(), currentUser.getUUID()); // after a user object is created, immediately export to stub, and convert to proxy. 
			System.out.println("using port: " + boundPort + " for user: " + currentUserStub);
			localRegistry.rebind(IUser.BOUND_NAME + boundPort, currentUserStub);
			_mainViewAdapter.appendInfo("User bound to " + IUser.BOUND_NAME + boundPort + " resigter on port " + boundPort + " \n");
		} catch (Exception e) {
			System.err.println("Failed to created a user stub and bind to registry \n");
			e.printStackTrace();
		}
	}

	/**
	 * Connect to a IP. List all the users binded to the registry on that IP.
	 * @param ipAddress is the remote IP address.
	 */
	public void connectToIP(String ipAddress) {
		try {
			_mainViewAdapter.appendInfo("Locating registry at " + ipAddress +"...\n");
			Registry remoteRegistry = rmiUtils.getRemoteRegistry(ipAddress);
			System.out.println("obtained remote resigtry");
			if (remoteRegistry.equals(localRegistry)) {
				System.out.println("same as local registry, using local registry");
				remoteRegistry = localRegistry;
			}
			_mainViewAdapter.appendInfo("Found registry : " + remoteRegistry + "\n");
			
//			IUser remoteUser = (IUser) remoteRegistry.lookup(IUser.BOUND_NAME);
//			users.add(new ProxyUser(remoteUser));
//			_mainViewAdapter.appendInfo("Found remote user stub: " + remoteUser + " on ip: " + ipAddress + "\n");
//			System.out.println("Found remote user stub: " + remoteUser + " on ip: " + ipAddress + "\n");
			
			users = new ArrayList<>();
			for (String name : remoteRegistry.list()) {
				System.out.println(name);
				IUser remoteUser = (IUser) remoteRegistry.lookup(name);
				if (currentUserStub.equals(remoteUser)) {
					users.add(currentUserStub);
				} else {
					users.add(new ProxyUser(remoteUser));
				}
				_mainViewAdapter.appendInfo("Found remote user stub: " + remoteUser + " on ip: " + ipAddress + "\n");
				System.out.println("Found remote user stub: " + remoteUser + " on ip: " + ipAddress + "\n");
			}
			
			_mainViewAdapter.listConnectedUsers(users);
			
		} catch (Exception e) {
			_mainViewAdapter.appendInfo("Error connecting to " + ipAddress + ": " + e + "\n");
			e.printStackTrace();
		}
	}

	/**
	 * invite a user to a chat room.
	 * @param user the user to invite.
	 */
	public void inviteUser(IUser user) {
		// TODO Auto-generated method stub
	}

	/**
	 * Request a list of chat rooms created by host.
	 * @param user is a selected user.
	 */
	public void requestChatRooms(IUser user) {
		try {
			if (currentUserStub.equals(user)) {
				_mainViewAdapter.listChatRooms(currentUser.getChatRooms());
				_mainViewAdapter.appendInfo("requesting joined chat room list to self \n");
			} else {
				_mainViewAdapter.listChatRooms(user.getChatRooms());
				_mainViewAdapter.appendInfo("requesting joined chat room list to user: " + user.getName() + "\n");
				user.connect(currentUserStub);
			}
		} catch (Exception e) {
			System.out.println("Error listing chat rooms from user " + user + "\n");
			e.printStackTrace();
		}
	}

	/**
	 * Create a Chat Room, then let the current user join this chat room.
	 * @param chatRoomName The name of the chat room.
	 */
	public void makeChatRoom(String chatRoomName) {
		IChatRoom chatRoom = new ChatRoom(chatRoomName, currentUserStub.toString());
		_mainViewAdapter.appendInfo("Successfully made a chat room: " + chatRoom + "\n");
		joinChatRoom(chatRoom);
	}
	
	/**
	 * Given a chat room, let the current join the chat room. 
	 * Create a chat room mini MVC using this chat room.
	 * @param chatRoom is the chat room to join.
	 */
	public void joinChatRoom(IChatRoom chatRoom) {
		try {
			// note here use currentUser not currentUserStub, which is crucial..
			if (currentUser.joinChatRoom(chatRoom)) {
				_mainViewAdapter.createChatRoomMVC(currentUserStub, chatRoom);
				_mainViewAdapter.appendInfo("Successfully joined the chat room: " + chatRoom + "\n");
			} else {
				_mainViewAdapter.selectChatRoom(chatRoom);
				_mainViewAdapter.appendInfo("Already has joined the chat room: " + chatRoom + "\n");
			}
		} catch (RemoteException e) {
			System.out.println("failed to join chat room. user: " + currentUserStub + " chat room: " + chatRoom);
			e.printStackTrace();
		}
	}
		
	/**
	 * Exit a chat room.
	 * @param chatRoom is the chat room to exit.
	 */
	public void exitChatRoom(IChatRoom chatRoom) {
		try {
			currentUser.exitChatRoom(chatRoom);
			_mainViewAdapter.removeChatRoomMVC(chatRoom);
		} catch (RemoteException e) {
			System.out.println("failed to exit chat room. user: " + currentUserStub + " chat room: " + chatRoom);
			e.printStackTrace();
		}
	}
	
	/**
	 * start the model, start RMI.
	 */
	public void start() {
		_mainViewAdapter.appendInfo("starting RMI... \n");
		rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER);
		_mainViewAdapter.appendInfo("Looking for registry... \n");
		localRegistry = rmiUtils.getLocalRegistry();
		_mainViewAdapter.appendInfo("Found registry: " + localRegistry + "\n");
	}
	
	/**
	 * exit the program.
	 */
	public void exit() {
		System.out.println("rmiUtils.stopRMI(): server is quitting");
		try {
			rmiUtils.stopRMI();
		}catch(Exception e) {
			System.err.println("rmiUtils.stopRMI(): Error when stopping server: "+ e);
			e.printStackTrace();
		}
		System.exit(0);
	}
}
