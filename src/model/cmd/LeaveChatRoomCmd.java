package model.cmd;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;
import common.IReceiver;

/**
 * Leave chat room command. This command is used when one user wants to leave a chat room.
 * This user will send data packet with data type TypeLeaveChatRoom to all the receivers in the chat room, 
 * and this command will be executed by remote users to update the informations about this user.
 *
 */
public class LeaveChatRoomCmd extends DataPacketAlgoCmd<RemoveReceiverType> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4661703049739445045L;
	
	/**
	 * Command to chat room mode adapter.
	 */
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	
	/**
	 * Constructor.
	 * @param cmd2ModelAdapter is the command to chat room mode adapter.
	 */
	public LeaveChatRoomCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

	@Override
	public String apply(Class<?> index, DataPacketChatRoom<RemoveReceiverType> host, String... params) {
		IReceiver sender = host.getSender();
		cmd2ModelAdapter.removeReceiver(sender);
		return null;
	}
	
	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}
}
