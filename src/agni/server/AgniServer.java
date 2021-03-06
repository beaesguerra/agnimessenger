package agni.server;

import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import agni.client.action.InfoRequestActionHandler;
import agni.server.communication.MessageReceiver;
import agni.server.communication.MessageSender;
import agni.server.dataguard.GroupChatDataGuard;
import agni.server.dataguard.I_FileDataGuard;
import agni.server.dataguard.I_GroupChatDataGuard;
import agni.server.dataguard.FileDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.manager.ChatManager;
import agni.server.manager.FileManager;
import agni.server.manager.HeartbeatManager;
import agni.server.manager.InfoRequestManager;
import agni.server.manager.LoginManager;
import agni.server.manager.StatusManager;
import agni.server.manager.UserManager;
import agni.server.receiver.ChatReceiver;
import agni.server.receiver.FileReceiver;
import agni.server.receiver.HeartbeatReceiver;
import agni.server.receiver.InfoRequestReceiver;
import agni.server.receiver.LoginReceiver;
import agni.server.receiver.UserReceiver;
import agni.server.sender.ChatSender;
import agni.server.sender.FileSender;
import agni.server.sender.HeartbeatSender;
import agni.server.sender.InfoSender;
import agni.server.sender.StatusSender;
import agni.server.communication.ChannelList;
import agni.server.communication.IpChannelPair;

public class AgniServer {
	
	private static String serverIpAddress = "162.246.157.203"; 
	private static String serverPort = "9001"; 
	private static String serverName = "AgniMessenger Server";
	
	
	public static String getServerIp() {
		return serverIpAddress; 
	}
	
	public static String getServerPort() { 
		return serverPort; 
	}
	public static String getServerName() {
		return serverName;
	}
	
	

    public static void main(String[] args) throws SQLException {

        ChannelList channels = new ChannelList();
        MessageSender messageSender = new MessageSender(channels);

        InfoSender infoSender = new InfoSender(messageSender);
        ChatSender chatSender = new ChatSender(messageSender);
        FileSender fileSender = new FileSender(messageSender);
        StatusSender statusSender = new StatusSender(messageSender);
        HeartbeatSender heartbeatSender = new HeartbeatSender(messageSender);

        // TODO 
        UserDataGuard userDataGuard = null;
		try {
			userDataGuard = new UserDataGuard("agni", "agni", "");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        GroupChatDataGuard chatDataGuard = new GroupChatDataGuard("agni", "agni", "");
        FileDataGuard fileDataGuard = new FileDataGuard("agni", "agni", "");


        LoginReceiver loginReceiver = new LoginReceiver();
        UserReceiver userReceiver = new UserReceiver();
        ChatReceiver chatReceiver = new ChatReceiver();
        FileReceiver fileReceiver = new FileReceiver();
        InfoRequestReceiver infoRequestReceiver = new InfoRequestReceiver();
        HeartbeatReceiver heartbeatReceiver = new HeartbeatReceiver();


        ChatManager chatManager = new ChatManager(userDataGuard, chatDataGuard, infoSender, chatSender);
        FileManager fileManager = new FileManager(infoSender, fileSender, fileDataGuard, userDataGuard);
        InfoRequestManager infoRequestManager = new InfoRequestManager(infoSender, heartbeatSender, userDataGuard, chatDataGuard);
        //StatusManager statusManager = new StatusManager(statusSender, userDataGuard);
        HeartbeatManager heartbeatManager = new HeartbeatManager(heartbeatSender, userDataGuard); 
        StatusManager statusManager = new StatusManager(statusSender, userDataGuard);
        LoginManager loginManager = new LoginManager(infoSender, userDataGuard, statusManager);
        UserManager userManager = new UserManager(infoSender, userDataGuard, chatDataGuard, statusManager);

        MessageReceiver messageReceiver = new MessageReceiver(channels,
                                                              loginReceiver,
                                                              userReceiver,
                                                              chatReceiver,
                                                              fileReceiver,
                                                              infoRequestReceiver,
                                                              heartbeatReceiver,
                                                              heartbeatManager);
        
        loginReceiver.register(loginManager);
        userReceiver.register(userManager);
        chatReceiver.register(chatManager);
        fileReceiver.register(fileManager);
        infoRequestReceiver.register(infoRequestManager);
        heartbeatReceiver.register(heartbeatManager);
        
        messageReceiver.initializeConnection(args[0]);
        messageReceiver.waitForClients();

        // TODO:	
        //	while (true) {
        // 		receivePackets(); 
        // 		heartbeatManager.update(); 
        // } 
    }
}
