package notepad.server;

import Database.Userhandler;
import FileManagement.Rootdirectory;
import Server.CreateConnection;
import Util.Information;
import Util.NetworkConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class NotepadServer {

     public static void main(String[] args) throws IOException {
         
         ServerSocket serv = new ServerSocket(55555);
         ConcurrentHashMap <String,Information> clientList=new ConcurrentHashMap<>();
         
         while(true){
         Socket socket = serv.accept();
         
            System.out.println("Accepted connection");
            NetworkConnection nc = new NetworkConnection(socket);
            Rootdirectory dest = new Rootdirectory();
            dest.makeroot();
//             Userhandler userhandler = new Userhandler();
            
//            String query = "INSERT INTO BOOk VALUES ("
//                    + "'" + "Awsaf" + "',"
//                    + "'" + "1234" + "',"
//                    + "'" + "Awsaf" + "',"
//                    + "'" + "Alam" + "',"
//                    + "'" + "awsafalam@gmail.com" + "',"
//                    + "" + "true" + ""
//                    + ")";
//             boolean rs = userhandler.execAction(query);
            new Thread(new CreateConnection(clientList,nc)).start();
       
         }
         
         
     }
    
}
