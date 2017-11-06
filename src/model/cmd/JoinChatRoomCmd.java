package model.cmd;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;
import common.IReceiver;

/**
 * Join chat room command. This command is used when one user wants to join a chat room.
 * This user will send data packet with data type TypeJoinChatRoom to all the receivers in the chat room, 
 * and this command will be executed by remote users to update the informations about this user.
 *
 *
 */
public class JoinChatRoomCmd extends DataPacketAlgoCmd<AddReceiverType> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 472355148803370877L;
	
	/**
	 * Command to chat room mode adapter.
	 */
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	
	/**
	 * Constructor.
	 * @param cmd2ModelAdapter is the command to chat room mode adapter.
	 */
	public JoinChatRoomCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

	@Override
	public String apply(Class<?> index, DataPacketChatRoom<AddReceiverType> host, String... params) {
		IReceiver sender = host.getSender();
		cmd2ModelAdapter.addReceiver(sender);
		return null;
	}
	
	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}
}