package Util;

import FileManagement.Rootdirectory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader implements Runnable{
    String username;
    NetworkConnection nc;
    ConcurrentHashMap<String,Information> clientList;
    Rootdirectory dest;
    
    public Reader(NetworkConnection nc){
       this.nc=nc;
    }

    public Reader(String user, NetworkConnection netConnection, ConcurrentHashMap<String, Information> cList) {
        username=user;
        nc=netConnection;
        clientList=cList;
        dest = new Rootdirectory();
    }
    
    @Override
    public void run() {
        
        while(true){
            
        Object obj=nc.read();
        if(obj == null){
//            try {
//                clientList.remove(username);
//                nc.socket.close();
//                System.out.println("Socket Closed");
//            } catch (IOException ex) {
//                System.out.println("filed to close socket ");
//                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
//            }
                System.out.println("Null read by server");
                continue;
            }
            String[] dataObj=(String[]) obj;
            System.out.println(Arrays.toString(dataObj));
            
            if(dataObj[0].equals("#$@txt#$")){
            
                try {
//                    System.out.println(Arrays.toString(dataObj));
                    dest.writetofile(dataObj[1], dataObj[2], dataObj[3]);
                    // This is code for syncing to different users
                    String[] sharedwith = dest.getsharedwith(dataObj[1],dataObj[2]);
                    
//                    System.out.println("Shared for file "+ dataObj[1] +Arrays.toString(sharedwith));
                    
                    if(dataObj[3].equals("ENTER")){
                        dataObj[3] = "\n";
                    }
                    
                    
                    Set<Map.Entry<String, Information>> set = clientList.entrySet(); 
                    boolean found = true;
                    for(Map.Entry<String, Information> me : set) {
                        
                        if(me.getKey().equals(dataObj[4])){
                            continue;
                        }
                       
                        
                        for(String x:sharedwith){
//                            System.out.println("Shared with "+x);
//                            if(x.equals(dataObj[4]))
//                            {
//                                Information info = clientList.get(dataObj[2]);
//                                info.netConnection.write("#incomingtxt="+dataObj[3]);
//                                System.out.println("Text sent to owner");
//                                break;
//                            }
                            if(x.equals(me.getKey()) || dataObj[2].equals(me.getKey())){
                                if(dataObj[2].equals(me.getKey())){found = false;}
                                System.out.println("Sending to "+me.getKey());
                                Information info = clientList.get(me.getKey());
                                info.netConnection.write("#incomingtxt="+dataObj[3]);
                                break;
                            }
                        
                        }
                        if(found){
                        if(me.getKey().equals(dataObj[2])){ //For sending to owner
                            System.out.println("Sending to "+me.getKey());
                            Information info = clientList.get(me.getKey());
                            info.netConnection.write("#incomingtxt="+dataObj[3]);
                        
                        }
                        }
                      
                      }
                    } catch (IOException ex) {
                    Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            
               
            
            }
            else if(dataObj[0].equals("#Change$Useraccess#")){
            ArrayList<String> list = new ArrayList<>();
                System.out.println("Got.."+Arrays.toString(dataObj));
               int i=3;
               while(!dataObj[i].equals("EndOF#SharedAccess&")){
                   list.add(dataObj[i]);
                   i++;
               }
                
                
            try {
                dest.updateaccess(dataObj[1] , dataObj[2] , list);
            } catch (IOException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }
            else if(dataObj[0].equals("~*#DeleTe$$")){
            
              boolean auth = false;
            try {
                auth = dest.deletefile(dataObj[1] , dataObj[2]);
            } catch (IOException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
              nc.write(auth);
            }
            else if(dataObj[0].equals("@$Getspecificfile$&")){
            try {
                String [] shared = dest.getspecificshared(dataObj[1],dataObj[2] );
                nc.write(shared);
                
            } catch (IOException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            }
            else if(dataObj[0].equals("#DLfile@#")){
                File src;
                BufferedOutputStream bos;
                BufferedInputStream bis;

                DataInputStream dis;
                DataOutputStream dos;

                FileInputStream fis = null;
                FileOutputStream fos = null;

                String [] s = dataObj[2].split(".", 2);
                System.out.println("File "+s[0]);
                src = dest.getfileobj(dataObj[1], dataObj[2]);
                if(src != null){
                nc.write(String.valueOf(src.length()));
                }
                else{
                    nc.write("#@DOESnoTExiSt&");
                    System.out.println("Does not exist");
                    return;
                }
                try {
                long  sourceSize =  src.length();
            
                byte[] arr = new byte[ (int) sourceSize];
                int done;
                System.out.println("File Size: "+ src.length()+"Mb File name:"+src.getName());
            
            
                bos = new BufferedOutputStream(nc.socket.getOutputStream() , (int) sourceSize);
                bis = new BufferedInputStream(nc.socket.getInputStream() , (int) sourceSize);

                dos = new DataOutputStream(bos);
                dis = new DataInputStream(bis);
                fis = new FileInputStream(src.toPath().toString());
                done = fis.read(arr, 0, (int) sourceSize);

                System.out.println("Sending file : Size:" + done);
                dos.write(arr, 0, done);
                dos.flush();
//            System.out.println("Byte array sent of piece " + i + " : " + (Boolean) objectInputStream.readObject() + "\n");
            
//            connect.write("Donesending file");
//            
//            if((boolean)connect.read()){
//                System.out.println("Send confirmed");
//            }
// 
//                boolean confirmation = (boolean) connect.read();
//                if(confirmation){
//                System.out.println("Done sending..");
//                }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }

            
            
            }
            else if(dataObj[0].equals("Sendingnewfile")){
                
                if(dest.checkfileexist(dataObj[3], dataObj[2])){
                    nc.write(true);
                }
                else{
                    nc.write(false);
                    return;
                }
                
                
                BufferedInputStream bufferedInputStream = null;
                try {
                    long pieceSize = Long.parseLong(dataObj[1]);
                    
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(nc.socket.getOutputStream() ,  (int)  pieceSize);
                    bufferedInputStream = new BufferedInputStream(nc.socket.getInputStream() ,  (int) pieceSize);
                    DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
                    DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
                    byte [] arr = new byte[ (int) pieceSize];
                    
                
                String tmpPath = dest.maindir+"/"+dataObj[3]+"/"+dataObj[2] ;
                File tmp = new File(tmpPath);
                FileOutputStream fileOutputStream = new FileOutputStream(tmp.toPath().toString());
                        dataInputStream.readFully(arr, 0, (int) pieceSize);
                        fileOutputStream.write(arr, 0, (int) pieceSize);
                        fileOutputStream.flush();
                    

                tmp.createNewFile();
                
                nc.write(true);
                
                } catch (IOException ex) {
                    Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                int i=4;
                while(!dataObj[i].equals("Endofshareduser"))
                {
                    try {
                        System.out.println("Rec by server :"+dataObj[1]+ dataObj[2]+"  "+ dataObj[i]);
                        dest.sharedwith(dataObj[3], dataObj[2], dataObj[i]);
                        i++;
                    } catch (IOException ex) {
                        Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//                nc.write(true);
                System.out.println("File transfer finished ... ");
                
            }
            else if(dataObj[0].equals("@#getmyfiles#")){
                
                try {
                    File[] list = dest.listofdir(username);
                    for(File file : list)
                    {
                       
                        String path = file.getPath();
                        int lastSlash = path.lastIndexOf("\\");
                        path = path.substring(0, lastSlash);
                        String owner = path.substring(0, lastSlash);
                        String s = (String) owner.subSequence(owner.lastIndexOf("\\")+1, owner.length());
//                        System.out.println(s);
                        
                        System.out.println("Name:"+file.getName()+"size: "+file.length()+"Last Modified :"+new Date(file.lastModified()).toString());
                        if(file.getName().equals("Sharedtome.txt") || file.getName().equals("Sharedwith.txt") ){
                            continue;
                        }
                        else
                        {
                            String [] fileinfo = new String[5];
                            fileinfo[0] = "$filename";
                            fileinfo[1] = file.getName();
                            fileinfo[2] = s;
                            fileinfo[3] = new Date(file.lastModified()).toString();
                            fileinfo[4] = file.length() +"Mb";
                            
                            nc.write(fileinfo);
                            System.out.println(Arrays.toString(fileinfo));
                        }
                        
                    }
                    File[] list2 = dest.listofshared(username);
                    for(File file : list2)
                    {
                        String path = file.getPath();
                        int lastSlash = path.lastIndexOf("\\");
                        path = path.substring(0, lastSlash);
                        String owner = path.substring(0, lastSlash);
                        String s = (String) owner.subSequence(owner.lastIndexOf("\\")+1, owner.length());
                       String [] fileinfo = new String[5];
                        fileinfo[0] = "$filename";
                        fileinfo[1] = file.getName();
                        fileinfo[2] = s;
                        fileinfo[3] = new Date(file.lastModified()).toString();
                        fileinfo[4] = file.length() +"Mb";
                            
                        nc.write(fileinfo);
                        System.out.println(Arrays.toString(fileinfo));
                        
                    }
                    String [] fileinfo = new String[2];
                    
                    fileinfo[0] = "Done sending file list";
                     fileinfo[1] = "Done sending file list";
                    nc.write(fileinfo);
                    System.out.println("Done sending file list");
//                    System.out.println("<From doc2> "+dest.readFile("Awsaf", "doc2.txt"));
                } catch (IOException ex) {
                    Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
            else if(dataObj[0].equals("~!getfiledata$$")){
                try {
                    String txt = dest.readFile(dataObj[1], dataObj[2]);
                    System.out.println("<Write txt content to client>");
                    nc.write(txt);
                } catch (IOException ex) {
                    Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            }
            else{
            if(dataObj[0].equals("Filename")){
            try {
                if(dest.makenewfile(dataObj[1],dataObj[2])){
                    
//                new ReaderWriterServer(username, nc, clientList);
//                  new Writer(username, nc, clientList);
                    int i=3;
                    while(!dataObj[i].equals("End of file"))
                    {
                        System.out.println("Rec by server :"+dataObj[1]+ dataObj[2]+".txt"+ dataObj[i]);
                        dest.sharedwith(dataObj[1], dataObj[2]+".txt", dataObj[i]);
                        i++;
                    }

                    nc.write(true);

                }
                else{
                    nc.write(false);
                }
                
                
            } catch (IOException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
                
            
            }
        }
            
        }
    }
    
    
    
    
    
}
