package javafxgui;

import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SplashController implements Initializable {

    @FXML
    private StackPane SplashScreen;
    @FXML
    private JFXProgressBar pb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new Sleeper().start();
    }    
    
    class Sleeper extends Thread{

        @Override
        public void run() {
            try {
                
                Thread.sleep(10000);
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FXMLLoader loader=new FXMLLoader();
                            loader.setLocation(getClass().getResource("FXML.fxml"));
                            
                            Parent root=loader.load();
                            
                            Stage mystage = new Stage();
                            Scene scene=new Scene(root,1300,600);
                            
                            mystage.setTitle("Welcome");
                            mystage.setScene(scene);
                            mystage.show();
                       
                            SplashScreen.getScene().getWindow().hide();
                        } catch (IOException ex) {
                            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
                        }
               
                    
                    
                    }
                });
                
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        
        
        }
    
    
    
    }
            
    
    
}
