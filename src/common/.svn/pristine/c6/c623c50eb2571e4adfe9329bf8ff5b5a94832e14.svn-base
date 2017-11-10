package common;

import java.io.Serializable;

/**
 * Common data type for failure status. The failure status should be wrapped in a standard data packet
 * which can be processed by a DataPacketAlgoCmd
 * Due to asynchronous design, the receiver only sends a status back if there is an error.
 * @param <T> the type of failure info
 */
public interface IFailureStatusType<T> extends Serializable {
	
	/**
	 * The corresponding original data for the failure status
	 * @return the original data
	 */
	public DataPacketChatRoom<?> getOriginalData();
	
	/**
	 * The detailed failure information
	 * @return the detailed failure info
	 */
	public T getFailureInfo();
}
