package model.receiver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

import javax.swing.ImageIcon;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.IAddReceiver;
import common.ICmd2ModelAdapter;
import common.IReceiver;
import common.IRemoveReceiver;
import common.IUser;
import common.ReturnStatus;
import model.algo.DataPacketChatRoomAlgo;
import model.cmd.DefaultCmd;
import model.cmd.DisplayImageCmd;
import model.cmd.DisplayTextCmd;
import model.cmd.JoinChatRoomCmd;
import model.cmd.LeaveChatRoomCmd;

/**
 * The receiver for a chat room.
 *
 */
public class Receiver implements IReceiver, Serializable {
		
	private static final long serialVersionUID = -80343119221410606L;
	/**
	 * The userStub for this receiver.
	 */
	private IUser userStub;
	/**
	 * The uuid of this receiver.
	 */
	private UUID uuid = UUID.randomUUID();
	/**
	 * The command to chat room model adapter.
	 */
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	/**
	 * The algo to execute data packets the receiver received.
	 */
	private DataPacketChatRoomAlgo algo;
	/**
	 * Constructor.
	 * @param userStub is the userStub of this receiver.
	 * @param cmd2ModelAdapter is the receiver to chat room model adapter.
	 */
	public Receiver(IUser userStub, ICmd2ModelAdapter cmd2ModelAdapter) {
		this.userStub = userStub;
		this.cmd2ModelAdapter = cmd2ModelAdapter;
		// set the well know algos
		algo = new DataPacketChatRoomAlgo(new DefaultCmd<>(cmd2ModelAdapter));
		algo.setCmd(ImageIcon.class, new DisplayImageCmd(cmd2ModelAdapter));
		algo.setCmd(String.class, new DisplayTextCmd(cmd2ModelAdapter));
		algo.setCmd(IAddReceiver.class, new JoinChatRoomCmd(cmd2ModelAdapter));
		algo.setCmd(IRemoveReceiver.class, new LeaveChatRoomCmd(cmd2ModelAdapter));
	}

	@Override
	public <T> ReturnStatus receive(DataPacketChatRoom<T> data) throws RemoteException {
		Class<?> id = data.getData().getClass();
		IReceiver sender = data.getSender();
		DataPacketAlgoCmd<?> cmd = this.getCmd(id);
		if (cmd == null) {
			System.out.println("missing cmd, request cmd from sender: " + sender);
			cmd = sender.getCmd(id);
			cmd = cmd == null ? new DefaultCmd<T>(cmd2ModelAdapter) : cmd;
		}
		cmd.setCmd2ModelAdpt(cmd2ModelAdapter);
		algo.setCmd(id, cmd);
		data.execute(algo);
		return ReturnStatus.SUCCESS;
	}
	
	@Override
	public DataPacketAlgoCmd<?> getCmd(Class<?> id) {
		return (DataPacketAlgoCmd<?>) algo.getCmd(id);
	}

	@Override
	public IUser getUserStub() throws RemoteException {
		return userStub;
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return uuid;
	}
}
