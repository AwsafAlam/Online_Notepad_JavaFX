package javafxgui;

import FileManagement.Rootdirectory;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Javafxgui extends Application {
    
    Stage mystage;
    
    @Override
    public void start(Stage stage) throws IOException {
       mystage = stage;
        Rootdirectory dest = new Rootdirectory();
        dest.makeroot();
       FXMLLoader loader=new FXMLLoader();
       loader.setLocation(getClass().getResource("Splash.fxml"));
       
       Parent root=loader.load();
       
       mystage.initStyle(StageStyle.UNDECORATED);
       Scene scene=new Scene(root,600,300);

       mystage.setTitle("Welcome");
       mystage.setScene(scene);
       mystage.show();
    }
   
    public static void main(String[] args) throws IOException {
//        NetworkConnection nc = new NetworkConnection("localhost", 55555);
               
        launch(args);
    }
}
