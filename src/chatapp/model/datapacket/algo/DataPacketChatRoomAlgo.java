package chatapp.model.datapacket.algo;

import common.DataPacketAlgoCmd;
import provided.datapacket.DataPacketAlgo;

/**
 * The data packet algo for chat room app.
 *
 */
public class DataPacketChatRoomAlgo extends DataPacketAlgo<String,String> {

	private static final long serialVersionUID = 8119780355923674125L;

	/**
	 * Constructor.
	 * @param defaultCmd is the data packet default command.
	 */
	public DataPacketChatRoomAlgo(DataPacketAlgoCmd<?> defaultCmd) {
		super(defaultCmd);
	}
}
