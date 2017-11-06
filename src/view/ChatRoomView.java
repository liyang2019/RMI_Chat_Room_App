package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTextPane;

/**
 * The chat room view.
 * @param <UserObj> the user generic object.
 * @param <ChatRoomObj> chat room generic object.
 */
public class ChatRoomView<UserObj, ChatRoomObj> extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7822436832079216116L;
	
	/**
	 * ImageIcon max height;
	 */
	private static final int IMAGE_ICON_MAXHEIGHT = 150;
	
	/** 
	 * Chat room view to chat room model adapter.
	 * 
	 */
	private IChatRoomView2ModelAdapter<ChatRoomObj> _modelAdapter;
	private String chatRoomName;
	
	private final JTabbedPane tabbedPaneUpdateComp = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel pnlChatRoomUserList = new JPanel();
	private final JSplitPane splitPnlInput = new JSplitPane();
	private final JScrollPane spTextInput = new JScrollPane();
	private final JTextArea taInput = new JTextArea();
	private final JPanel pnlButtons = new JPanel();
	private final JButton btnSendText = new JButton("Send Text");
	private final JButton btnSendFile = new JButton("Send File");
	private final JButton btnSendEmoji = new JButton("Send Emoji");
	private final JButton btnExistRoom = new JButton("Exist Room");
	private final JScrollPane spUsers = new JScrollPane();
	private final JList<UserObj> userList = new JList<UserObj>();
	private final JScrollPane spChatRoom = new JScrollPane();
	private final JTextPane tpChatRoom = new JTextPane();
	
	/**
	 * @param _modelAdapter view to model adapter.
	 * @param chatRoomName the name of the chat room.
	 */
	public ChatRoomView(IChatRoomView2ModelAdapter<ChatRoomObj> _modelAdapter, String chatRoomName) {
		this._modelAdapter = _modelAdapter;
		this.chatRoomName = chatRoomName;
		initGUI();
	}
	
	private void initGUI() {
		setAlignmentX(0.1f);
		
		// for debug
		setBounds(100, 100, 500, 300);

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		add(tabbedPaneUpdateComp, BorderLayout.CENTER);
		
		tabbedPaneUpdateComp.addTab("New tab", null, spChatRoom, null);
		
		spChatRoom.setViewportView(tpChatRoom);
		add(pnlChatRoomUserList, BorderLayout.WEST);
		pnlChatRoomUserList.setLayout(new BorderLayout(0, 0));
		btnExistRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_modelAdapter.exitChatRoom();
			}
		});
		btnExistRoom.setToolTipText("Exist this chat room");
		
		pnlChatRoomUserList.add(btnExistRoom, BorderLayout.NORTH);
		spUsers.setToolTipText("Users in this chat room");
		
		pnlChatRoomUserList.add(spUsers, BorderLayout.CENTER);
		
		spUsers.setViewportView(userList);
		splitPnlInput.setToolTipText("input text, select files and emojis");
		
		add(splitPnlInput, BorderLayout.SOUTH);
		spTextInput.setToolTipText("");
		
		splitPnlInput.setLeftComponent(spTextInput);
		taInput.setToolTipText("input text here");
		
		spTextInput.setViewportView(taInput);
		
		splitPnlInput.setRightComponent(pnlButtons);
		splitPnlInput.setResizeWeight(1);
		pnlButtons.setLayout(new GridLayout(3, 0, 0, 0));
		btnSendText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendText(taInput.getText());
			}
		});
		btnSendText.setToolTipText("Send text");
		pnlButtons.add(btnSendText);
		btnSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendFile();
			}
		});
		btnSendFile.setToolTipText("Send files");
		pnlButtons.add(btnSendFile);
		btnSendEmoji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendEmoji();
			}
		});
		btnSendEmoji.setToolTipText("Send emojis");
		pnlButtons.add(btnSendEmoji);
	}
	
	/**
	 * Append text to chat room text area.
	 * @param text is the text to append to chat room text area.
	 */
	public void appendMessage(String text) {
		StyledDocument doc = tpChatRoom.getStyledDocument();
		//  Define a keyword attribute
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.RED);
		StyleConstants.setBackground(keyWord, Color.YELLOW);
		StyleConstants.setBold(keyWord, true);
		//  Add some text
		try {
			doc.insertString(doc.getLength(), text + "\n", keyWord);
		} catch(Exception e) {
			System.out.println("failed to append message to chat room");
			e.printStackTrace();
		}
	}
	
	/**
	 * Display icon in the chat room textPane.
	 * @param icon is the icon to display.
	 */
	public void displayImageIcon(ImageIcon icon) {
		Image image = icon.getImage();
		double aspectRatio = (double) icon.getIconWidth() / icon.getIconHeight();
		int newWidth = (int) (IMAGE_ICON_MAXHEIGHT * aspectRatio);
		ImageIcon resizedIcon = new ImageIcon(image.getScaledInstance(newWidth, IMAGE_ICON_MAXHEIGHT, Image.SCALE_DEFAULT));
		
		StyledDocument doc = tpChatRoom.getStyledDocument();
	    Style style = doc.addStyle("Icon", null);
	    StyleConstants.setIcon(style, resizedIcon);
	    try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch(Exception e) {
			System.out.println("failed to append image to chat room");
			e.printStackTrace();
		}
	}

	/**
	 * list users in this chat room in the left text area.
	 * @param users the users in this chat room.
	 */
	public void listUsers(UserObj[] users) {
		userList.setListData(users);
	}
	
	/** 
	 * get the name of this chat room view component.
	 */
	@Override
	public String getName() {
		return chatRoomName;
	}
	
	/**
	 * Send a message to this chat room.
	 * @param text is the text to send to the chat room.
	 */
	public void sendText(String text) {
		_modelAdapter.sendText(text);
		appendMessage("You: " + text);
	}
	
	/**
	 * Send a file to this chat room.
	 */
	public void sendFile() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			_modelAdapter.sendFile(file);
		} else {
			System.out.println("Cancelled");
		}
	}
	

	
	/**
	 * Send a emoji to this chat room.
	 */
	public void sendEmoji() {
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "png", "bmp", "gif"));
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			ImageIcon icon = new ImageIcon(file.getAbsolutePath());			
			_modelAdapter.sendEmoji(icon);
			appendMessage("You have sent the image: " + icon);
			displayImageIcon(icon);
		} else {
			System.out.println("Cancelled");
		}
	}
	
	/**
	 * start the GUI.
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * Get chat room generic object from model adapter.
	 * @return chat room generic object.
	 */
	public ChatRoomObj getChatRoom() {
		return _modelAdapter.getChatRoom();
	}
	
//	/**
//	 * Launch the application.
//	 * @param args input arguments.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					(new ChatRoomView<>(null, "test room")).start();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}