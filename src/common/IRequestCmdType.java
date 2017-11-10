package common;

import java.io.Serializable;

/**
 * Common data type for sending a message that 
 * a specific command is needed for processing a certain type of data packet.
 * When a receiver receives a packet which himself/herself cannot handle, 
 * he/she will send a IRequestCmdType back to the sender.
 */
public interface IRequestCmdType extends Serializable {
	
	/**
	 * Get the id of the command, which is the Class of the data in the packet
	 * @return the id of the command
	 */
	public Class<?> getCmdId();
}
