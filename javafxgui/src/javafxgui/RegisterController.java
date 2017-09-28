package javafxgui;

import Database.Userhandler;
import Util.NetworkConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class RegisterController implements Initializable {

    

    Userhandler userhandler ;
    
    @FXML
    private JFXTextField Firstname;

    @FXML
    private JFXButton confirm;

    @FXML
    private JFXCheckBox gendermale;

    @FXML
    private JFXTextField Username;

    @FXML
    private JFXPasswordField Retypepassword;

    @FXML
    private JFXCheckBox genderfemale;

    @FXML
    private JFXTextField Lastname;

    @FXML
    private JFXButton back;

    @FXML
    private JFXPasswordField Password;
    @FXML
    private Label label;
    @FXML
    private JFXTextField Email;
    
    public static NetworkConnection connect;
    
    static void setnetwork(NetworkConnection auth) {
        connect = auth;
        System.out.println("Network connection set");
    }
    
    @FXML
    void handleback(MouseEvent event) throws IOException {
         if(event.getSource()==back){
            Stage stage = null; 
            Parent root = null;
            stage=(Stage) back.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
            
            }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void handleconfirm(MouseEvent event) throws IOException {
    try {
if(Username.getText().equals("")||Firstname.getText().equals("")|| Lastname.getText().equals("")||Password.getText().equals("")){label.setText("Content missing");}
else{
    if(!Password.getText().equals(Retypepassword.getText())){ label.setText("Passwords don't Match");}
        else{
                String reg[] = new String[6];
                reg[0] = "register";
                reg[1] = Username.getText();
                reg[2] = Firstname.getText();
                reg[3] =Lastname.getText();
                reg[4]=Password.getText();
                reg[5]= Email.getText();
              
                connect.write(reg);
                
                boolean auth = (boolean) connect.read();
                
                if(auth){
               
                
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Success");
                    alert.showAndWait();
                
               
                    
               Stage stage = null; 
            Pane root = null;
//            System.out.println(user.getText()+"\t"+pass.getText());
            stage=(Stage) confirm.getScene().getWindow();
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            root = loader.load(getClass().getResource("Home.fxml"));
            System.out.println("Setting root");
            stage.setTitle(Username.getText());
            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
            
            // Create a controller instance
//            HomeController controller = new HomeController(connect, Username.getText());
            // Set it in the FXMLLoader
//            loader.setController(controller);
            
            
            HomeController homeController = (HomeController)loader.getController();
            HomeController.setnetwork(connect, Username.getText());
                
                
                }
                else{
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Username");
                    alert.showAndWait();
                }
              
            
           
            }
        }
        
    } catch (Exception ex) {
        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
        
        
    }
    
}
