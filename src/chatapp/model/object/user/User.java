package chatapp.model.object.user;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import chatapp.model.IMainModel2MainViewAdapter;
import common.IChatRoom;
import common.IUser;

/**
 * a concrete user class.
 *
 */
public class User implements ICustomUser {
	
	/**
	 * User name.
	 */
	private String userName;
	
	/**
	 * User's uuid.
	 */
	private UUID uuid = UUID.randomUUID();
	
	/**
	 * The chat rooms the user has joined.
	 */
	protected Set<IChatRoom> chatRooms = new HashSet<IChatRoom>();
	
	/**
	 * Main model to main view adapter.
	 */
	private transient IMainModel2MainViewAdapter _mainViewAdapter;
	
	/**
	 * Constructor.
	 * @param userName is the name of the user.
	 * @param _mainViewAdapter is the user to view adapter.
	 */
	public User(String userName, IMainModel2MainViewAdapter _mainViewAdapter) {
		this.userName = userName;
		this._mainViewAdapter = _mainViewAdapter;
	}
	
	@Override
	public String getName() throws RemoteException {
		return userName;
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return uuid;
	}

	@Override
	public Collection<IChatRoom> getChatRooms() throws RemoteException {
		return chatRooms;
	}

	@Override
	public boolean joinChatRoom(IChatRoom chatRoom) throws RemoteException {
		return chatRooms.add(chatRoom);
	}
	
	@Override
	public boolean exitChatRoom(IChatRoom chatRoom) throws RemoteException {
		return chatRooms.remove(chatRoom);
	}
	
	@Override
	/**
	 * Auto connect back, to list the chat rooms of remote user.
	 */
	public void connect(IUser user) throws RemoteException {
		try {
			if (user.equals(this)) {
//				_mainViewAdapter.listChatRooms(this.getChatRooms());
				_mainViewAdapter.appendInfo("connected to self \n");
			} else {
//				_mainViewAdapter.listChatRooms(user.getChatRooms());
				_mainViewAdapter.appendInfo("connected to user: " + user.getName() + "\n");
			}
		} catch (RemoteException e) {
			System.out.println("remote exception when connecting remote user");
			e.printStackTrace();
		}
	}
}
