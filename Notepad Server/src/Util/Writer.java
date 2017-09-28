package Util;

import FileManagement.Rootdirectory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Writer implements Runnable{
    String username;
    NetworkConnection nc;
    HashMap<String,Information> clientList;
    

    public Writer(String user, NetworkConnection netConnection, HashMap<String, Information> cList) {
       username=user;
        nc=netConnection;
        clientList=cList;
    
//        Set<Map.Entry<String, Information>> set = clientList.entrySet(); 
//       
//        for(Map.Entry<String, Information> me : set) {
//           Information info = clientList.get(me.getKey());
//           for(Map.Entry<String, Information> u: set) {
//        
//           info.netConnection.write("$onlineuser="+u.getKey());
//           }
//      }
        
    }
    
    @Override
    public void run() {
        
        
        
//        Rootdirectory dest = new Rootdirectory();
//        File [] all = dest.listofdir();
//        while(true){
//            File [] update = dest.listofdir();
//            if(all!= update){
//            nc.write(dest.listofdir()); 
//            }
//        }
   
    }
    
}
