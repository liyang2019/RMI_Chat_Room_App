package common;

import provided.datapacket.ADataPacketAlgoCmd;

/**
 * Specialized data packet processing command that uses DataPacketChatRoom&lt;T&gt; and ICmd2ModelAdapters.
 * Here we specify return data type is String, and the input parameter type is also String.
 * @param <T>  The data in the data packet
 */
public abstract class DataPacketAlgoCmd<T> extends ADataPacketAlgoCmd<String, T, String, ICmd2ModelAdapter, DataPacketChatRoom<T>> {

	private static final long serialVersionUID = 3493108470390456052L;
	
}
