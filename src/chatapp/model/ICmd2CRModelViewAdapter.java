package chatapp.model;

import common.ICmd2ModelAdapter;
import common.IReceiver;

/**
 * Customized command to chat room model adapter.
 *
 */
public interface ICmd2CRModelViewAdapter extends ICmd2ModelAdapter {
	
	/**
	 * Add receiver to the chat room model.
	 * @param rcvr The receiver to add to chat room model.
	 */
	public void addReceiver(IReceiver rcvr);

	/**
	 * Remove receiver from chat room model.
	 * @param rcvr The receiver to remove from the chat room model.
	 */
	public void removeReceiver(IReceiver rcvr);

}
