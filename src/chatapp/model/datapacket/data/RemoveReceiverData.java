package chatapp.model.datapacket.data;


import common.IReceiver;
import common.IRemoveReceiverType;

/**
 * This RemoveReceiverData is used for remove a receiver from a remote chat room. A receiver can receive 
 * a data packet with RemoveReceiverData inside and IRemoveReceiverType.class as type id, the execution of
 * this data packet will remove the receiver from getReceiverStub() from the chat room.
 *
 */
public class RemoveReceiverData implements IRemoveReceiverType {

	private static final long serialVersionUID = -2577528344552231078L;
	
	IReceiver receiver;

	/**
	 * Constructor.
	 * @param receiver The receiver to remove.
	 */
	public RemoveReceiverData(IReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public IReceiver getReceiverStub() {
		return receiver;
	}

}
