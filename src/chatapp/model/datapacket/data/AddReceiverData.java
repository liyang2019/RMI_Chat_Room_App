package chatapp.model.datapacket.data;


import common.IAddReceiverType;
import common.IReceiver;

/**
 * This AddReceiverData is used for add a receiver to a remote chat room. A receiver can receive 
 * a data packet with AddReceiverData inside and IAddReceiverType.class as type id, the execution of
 * this data packet will add the receiver from getReceiverStub() to the chat room.
 *
 */
public class AddReceiverData implements IAddReceiverType {

	private static final long serialVersionUID = -1549525003510266976L;
	IReceiver receiver;
	
	/**
	 * Constructor.
	 * @param receiver The receiver to add.
	 */
	public AddReceiverData(IReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public IReceiver getReceiverStub() {
		return receiver;
	}	
}