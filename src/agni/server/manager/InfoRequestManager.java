package agni.server.manager;


import agni.server.receiver.InfoListener;
import agni.server.sender.HeartbeatSender;
import agni.server.dataguard.I_FileDataGuard;
import agni.server.sender.InfoSender;

public class InfoRequestManager implements InfoListener{
    private InfoSender infoSender;
    private I_FileDataGuard fileDataGuard;

    public InfoRequestManager(InfoSender infoSender,
                              HeartbeatSender heartbeatSender, 
                              I_FileDataGuard fileDataGuard) {

        this.infoSender = infoSender;
        this.fileDataGuard = fileDataGuard;
    }

    @Override
    public void infoRequest(String ip, byte type) {
        // TODO Auto-generated method stub
        
    }

}
