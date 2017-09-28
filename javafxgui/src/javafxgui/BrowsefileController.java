package javafxgui;

import Util.NetworkConnection;
import Util.Uploadfile;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;


public class BrowsefileController implements Initializable {

    static NetworkConnection nc;
    static String Username;

    static void setnetwork(NetworkConnection connect, String username) {
        nc = connect;
        Username = username;
    }
    
    @FXML
    private TextField documentname;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton uploaddialogue;
    @FXML
    private JFXTextField users;
    @FXML
    private JFXButton savedialogue1;
    @FXML
    private JFXButton browse;
    @FXML
    private Label SharedUses;

    File src_file = null;
    
    public ArrayList<String> sharedusers = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    
    }    



    @FXML
    private void handlebrowse(MouseEvent event) {
      if( event.getButton() == MouseButton.PRIMARY){
          try{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File");
        fileChooser.setInitialDirectory(new File("./"));
        File file= fileChooser.showOpenDialog(null);
        src_file = file;
        
        System.out.println("File path : "+file.getAbsolutePath());
        documentname.setText(file.getAbsolutePath());
        }catch(NullPointerException e){
              System.out.println("Null in browse"+e);
        }
      }
        
    }

    @FXML
    private void handleshared(MouseEvent event) {
        String colon="";
        if(!SharedUses.getText().equals(""))
        {
            colon = " ; ";
        }
        SharedUses.setText(SharedUses.getText()+colon+users.getText());
        sharedusers.add(users.getText());
        users.setText("");

    }

    @FXML
    private void handlcancle(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void handleuplod(MouseEvent event) {
        System.out.println("Clicked Upload");
        System.out.println("File path : "+src_file.getAbsolutePath());
        sharedusers.add(0, "Sendingnewfile");
        sharedusers.add(1, String.valueOf(src_file.length()));
        sharedusers.add(2,  src_file.getName());
        sharedusers.add(3,  Username);
        sharedusers.add("Endofshareduser");
        
        String[] create =  sharedusers.toArray(new String[0]);
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        new Uploadfile(nc , src_file , create).start();
        
        
    }
    
}
