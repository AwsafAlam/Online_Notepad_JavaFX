package javafxgui;

import Util.NetworkConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.TextFields;


public class NewDialogueController implements Initializable {

    @FXML
    private ImageView sharewith;
    @FXML
    private TextField documentname;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton savedialogue;
    
    public ArrayList<String> sharedusers;

    
//    public JFXTextField users = FXCollections.observableArrayList();
    @FXML
    private Label info;
    
     public static String Username;
    static NetworkConnection connect;
    @FXML
    private JFXTextField users;

    public NewDialogueController() {
        this.sharedusers = new ArrayList<>();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        for(int i=0;i<20;i++){
//       
//        users.add("Value "+i);
//        }
//        sharewith.setItems(users);
    String []possibleSuggestions = {"Hii","Hello","How are you"};
    TextFields.bindAutoCompletion(users, possibleSuggestions);



    }    

    public static void setnetwork(NetworkConnection nc , String username) {
            Username = username;
            System.out.println("set auth...");
            connect = nc;
//            new Reader(connect);
    }
    @FXML
    private void handlecanceldialogue(MouseEvent event) {
//        Label e = new Label("New");
//        users.add(e);
//        sharewith.setItems(users);
         ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void handlesavedialogue(MouseEvent event) throws IOException {
        
        System.out.println("Filename : "+documentname.getText());
//        String create[] = new String[3];
        sharedusers.add(0, "Filename");
        sharedusers.add(1, Username);
        sharedusers.add(2, documentname.getText());
        sharedusers.add("End of file");
        
       String[] create =  sharedusers.toArray(new String[0]);
        
       for(String x:create)
       {
           System.out.println(x);
       
       }
       
       
//        create[0] = "Filename";
//        create[1] = Username;
//        create[2] = documentname.getText();
        connect.write(create);
          
        boolean verify = (boolean) connect.read();
        
        if(verify){
        Stage stage = new Stage();
        
//        Parent root = FXMLLoader.load(getClass().getResource("OptionsMenu.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("NewDialogue.fxml"));
        ((Node)event.getSource()).getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("OptionsMenu.fxml"));
        Scene scene = new Scene(root, 1300, 600);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        OptionsMenuController homeController = (OptionsMenuController)loader.getController();
        OptionsMenuController.setnetwork(connect,Username, Username, documentname.getText()+".txt" ,"");
        
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsMenu.fxml"));
//        // Create a controller instance
//        OptionsMenuController controller = new OptionsMenuController(create, connect,Username);
//        // Set it in the FXMLLoader
//        loader.setController(controller);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("This file already exists");
            alert.showAndWait();
        }
        
        
        
    }

    @FXML
    private void handleshared(MouseEvent event) {
//        String share = sharewith.getValue();
//        info.setText(info.getText()+";"+share);
//        System.out.println("selected ... "+ sharewith.getSelectionModel().getSelectedItem().getText());
        String colon="";
        if(!info.getText().equals(""))
        {
            colon = " ; ";
        }
        info.setText(info.getText()+colon+users.getText());
        sharedusers.add(users.getText());
        users.setText("");



    }
    
}
