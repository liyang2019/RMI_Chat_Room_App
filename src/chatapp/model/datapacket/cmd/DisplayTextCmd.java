package chatapp.model.datapacket.cmd;

import java.rmi.RemoteException;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;
import common.IReceiver;

/**
 * The command to display the text in the data packet.
 *
 */
public class DisplayTextCmd extends DataPacketAlgoCmd<String> {

	private static final long serialVersionUID = -4146905191178168531L;
	
	/**
	 * Command to chat room mode adapter.
	 */
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	
	/**
	 * Constructor.
	 * @param cmd2ModelAdapter is the command to chat room mode adapter.
	 */
	public DisplayTextCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}
	
	@Override
	public String apply(Class<?> index, DataPacketChatRoom<String> host, String... params) {
		String text = (String) host.getData();
		IReceiver sender = host.getSender();
		try {
			cmd2ModelAdapter.appendMsg(text, sender.getUserStub().getName());
		} catch (RemoteException e) {
			System.out.println("Failed to get sender name with sender.getUserStub().getName()");
			e.printStackTrace();
		}
		return text;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

}
