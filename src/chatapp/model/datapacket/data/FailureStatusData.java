package chatapp.model.datapacket.data;

import common.DataPacketChatRoom;
import common.IFailureStatusType;

/**
 * The failure status data.
 */
public class FailureStatusData implements IFailureStatusType {

	private static final long serialVersionUID = -3067315889040770694L;
	private String info;
	private DataPacketChatRoom<?> data;
	
	/**
	 * Constructor.
	 * @param info The failure info.
	 * @param data The original sent data of this failure.
	 */
	public FailureStatusData(String info, DataPacketChatRoom<?> data) {
		this.info = info;
		this.data = data;
	}
	
	@Override
	public DataPacketChatRoom<?> getOriginalData() {
		return data;
	}

	@Override
	public String getFailureInfo() {
		return info;
	}

}
