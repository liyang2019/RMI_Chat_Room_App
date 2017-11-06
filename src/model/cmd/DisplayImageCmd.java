package model.cmd;

import javax.swing.ImageIcon;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;

/**
 * The command to display the ImageIcon in the data packet.
 *
 */
public class DisplayImageCmd extends DataPacketAlgoCmd<ImageIcon>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4390262620406962086L;
	/**
	 * Command to chat room mode adapter.
	 */
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	
	/**
	 * Constructor.
	 * @param cmd2ModelAdapter is the command to chat room mode adapter.
	 */
	public DisplayImageCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}
	
	@Override
	public String apply(Class<?> index, DataPacketChatRoom<ImageIcon> host, String... params) {
		ImageIcon image = host.getData();
//		IReceiver sender = host.getSender();
//		Container panel = cmd2ModelAdapter.getContainer();
//		try {
//			panel.add(new JLabel(sender.getUserStub().getName() + "@" + sender.getUserStub().getUUID() + ":"));
//			panel.add(new JLabel(image));
//		} catch (RemoteException e) {
//			cmd2ModelAdapter.appendMsg("failed to display image from user: " + sender);
//			e.printStackTrace();
//		}
		cmd2ModelAdapter.displayImage(image);
		return image.toString();
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

}
