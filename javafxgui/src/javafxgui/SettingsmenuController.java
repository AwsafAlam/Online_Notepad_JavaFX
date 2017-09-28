package javafxgui;

import FileManagement.Rootdirectory;
import Util.NetworkConnection;
import Util.Reader;
import com.jfoenix.controls.JFXButton;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafxgui.HomeController.connect;

public class SettingsmenuController implements Initializable {

    static NetworkConnection nc;
    static String filename;
    static String Username;
    static String owner;
    Rootdirectory dest;
   
    static void setnetwork(NetworkConnection connect, String Filename, String oWner, String username) {
        nc= connect;
        Username = username;
        owner = oWner;
        filename = Filename;
    }

    @FXML
    private JFXButton open;
    @FXML
    private JFXButton share;
    @FXML
    private JFXButton details;
    @FXML
    private JFXButton download;
    @FXML
    private JFXButton delete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dest = new Rootdirectory();
    }    

    @FXML
    private void handleopen(MouseEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        
        String ext = null;
        int i = filename.lastIndexOf('.');
        if (i > 0) {
             ext = filename.substring(i+1);
        }
        
        if(ext.equals("txt")){
        
        Stage stage = new Stage();
        
        String [] query = {"~!getfiledata$$" , owner , filename};
        nc.write(query);
        String txt = (String) nc.read();
        
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("OptionsMenu.fxml"));
        
        OptionsMenuController homeController = (OptionsMenuController)loader.getController();
        OptionsMenuController.setnetwork(connect,owner,Username , filename , txt); // Must supply owner info here
        
        Scene scene = new Scene(root, 1300, 600);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(filename);
        stage.setScene(scene);
//        stage.setX(event.getScreenX());
//        stage.setY(event.getScreenY());
        stage.show();
        
    }
    else if (ext.equals("mp4") || ext.equals("flv") || ext.equals("mp3") || ext.equals("wav")){
        Rootdirectory dest = new Rootdirectory();
        if(dest.Fileexists(filename)){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("MediaPlayer.fxml"));
        Scene scene = new Scene(root, 1300, 600);
        
        stage.setScene(scene);
        stage.show();
        
        MediaPlayerController control = loader.getController();
        MediaPlayerController.setvar(filename);
        }
        else{
        download();
        System.out.println("Downloaded...");
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("MediaPlayer.fxml"));
        Scene scene = new Scene(root, 1300, 600);
        
        stage.setScene(scene);
        stage.show();
        
        MediaPlayerController control = loader.getController();
        MediaPlayerController.setvar(filename);
            
            
        }
    
    
    }
    else{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText("This file format is not supported currently.\nPlease choose another file.\nThank You");
    alert.showAndWait();
    }
        
    }

    @FXML
    private void handleshared(MouseEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        if(Username.equals(owner)){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeShared.fxml"));
            Pane root = loader.load();
            Scene scene = new Scene(root, 500, 350);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
            ChangeSharedController homeController = (ChangeSharedController)loader.getController();
            homeController.setnetwork(nc,Username ,filename);
        }
        else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Cannot change access to file");
        alert.showAndWait();
        
        }
        
    }

    @FXML
    private void handledetails(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        String s = new String();
        
        if(Username.equals(owner)){
            s= "you";
        }else{ s= owner;}
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Hello "+Username+" this file "+filename+" is currently owned by "+s+"\nThank you for using my drive");
        alert.showAndWait();
    }

    @FXML
    private void handledownload(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        System.out.println("Download entered...");
        download();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Download Complete");
        alert.show();
        
        
        
    }

    private  void download(){
    String [] s = new String[3];
        s[0] ="#DLfile@#";
        s[1] = owner;
        s[2] = filename;
        nc.write(s);
        
        Object obj = nc.read();
        String msg = (String) obj;
        System.out.println("Server file: "+msg);
        if(msg.equals("#@DOESnoTExiSt&"))
        {
            return;
        }
        else{
            
            BufferedInputStream bufferedInputStream = null;
                
            try {
            long pieceSize = Long.parseLong(msg);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(nc.socket.getOutputStream() ,  (int)  pieceSize);
            bufferedInputStream = new BufferedInputStream(nc.socket.getInputStream() ,  (int) pieceSize);
            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
            byte [] arr = new byte[ (int) pieceSize];


            String tmpPath = dest.maindir+"/"+filename ;
            File tmp = new File(tmpPath);
            FileOutputStream fileOutputStream = new FileOutputStream(tmp.toPath().toString());
            dataInputStream.readFully(arr, 0, (int) pieceSize);
            fileOutputStream.write(arr, 0, (int) pieceSize);
            fileOutputStream.flush();


                tmp.createNewFile();
                
                System.out.println("Download Complete...");
//                nc.write(true);
                
                } catch (IOException ex) {
                    Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        
        
        
        
        
        }
    
    }
    @FXML
    private void handledelete(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        if(Username.equals(owner)){
        String [] query = new String[3];
        
        query[0] = "~*#DeleTe$$";
        query[1] =Username;
        query[2]= filename;
        nc.write(query);
        
        
        boolean auth = (boolean) nc.read();
        if(auth){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("File deleted successfuly");
        alert.show();
        
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to delete");
            alert.show();
        }
        }
        else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You Do not have permission to delete this file");
        alert.showAndWait();
        
        }
    
    }
    
}
