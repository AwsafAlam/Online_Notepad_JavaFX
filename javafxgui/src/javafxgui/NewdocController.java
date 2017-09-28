package javafxgui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class NewdocController implements Initializable {

    String message ="";
    
    @FXML
    private JFXTextField Textfield;
    
    @FXML
    private ImageView home;

    @FXML
    private JFXTextArea chatwindow;

    @FXML
    private JFXButton submit;
    
    @FXML
    private JFXHamburger haml;

    private JFXDrawer drawer;
    
     @FXML
    private ImageView options;
    
    @FXML
    void handleoptions(MouseEvent event) {

    }
    

    @FXML
    void handlehomebtn(MouseEvent event) throws IOException {
        if(event.getSource()==home){
//            Stage stage = null; 
//            Parent root = null;
//            stage=(Stage) home.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
//             Scene scene = new Scene(root, 1300, 600);
//            stage.setScene(scene);
//            stage.show();
            System.out.println("Home Clicked");       
            }
    }
   
    @FXML
    void handlesubmit(MouseEvent event) throws FileNotFoundException {
//        PDF newpdf = new PDF();
       String message =  chatwindow.getText();
//        PDF.createpdf(message);
        
        
    }
    

   
//    
//    public void startRun(){
//        try {
//            String cName = "c2";
//            String serverAddress="127.0.0.1";
//            int serverPort=33333;
//            NetworkUtil nc = new NetworkUtil(serverAddress,serverPort);
//            rt = new ReadThread(nc);
//            wt =  new WriteThread(nc, cName);
//            } catch(Exception e) {
//                System.out.println (e);
//            }
//	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         try {
          
         VBox box = FXMLLoader.load(getClass().getResource("NavBar.fxml"));
        drawer.setSidePane(box);
        
        for(Node node : box.getChildren()){
            if(node.getId()!= null){
                node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                   switch(node.getId()){
                   case "btn1" : System.out.println("Pressed 1");
                        break;
                   case "btn2" : System.out.println("Pressed 2");
                        break;
                   case "btn3" : System.out.println("Pressed 3");
                        break;
                   case "btn4" : System.out.println("Pressed 4");
                        break;
                   case "btn5" : System.exit(0);
                        break;
                   
                   }     
                });
            
            
            
            
            
            }
        
        }
        
        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(haml);
        transition.setRate(-1);
        haml.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();

            if(drawer.isShown()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
         } catch (IOException ex) {
             System.out.println("Expection in new document!!! "+ ex);
             Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }    

}

  
