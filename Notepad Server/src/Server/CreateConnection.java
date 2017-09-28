package Server;

import Database.Userhandler;
import FileManagement.Rootdirectory;
import Util.Information;
import Util.Mailsender;
import Util.NetworkConnection;
import Util.Reader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateConnection implements Runnable{
    
    ConcurrentHashMap<String,Information> clientList;
    NetworkConnection nc;
    Userhandler userhandler;
    Rootdirectory dest ;
    
    public CreateConnection(ConcurrentHashMap<String,Information> cList, NetworkConnection nConnection){
        clientList=cList;
        nc=nConnection;    
        userhandler = new Userhandler();
        this.dest = new Rootdirectory();
    }

   
    @Override
    public void run() {
        while(true){
        Object userObj=nc.read();
        String[] username=(String[])userObj;
        boolean flag = false;
        System.out.println("User-->"+username[1]);
        
    if(username[0].equals("login"))
    {
        Set<Map.Entry<String, Information>> set = clientList.entrySet(); 
                    
        for(Map.Entry<String, Information> me : set) {
            if(me.getKey().equals(username[1])){
                System.out.println("User Already Loged in"); // Send mail to secure user
                nc.write(false);
                return;
            }
        }
        String emailtouser = null;
        String qu = "SELECT Username,Password,email FROM BOOk";
        ResultSet rs = userhandler.execQuery(qu);
        try {
            while(rs.next()){
                String titlex = rs.getString("Username");
                String pass = rs.getString("Password"); //username[4] == password for register
                String email = rs.getString("email");
                System.out.println(titlex+"\t"+ pass);
                if(titlex.equals(username[1]))
                {
                    System.out.println("Matched"+titlex);
                    if(pass.equals(username[2])){
                    emailtouser = email;
                    System.out.println("Mail sent to"+ email);
                    flag = true;
                    nc.write(true);
                    }
                }
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }catch(Exception e){
            System.out.println("In login..."+e);
        }
        
        if(!flag){
          System.out.println("Not found");
          nc.write(false);
          continue;
        }

        System.out.println("User : "+username[1]+" connected");
        
        clientList.put(username[1],new Information(username[0],nc));
      
//        new Mailsender(emailtouser, 0, username[1]).start();
        
    }
    else if(username[0].equals("register")){
        
        String qu = "SELECT Username FROM BOOk";
        ResultSet rs = userhandler.execQuery(qu);
        try {
            while(rs.next()){
                String titlex = rs.getString("Username");
                if(titlex.equals(username[1]))
                {
                    System.out.println("Matched"+titlex);
                    nc.write(false);
                    flag = true;
                }                    
            }
            if(flag)
            {
                continue;
            }
                System.out.println(Arrays.toString(username));
               String query = "INSERT INTO BOOK VALUES ("
                    + "'" + username[1] + "',"
                    + "'" + username[4] + "',"
                    + "'" + username[2] + "',"
                    + "'" + username[3] + "',"
                    + "'" + username[5] + "',"
                    + "" + "true" + ""
                    + ")";
                    System.out.println("<Query>"+ query);
                boolean s = userhandler.execAction(query);

            nc.write(s);
            
            if(s){
            
//            new Mailsender(username[5], 1, username[1]).start();
            }
            
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("User : "+username[1]+" connected");
        
        clientList.put(username[1],new Information(username[1],nc));
        
            try {
                if(dest.userdir(username[1])){
                dest.userreg(username[1]);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(CreateConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
//    new Thread(new Writer(username[1],nc,clientList)).start();
    new Thread(new Reader(username[1],nc,clientList)).start();
//    new Thread(new ReaderWriterServer(username[1],nc,clientList)).start();
     
    break;
    
    
    }
       
        
    }
    
}
