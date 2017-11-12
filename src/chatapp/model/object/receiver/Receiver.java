package chatapp.model.object.receiver;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ImageIcon;

import chatapp.model.ICmd2CRModelViewAdapter;
import chatapp.model.datapacket.algo.DataPacketChatRoomAlgo;
import chatapp.model.datapacket.cmd.DisplayImageCmd;
import chatapp.model.datapacket.cmd.DisplayTextCmd;
import chatapp.model.datapacket.cmd.SaveFileCmd;
import chatapp.model.datapacket.data.InstallCmdData;
import chatapp.model.datapacket.data.RequestCmdData;
import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.IAddReceiverType;
import common.ICmd2ModelAdapter;
import common.IExceptionStatusType;
import common.IFailureStatusType;
import common.IInstallCmdType;
import common.IReceiver;
import common.IRejectionStatusType;
import common.IRemoveReceiverType;
import common.IRequestCmdType;
import common.IUser;
import provided.datapacket.DataPacketAlgo;

/**
 * The receiver for a chat room.
 *
 */
public class Receiver implements IReceiver, Serializable {
		
	private static final long serialVersionUID = -80343119221410606L;
	/**
	 * The userStub for this receiver.
	 */
	private IUser userStub;
	/**
	 * The uuid of this receiver.
	 */
	private UUID uuid = UUID.randomUUID();
	/**
	 * The command to chat room model adapter.
	 */
	private transient ICmd2CRModelViewAdapter cmd2CRModelViewAdapter;
	/**
	 * The algo to execute data packets the receiver received.
	 */
	private DataPacketAlgo<String,String> algo;
	
	/**
	 * Data packet cache. When a received data packet is found missing corresponding command,
	 * A IRequestCmdType containing data type id will be sent to the sender of the datapacket. Then the sender will
	 * send back a IInstallCmdType containing data type id and cmd. After received IInstallCmdType, the cached data
	 * packet will be executed.
	 */
	private Map<Class<?>, List<DataPacketChatRoom<?>>> cachedPackets = new HashMap<>();
	
	/**
	 * Constructor.
	 * @param userStub is the userStub of this receiver.
	 * @param cmd2CRModelViewAdapter is the receiver to chat room model adapter.
	 */
	public Receiver(IUser userStub, ICmd2CRModelViewAdapter cmd2CRModelViewAdapter) {
		this.userStub = userStub;
		this.cmd2CRModelViewAdapter = cmd2CRModelViewAdapter;
		// well known algos.
		// initialize the algo and set the default command.
		algo = new DataPacketChatRoomAlgo(new DataPacketAlgoCmd<Object>() {
			private static final long serialVersionUID = 3547760123525399003L;
			@Override
			public String apply(Class<?> id, DataPacketChatRoom<Object> host, String... params) {
				System.out.println("default cmd host class: " + host.getClass());
				List<DataPacketChatRoom<?>> list = cachedPackets.get(id);
				list = list == null ? new ArrayList<DataPacketChatRoom<?>>() : list;
				list.add(host);
				cachedPackets.put(id, list);
				IReceiver sender = host.getSender();
				Receiver.this.cmd2CRModelViewAdapter.appendMsg("missing cmd, requesting cmd from sender: " + sender, sender.toString());
				try {
					sender.receive(new DataPacketChatRoom<IRequestCmdType>(IRequestCmdType.class, new RequestCmdData(id), Receiver.this));
				} catch (RemoteException e) {
					System.out.println("failed to request cmd from sender: + " + sender);
					e.printStackTrace();
				}
				return null;
			}
			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {}
		});
		// add receiver command.
		algo.setCmd(IAddReceiverType.class, new DataPacketAlgoCmd<IAddReceiverType>() {

			private static final long serialVersionUID = 445855638505927139L;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IAddReceiverType> host, String... params) {
				IReceiver receiver = host.getData().getReceiverStub();
				Receiver.this.cmd2CRModelViewAdapter.addReceiver(receiver);
				return "receiver: " + receiver + " added";
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {}
			
		});
		// remove receiver command
		algo.setCmd(IRemoveReceiverType.class, new DataPacketAlgoCmd<IRemoveReceiverType>() {

			private static final long serialVersionUID = -5292044967833187117L;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IRemoveReceiverType> host, String... params) {
				IReceiver sender = host.getSender();
				Receiver.this.cmd2CRModelViewAdapter.removeReceiver(sender);
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {}
			
		});
		// execute failure status command
		algo.setCmd(IFailureStatusType.class, new DataPacketAlgoCmd<IFailureStatusType>() {

			private static final long serialVersionUID = -1857161355501646599L;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IFailureStatusType> host, String... params) {
				cmd2CRModelViewAdapter.appendMsg(host.getData().getFailureInfo(), host.getSender().toString());
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {}
		});
		// request cmd command
		algo.setCmd(IRequestCmdType.class, new DataPacketAlgoCmd<IRequestCmdType>() {

			private static final long serialVersionUID = -2609602603430750073L;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IRequestCmdType> host, String... params) {
				IReceiver sender = host.getSender();
				Class<?> id = host.getData().getCmdId();
				DataPacketAlgoCmd<?> cmd = (DataPacketAlgoCmd<?>) algo.getCmd(id);
				try {
					sender.receive(new DataPacketChatRoom<IInstallCmdType>(
							IInstallCmdType.class, 
							new InstallCmdData(id, cmd), 
							Receiver.this));
				} catch (RemoteException e) {
					System.out.println("failed to send cmd back to receiver: " + Receiver.this + " for install");
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {}
		});
		// install cmd command
		algo.setCmd(IInstallCmdType.class, new DataPacketAlgoCmd<IInstallCmdType>() {
			
			private static final long serialVersionUID = 2773448536986212690L;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IInstallCmdType> host, String... params) {
				IInstallCmdType data = host.getData();
				Class<?>  id = data.getCmdId();
				DataPacketAlgoCmd<?> cmd = data.getCmd();
				cmd.setCmd2ModelAdpt(cmd2CRModelViewAdapter);
				System.out.println("---------" + id);
				System.out.println("cmd" + cmd);
				algo.setCmd(id, cmd);
				List<DataPacketChatRoom<? extends Object>> list = cachedPackets.remove(id);
				for (DataPacketChatRoom<? extends Object> d : list) {
					cmd.apply(id, d);
				}
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {}
		});
		// exception status command.
		algo.setCmd(IExceptionStatusType.class, new DataPacketAlgoCmd<IExceptionStatusType>() {
			
			private static final long serialVersionUID = -8251873133456388224L;
			private transient ICmd2ModelAdapter cmd2ModelAdpt;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IExceptionStatusType> host, String... params) {
				String failureInfo = host.getData().getFailureInfo();
				IReceiver sender = host.getSender();
				String senderName = "";
				try {
					senderName = sender.getUserStub().getName();
				} catch (RemoteException e) {
					System.out.println("failed to get sender name, sender: " + sender);
					e.printStackTrace();
				}
				cmd2ModelAdpt.appendMsg("data packet exception with info: " + failureInfo, senderName);
				return failureInfo;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
					this.cmd2ModelAdpt = cmd2ModelAdpt;
			}
		});
		// rejection status command.
		algo.setCmd(IRejectionStatusType.class, new DataPacketAlgoCmd<IRejectionStatusType>() {

			private static final long serialVersionUID = 1928526090101061225L;
			private transient ICmd2ModelAdapter cmd2ModelAdpt;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IRejectionStatusType> host, String... params) {
				String failureInfo = host.getData().getFailureInfo();
				IReceiver sender = host.getSender();
				String senderName = "";
				try {
					senderName = sender.getUserStub().getName();
				} catch (RemoteException e) {
					System.out.println("failed to get sender name, sender: " + sender);
					e.printStackTrace();
				}
				cmd2ModelAdpt.appendMsg("data packet rejected with info: " + failureInfo, senderName);
				return failureInfo;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				this.cmd2ModelAdpt = cmd2ModelAdpt;
			}
		});
		// failure status command
		// rejection status command.
		algo.setCmd(IFailureStatusType.class, new DataPacketAlgoCmd<IFailureStatusType>() {

			private static final long serialVersionUID = -2093682637800309729L;
			private transient ICmd2ModelAdapter cmd2ModelAdpt;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IFailureStatusType> host, String... params) {
				String failureInfo = host.getData().getFailureInfo();
				IReceiver sender = host.getSender();
				String senderName = "";
				try {
					senderName = sender.getUserStub().getName();
				} catch (RemoteException e) {
					System.out.println("failed to get sender name, sender: " + sender);
					e.printStackTrace();
				}
				cmd2ModelAdpt.appendMsg("data packet failure with info: " + failureInfo, senderName);
				return failureInfo;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				this.cmd2ModelAdpt = cmd2ModelAdpt;
			}
		});
		// image, string and file type
		algo.setCmd(ImageIcon.class, new DisplayImageCmd(cmd2CRModelViewAdapter));
		algo.setCmd(String.class, new DisplayTextCmd(cmd2CRModelViewAdapter));
		algo.setCmd(File.class, new SaveFileCmd(cmd2CRModelViewAdapter));
	}

	@Override
	public <T> void receive(DataPacketChatRoom<T> data) throws RemoteException {
		data.execute(algo);
	}

	@Override
	public IUser getUserStub() throws RemoteException {
		return userStub;
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return uuid;
	}
}
