package chatapp.model.datapacket.cmd;

import java.awt.Component;
import java.awt.Image;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;
import common.IComponentFactory;

/**
 * The command to display the ImageIcon in the data packet.
 *
 */
public class DisplayImageCmd extends DataPacketAlgoCmd<ImageIcon>{
	
	private static final long serialVersionUID = -4390262620406962086L;
	/**
	 * Command to chat room mode adapter.
	 */
	/**
	 * ImageIcon max height;
	 */
	private final int IMAGE_ICON_MAXHEIGHT = 150;
	
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
		ImageIcon icon = host.getData();
		try {
			cmd2ModelAdapter.buildScrollableComponent(new IComponentFactory() {
				@Override
				public Component makeComponent() {
					double aspectRatio = (double) icon.getIconWidth() / icon.getIconHeight();
					int newHeight = Math.min(icon.getIconHeight(), IMAGE_ICON_MAXHEIGHT);
					int newWidth = (int) (newHeight * aspectRatio);
					Image image = icon.getImage();
					ImageIcon resizedIcon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
					return new JLabel(resizedIcon);
				}
			}, host.getSender().getUserStub().getName());
		} catch (RemoteException e) {
			System.out.println("failed to get the sender's name, sender: " + host.getSender());
			e.printStackTrace();
		}
		return icon.toString();
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}
}
