package chatapp.model.datapacket.cmd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;
import common.IComponentFactory;

/**
 * The command to display the ImageIcon in the data packet.
 *
 */
public class SaveFileCmd extends DataPacketAlgoCmd<File> {
	
	private static final long serialVersionUID = 4800502814176277735L;
	/**
	 * Command to chat room mode adapter.
	 */
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	
	/**
	 * Constructor.
	 * @param cmd2ModelAdapter is the command to chat room mode adapter.
	 */
	public SaveFileCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

	@Override
	public String apply(Class<?> index, DataPacketChatRoom<File> host, String... params) {
		File file = host.getData();
		String senderName = "";
		try {
			senderName = host.getSender().getUserStub().getName();
		} catch (RemoteException e) {
			System.out.println("failed to get the sender's name, sender: " + host.getSender());
			e.printStackTrace();
		}
		cmd2ModelAdapter.appendMsg(file.getName(), senderName + " sent the file");
		cmd2ModelAdapter.buildNonScrollableComponent(new IComponentFactory() {
			@Override
			public Component makeComponent() {
				JPanel content = new JPanel();
				JPanel pnlSaveFile = new JPanel();
				content.add(pnlSaveFile, BorderLayout.SOUTH);
				pnlSaveFile.setPreferredSize(new Dimension(150, 80));
				pnlSaveFile.setMinimumSize(new Dimension(150, 80));
				pnlSaveFile.setLayout(new GridLayout(0, 1));
				
				JLabel lblFileName = new JLabel();
				pnlSaveFile.add(lblFileName);
				
				lblFileName.setText("received file: " + file.getName());
				JButton btnSave = new JButton("Save");
				pnlSaveFile.add(btnSave);
				
				JLabel lblFilePath = new JLabel();
				pnlSaveFile.add(lblFilePath);
				
				btnSave.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser c = new JFileChooser();
						int rVal = c.showSaveDialog(pnlSaveFile);
						if (rVal == JFileChooser.APPROVE_OPTION) {
							lblFilePath.setText("saved to: " + c.getCurrentDirectory().toString());
						}
					}
				});
				return content;
			}
		}, senderName);
		return file.getName();
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdapter) {
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

}
