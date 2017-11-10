package chatapp.model.datapacket.data;

import common.DataPacketAlgoCmd;
import common.IInstallCmdType;

/**
 * When send data packet to install a command into a remote receiver, the data is InstallCmdData, which
 * contains the command and the data type id for this command.
 */
public class InstallCmdData implements IInstallCmdType {

	private static final long serialVersionUID = 7254681078667406822L;
	private DataPacketAlgoCmd<?> cmd;
	private Class<?> id;
	
	/**
	 * Constructor.
	 * @param id The data type for the missing command.
	 * @param cmd The missing command.
	 */
	public InstallCmdData(Class<?> id, DataPacketAlgoCmd<?> cmd) {
		this.id = id;
		this.cmd = cmd;
	}
	
	@Override
	public DataPacketAlgoCmd<?> getCmd() {
		return cmd;
	}

	@Override
	public Class<?> getCmdId() {
		return id;
	}

}
