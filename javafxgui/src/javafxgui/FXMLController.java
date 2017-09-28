package javafxgui;

import Util.NetworkConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLController implements Initializable {
    
    Javafxgui main = new Javafxgui();
    
    String ip = "localhost";
  
    NetworkConnection auth;
    
     @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXButton register;

    @FXML
    private JFXButton login;

    @FXML
    void handlelogin(MouseEvent event) throws IOException {
//        String username =user.getText();
//        String password = pass.getText();
        
        String User[] = new String[3];
        User[0] = "login";
        User[1] = user.getText();
        User[2] = pass.getText();
        
        auth.write(User);
        
        Boolean waitforauth = (Boolean) auth.read();
        
        if(waitforauth){
            
            Stage stage = null; 
            Pane root = null;
            System.out.println(user.getText()+"\t"+pass.getText());
            stage=(Stage) login.getScene().getWindow();
           
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("Home.fxml"));
            System.out.println("Setting root");
            stage.setTitle(user.getText());
            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
            
            HomeController homeController = loader.<HomeController>getController();
            HomeController.setnetwork(auth, user.getText());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Login Failed");
            alert.showAndWait();
        
        
        }
 }
    
   
    
    @FXML
    void handleregister(MouseEvent event) throws IOException {
            Stage stage = null; 
            Pane root = null;
            System.out.println(user.getText()+"\t"+pass.getText());
            stage=(Stage) login.getScene().getWindow();
           
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("Register.fxml"));
            System.out.println("Setting root");

            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
            
            RegisterController homeController = (RegisterController)loader.getController();
            RegisterController.setnetwork(auth);
            
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NetworkConnection nc = new NetworkConnection( ip, 55555);
            auth =nc;
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
}
