package Server;

import Util.Data;
import Util.Information;
import Util.NetworkConnection;
import java.util.HashMap;

public class ReaderWriterServer implements Runnable{
    String username;
    NetworkConnection nc;
    HashMap<String,Information> clientList;
    
    public ReaderWriterServer(String user, NetworkConnection netConnection, HashMap<String,Information> cList){
        username=user;
        nc=netConnection;
        clientList=cList;
        
    }
    
    @Override
    public void run() {
        while(true){
            Object obj=nc.read();
            Data dataObj=(Data)obj;
            String actualMessage=dataObj.message;
            String msgs[]=actualMessage.split(":",2);
            String toUser=msgs[0];
            String sendMsg=msgs[1];
            
            Information info=clientList.get(toUser);
            
            String messageToSend=username+" -> "+sendMsg;
            Data data=new Data();
            data.message=messageToSend;
            
            info.netConnection.write(data);
            
            
        
        
        }
        
    }
    
}
