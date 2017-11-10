package common;

import java.io.Serializable;

/**
 * Common data type for transmitting command between remote peers.
 * Typically, when a receiver receives such a message (Due to the receiver sends a IRequestCmdType message before), 
 * it installs the command into the visitor.
 */
public interface IInstallCmdType extends Serializable {

	/**
	 *  Get the command
	 * @return the command
	 */
	public DataPacketAlgoCmd<?> getCmd();
	
	/**
	 * Get the command id
	 * @return the command id
	 */
	public Class<?> getCmdId();
}
