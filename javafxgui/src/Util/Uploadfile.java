package Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class Uploadfile extends Thread{

    String[] Username;
    NetworkConnection connect;
    File src;
    
    BufferedOutputStream bos;
    BufferedInputStream bis;

    DataInputStream dis;
    DataOutputStream dos;


    FileInputStream fis = null;
    FileOutputStream fos = null;
//    Thread t;
    
    public Uploadfile(NetworkConnection nc, File file, String[] userinfo) {
    this.Username = userinfo;
    connect = nc;
    src = file;
//    t = new Thread();
//    t.start();
    System.out.println("Inside Upload construct... File Size: "+ src.length()+"Mb File name:"+src.getName());
    }

    @Override
    public void run() {
        try {
            long  sourceSize =  src.length();
            
            byte[] arr = new byte[ (int) sourceSize];
            int done;
            System.out.println("File Size: "+ src.length()+"Mb File name:"+src.getName());
            connect.write(Username);
            
            if((boolean)connect.read()){
            bos = new BufferedOutputStream(connect.socket.getOutputStream() , (int) sourceSize);
            bis = new BufferedInputStream(connect.socket.getInputStream() , (int) sourceSize);
            
            dos = new DataOutputStream(bos);
            dis = new DataInputStream(bis);
            
            
            fis = new FileInputStream(src.toPath().toString());
            done = fis.read(arr, 0, (int) sourceSize);

            System.out.println("Sending file : Size:" + done);
            dos.write(arr, 0, done);
            dos.flush();

            boolean confirmation = (boolean) connect.read();
            if(confirmation){
            System.out.println("Done sending..");
            }
            }
            else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("This file already exists");
            alert.showAndWait();
            
            
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Uploadfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Uploadfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
