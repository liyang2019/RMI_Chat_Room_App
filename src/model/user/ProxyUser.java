package model.user;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

import common.IChatRoom;
import common.IUser;

/**
 * The proxy of IUser.
 *
 */
public class ProxyUser implements IUser, Serializable {

	private static final long serialVersionUID = -4680479802913331855L;
	private IUser user;
	private String userName; // to store the user name, no need get it remotely;
	private UUID uuid; // to store the user's uuid, no need get it remotely;
	
	/**
	 * Constructor.
	 * @param user is the user in proxy user.
	 */
	public ProxyUser(IUser user) {
		this.user = user;
		try {
			userName = user.getName(); // get the user name remotely.
			uuid = user.getUUID(); // get the user's uuid remotely.
		} catch (RemoteException e) {
			System.out.println("failed to get userName or UUID when constructing proxy user from: " + user);
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return userName;
	}

	@Override
	public Iterable<IChatRoom> getChatRooms() throws RemoteException {
		return user.getChatRooms();
	}
	
	/**
	 * Overridden equals() method to compare UUID's
	 * @return  Equal if UUID's are equal.  False otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
//		if (this == obj) return true;
//		if (!(obj instanceof IUser)) return false;
//		IUser that = (IUser) obj;
//		try {
//			// Equality of IUsers is same as equality of UUID's.
//			return this.getUUID().equals(((IUser)that).getUUID());
//		} catch (RemoteException e) {
//			// Deal with the exception without throwing a RemoteException.
//			System.err.println("ProxyUser.equals(): error getting UUID: "+ e);
//			e.printStackTrace();
//			// Fall through and return false
//		}
//		return false;
		if (this == obj) return true;
		if (!(obj instanceof IUser)) return false;
		IUser that = (IUser) obj;
		try {
			return this.uuid.equals(((IUser)that).getUUID());
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
//		try {
//			// hashCode is shorter than UUID, but Java spec says that if two objects are equal then
//			// their hashCodes must also be equal, which will be true here since equals() is based on 
//			// UUID equality.  Java does NOT require that unequal entities have unequal hashCodes. 
//			return this.getUUID().hashCode();
//		} catch (RemoteException e) {
//			// Deal with the exception without throwing a RemoteException.
//			System.err.println("ProxyUser.hashCode(): Error calling remote method on IUser stub: "+e);
//			e.printStackTrace();
//			return super.hashCode();  // return some sort of hashCode
//		}
		return uuid.hashCode();
	}
	
	@Override
	public String toString() {
		return userName;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public void connect(IUser userStub) throws RemoteException {
		user.connect(userStub);
	}
}