package Util;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Reader implements Runnable{
    Thread t;
    public NetworkConnection netConnection;
    static ListView<Label> List;
    public ObservableList<Label> users = FXCollections.observableArrayList();
    
    public Reader(NetworkConnection connect, ListView<Label> Listview) {
        netConnection=connect;
        List = Listview;
        t = new Thread(this);
        t.start();
    }

    public Reader(NetworkConnection connect) {
    netConnection=connect;
    }
    
    @Override
    public void run() {
        
    try {
            
        
    
    Platform.runLater(new Runnable() {
    @Override
    public void run() {
        boolean f = true;
        while(true){
           try {
                
            if(f){
            String [] writ = {"@#getmyfiles#", "###"}; 
            netConnection.write(writ);
             f = false;
                System.out.println("Requested for files");
             }  
               
            System.out.println("Reader thread started in client");
            Object obj=netConnection.read();
            System.out.println(obj);
            String online= (String) obj;
            
            if(online.equals("Done sending file list")){
            Thread.yield();
            break;
            }
            if(online.startsWith("$") ){
            boolean flag = true; 
            String msgs[]=online.split("=",2);
            String User=msgs[1];
            
//            for(int i = 0 ; i<users.size();i++){
//            if(users.get(i).getText().equals(User)){
//                flag = false;
//            }
//            }
            if(flag){
                Label lbl = new Label();
                lbl.setText(User);
                lbl.setAlignment(Pos.CENTER);
                lbl.setPadding(new Insets(10, 10, 10, 10));
                
                
            users.add(lbl);
            }
            System.out.println("Label added..." + User);
            List.setItems(users);
            
            }
        } catch (Exception e) {
            System.out.println("Client failed to read from surver" + e);
            e.printStackTrace();
        }
        }
        System.out.println("Updated listview");
    }
    
         });
     
         } catch (Exception e) {
             System.out.println("Exception in Platform runnable"+e);
             e.printStackTrace();
        } 
    
    
}
    
   
}