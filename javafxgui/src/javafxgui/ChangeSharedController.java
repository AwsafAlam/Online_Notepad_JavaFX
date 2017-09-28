package javafxgui;

import Util.NetworkConnection;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;


public class ChangeSharedController implements Initializable {

    @FXML
    private JFXButton uploaddialogue;
    @FXML
    private ImageView close;
    @FXML
    private FlowPane rootPane;
    @FXML
    private TextField users;

    public ObservableList<Label> showuser = FXCollections.observableArrayList();
    public ArrayList<String> list = new ArrayList<>();
    
    NetworkConnection connect;
    String Filename;
    String Owner;
    @FXML
    private Button newshared;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void handleclose(MouseEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void handleshared(KeyEvent event) {
    
    
    }


    @FXML
    private void handlechange(MouseEvent event) {
        list.add(0, "#Change$Useraccess#");
        list.add(1, Filename);
        list.add(2, Owner);
        list.add("EndOF#SharedAccess&");
        
        String[] info = list.toArray(new String[0]);
        System.out.println(Arrays.toString(info));
        
        connect.write(info);
         ((Node)event.getSource()).getScene().getWindow().hide();
        
    }

    void setnetwork(NetworkConnection nc, String Username , String file) {
        System.out.println("Change sharing settings...");
        connect= nc;
        Filename =file;
        Owner = Username;
    
        getshared();
    
    }
    
    private void getshared(){
        String [] query = new String[3];
        query[0] = "@$Getspecificfile$&";
        query[1] = Filename;
        query[2] = Owner;
        
        connect.write(query);
        
        Object obj = connect.read();
        String[] shared = (String[]) obj;
        System.out.println(Arrays.toString(shared));
        
        for(int i=0;i <shared.length;i++){
        list.add(shared[i]);
        Label lbl = new Label(shared[i]);
        Image image = new Image(getClass().getResourceAsStream("cross2.png"));
        lbl.setGraphic(new ImageView(image));
        lbl.setStyle("-fx-effect: dropshadow(two-pass-box , darkgray, 10, 0.0 , 3, 4);-fx-text-fill: #4b8df9;-fx-background-color: #f7f7f1 ;");
        lbl.setAlignment(Pos.CENTER_LEFT);
        lbl.setPadding(new Insets(3, 3, 3, 3));
        lbl.setFont(new Font(17));
        
        lbl.setContentDisplay(ContentDisplay.RIGHT);
        
        
        lbl.addEventHandler(MouseEvent.MOUSE_CLICKED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    System.out.println("Clicked on "+lbl.getText());
                    list.remove(lbl.getText());
                    showuser.remove(lbl);
                    rootPane.getChildren().remove(lbl);
//                    showuser.remove(showuser.indexOf(lbl));
                    
            }
        });
       
        showuser.add(lbl);
        }
        
        
        rootPane.getChildren().addAll(showuser);
    
    }

    @FXML
    private void handlenewshared(MouseEvent event) {
       String user = users.getText();
       users.setText("");
       list.add(user);
       Label lbl = new Label(user);
        Image image = new Image(getClass().getResourceAsStream("cross2.png"));
        lbl.setGraphic(new ImageView(image));
        lbl.setStyle("-fx-effect: dropshadow(two-pass-box , darkgray, 10, 0.0 , 3, 4); -fx-border-radius:5px;-fx-border-insets: 5 5 5 5 ;-fx-text-fill: #4b8df9;  -fx-background-color: #f7f7f1 ;");
        lbl.setAlignment(Pos.CENTER_LEFT);
        lbl.setPadding(new Insets(3, 3, 3, 3));
        lbl.setFont(new Font(17));
        
        lbl.setContentDisplay(ContentDisplay.RIGHT);
        
        lbl.addEventHandler(MouseEvent.MOUSE_CLICKED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    System.out.println("Clicked on "+lbl.getText());
                    
                    list.remove(lbl.getText());
                    showuser.remove(lbl);
                    rootPane.getChildren().remove(lbl);
                    showuser.remove(showuser.indexOf(lbl));
                    
            }
        });
        
        showuser.add(lbl); 
        rootPane.getChildren().removeAll(showuser);
        rootPane.getChildren().addAll(showuser);
        
        

    }
    
}
