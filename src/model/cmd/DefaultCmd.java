package model.cmd;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;

/**
 * The default cmd.
 *
 * @param <T> The data type held by the data packet.
 */
public class DefaultCmd<T> extends DataPacketAlgoCmd<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1488535773377760808L;
	
	/**
	 * Command to chat room view adapter.
	 */
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	
	/**
	 * Constructor.
	 * @param cmd2ModelAdapter is the command to chat room model adapter.
	 */
	public DefaultCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

	@Override
	public String apply(Class<?> index, DataPacketChatRoom<T> host, String... params) {
		cmd2ModelAdapter.appendMsg("default data packet command");
		return "default data packet command";
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

}
