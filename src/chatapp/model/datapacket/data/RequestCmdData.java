package chatapp.model.datapacket.data;

import common.IRequestCmdType;

/**
 * When a receiver found it missing a command, it will send a data packet with
 * RequestCmdData inside to the sender to request a command.
 *
 */
public class RequestCmdData implements IRequestCmdType {

	private static final long serialVersionUID = -1446089909740147970L;
	private Class<?> id;
	
	/**
	 * Constructor.
	 * @param id The command id.
	 */
	public RequestCmdData(Class<?> id) {
		this.id = id;
	}
	
	@Override
	public Class<?> getCmdId() {
		return id;
	}
	
}
