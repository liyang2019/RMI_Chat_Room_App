package chatapp.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

/**
 * The chat room GUI view.
 * @param <UserObj> the object in the user list.
 * @param <ChatRoomObj> the object in the chat room list.
 */
public class MainView<UserObj, ChatRoomObj> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3027338569906051915L;
	
	private IMainView2MainModelAdapter<UserObj, ChatRoomObj> _view2ModelAdapter;
	private Map<ChatRoomObj, Component> chatRoomViewMap = new HashMap<>();
	
	private JPanel contentPane = new JPanel();;
	private final JTextField tfUserName = new JTextField("Li Yang");;
	private JTextField tfIP = new JTextField();;
	private JTextField tfChatRoomName = new JTextField("Earth");;
	
	private final JPanel panel_top = new JPanel();
	private final JLabel lblUsername = new JLabel("Username:");
	private final JPanel pnlConnectToIP = new JPanel();
	private final JButton btnLogIn = new JButton("Log In");
	private final JPanel pnlConnectedUsers = new JPanel();
	private final JPanel pnlMakeChatRoom = new JPanel();
	private final JButton btnMakeIt = new JButton("Make");
	private final JButton btnConnect = new JButton("Connect");
	private final JComboBox<UserObj> cbUsers = new JComboBox<>();
	private final JButton btnRequest = new JButton("Request");
	private final JPanel panel_4 = new JPanel();
	private final JButton btnExit = new JButton("EXIT");
	private final JTabbedPane panel_tab = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel pnlInfo = new JPanel();
	
	private final JPanel pnlChooseRoom = new JPanel();
	private final JComboBox<ChatRoomObj> cbChatRooms = new JComboBox<>();
	private final JButton btnJoin = new JButton("Join");
	
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea taInfo = new JTextArea();
	private final JPanel pnlLogIn = new JPanel();
	private final JTextField tfServerName = new JTextField("USER");
	private final JLabel lblServername = new JLabel("Servername:");
	private final JPanel pnlConnect = new JPanel();

	/**
	 * Create the frame.
	 * @param _view2ModelAdapter the view to model adapter.
	 */
	public MainView(IMainView2MainModelAdapter<UserObj, ChatRoomObj> _view2ModelAdapter) {
		tfServerName.setToolTipText("input the server name to bind the user to registry");
		tfServerName.setColumns(10);
		this._view2ModelAdapter = _view2ModelAdapter;
		initGUI();
	}

	private void initGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(panel_top, BorderLayout.NORTH);
		pnlLogIn.setBorder(new TitledBorder(null, "Log In", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlLogIn.setToolTipText("log in panel");
		
		panel_top.add(pnlLogIn);
		pnlLogIn.setLayout(new GridLayout(0, 1));
		pnlLogIn.add(lblUsername);
		pnlLogIn.add(tfUserName);
		tfUserName.setToolTipText("input user name");
		tfUserName.setColumns(10);
		
		pnlLogIn.add(lblServername);
		
		pnlLogIn.add(tfServerName);
		pnlLogIn.add(btnLogIn);
		btnLogIn.setToolTipText("log in to start the user instance");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdapter.startServer(tfUserName.getText(), tfServerName.getText());
			}
		});
		pnlMakeChatRoom.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Make New Chat Room", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_top.add(pnlMakeChatRoom);
		pnlMakeChatRoom.setLayout(new GridLayout(2, 0, 0, 0));
		pnlMakeChatRoom.add(tfChatRoomName);
		tfChatRoomName.setColumns(12);
		btnMakeIt.setToolTipText("press to make a new chat room");
		btnMakeIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdapter.makeChatRoom(tfChatRoomName.getText());
			}
		});
		pnlMakeChatRoom.add(btnMakeIt);
		
		panel_top.add(pnlConnect, new GridLayout(0, 1, 0, 0));
		pnlConnect.setLayout(new GridLayout(2, 1));
		pnlConnect.add(pnlConnectToIP);
		pnlConnectToIP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Connect to IP", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlConnectToIP.setLayout(new GridLayout(0, 1, 0, 0));
		pnlConnectToIP.add(tfIP);
		tfIP.setColumns(12);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdapter.connectToIP(tfIP.getText());
			}
		});
		pnlConnectToIP.add(btnConnect);
		pnlConnect.add(pnlConnectedUsers);
		pnlConnectedUsers.setToolTipText("connected users panel");
		pnlConnectedUsers.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Connected Users", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlConnectedUsers.setLayout(new GridLayout(0, 1, 0, 0));
		cbUsers.setToolTipText("connected users will listed here");
		pnlConnectedUsers.add(cbUsers);
		pnlConnectedUsers.setPreferredSize(new Dimension(150, 80));// hardCoded sizing
		pnlConnectedUsers.setMaximumSize(new Dimension(250, 80));  // hardCoded sizing
		pnlConnectedUsers.setMinimumSize(new Dimension(150, 80));
		btnRequest.setToolTipText("request to list the chat rooms a selected user has joined");
		btnRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdapter.requestChatRoomList(cbUsers.getItemAt(cbUsers.getSelectedIndex()));
			}
		});
		pnlConnectedUsers.add(btnRequest);
		pnlChooseRoom.setToolTipText("choose chat room panel");
		
		pnlChooseRoom.setPreferredSize(new Dimension(150, 80));// hardCoded sizing
		pnlChooseRoom.setMaximumSize(new Dimension(250, 80));  // hardCoded sizing
		pnlChooseRoom.setMinimumSize(new Dimension(150, 80));  // hardCoded sizing
		pnlChooseRoom.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Choose a Room", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		panel_top.add(pnlChooseRoom);
		pnlChooseRoom.setLayout(new GridLayout(0, 1, 0, 0));
		cbChatRooms.setToolTipText("the chat rooms the selected user has joined will listed here after request");
		
		pnlChooseRoom.add(cbChatRooms);
		btnJoin.setToolTipText("press to join a chat room");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdapter.joinChatRoom(cbChatRooms.getItemAt(cbChatRooms.getSelectedIndex()));
			}
		});
		
		pnlChooseRoom.add(btnJoin);
		panel_top.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		btnExit.setToolTipText("exit the chat room app");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdapter.exit();
			}
		});
		panel_4.add(btnExit);
		contentPane.add(panel_tab, BorderLayout.CENTER);
		panel_tab.addTab("Info", null, pnlInfo, null);
		pnlInfo.setLayout(new BorderLayout(0, 0));
		pnlInfo.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(taInfo);
	}
	
	/**
	 * append info in the info component.
	 * @param text the text to append in the info component.
	 */
	public void appendInfo(String text) {
		taInfo.append(text);
	}
	
	/**
	 * Add a chat room view in the JTabbedPane. 
	 * @param chatRoomView the chat room view to add.
	 */
	public void addChatRoomView(ChatRoomView<UserObj, ChatRoomObj> chatRoomView) {
		panel_tab.addTab(chatRoomView.getName(), null, chatRoomView, null);
		chatRoomViewMap.put(chatRoomView.getChatRoom(), chatRoomView);
		panel_tab.setSelectedComponent(chatRoomView);
	}
	
	/**
	 * Remove a chat room view in the JTabbedPane.
	 * @param chatRoom the chat room to remove.
	 */
	public void removeChatRoomView(ChatRoomObj chatRoom) {
		panel_tab.remove(chatRoomViewMap.remove(chatRoom));
	}
	
	/**
	 * List hosts in the combo box.
	 * @param users the items to put in the combo box.
	 */
	public void listConnectedHosts(List<UserObj> users) {
		cbUsers.removeAllItems();
		for (UserObj user: users) {
			cbUsers.addItem(user);
		}
		if (cbUsers.getItemCount() > 0) {
			cbUsers.setSelectedIndex(0);
		}
	}

	/**
	 * list the chat rooms the selected remote host created or joined.
	 * @param chatRoomLIst a list of chat rooms.
	 */
	public void listChatRooms(Iterable<ChatRoomObj> chatRoomLIst) {
		cbChatRooms.removeAllItems();
		for (ChatRoomObj chatRoom : chatRoomLIst) {
			cbChatRooms.addItem(chatRoom);
		}
		if (cbChatRooms.getItemCount() > 0) {
			cbChatRooms.setSelectedIndex(0);
		}
	}
	
	/**
	 * start the GUI.
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * High light an already join chat room view.
	 * @param chatRoom is the chat room the user already joined.
	 */
	public void seletChatRoom(ChatRoomObj chatRoom) {
		panel_tab.setSelectedComponent(chatRoomViewMap.get(chatRoom));
	}

	/**
	 * Display IP adress in connect to IP panel.
	 * @param localAddress The IP adress.
	 */
	public void displayIP(String localAddress) {
		tfIP.setText(localAddress);
	}
}