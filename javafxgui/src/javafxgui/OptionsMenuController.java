package javafxgui;

import Util.NetworkConnection;
import Util.syncdoc;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafxgui.SettingsmenuController.Username;


public class OptionsMenuController implements Initializable {

   

    Model model = new Model();
    private Textfile currentText;
    private boolean flagg;
    
    @FXML
    private MenuItem New;
    @FXML
    private MenuItem Load;
    @FXML
    private MenuItem Save;
    @FXML
    private MenuItem SaveAs;
    @FXML
    private MenuItem Close;
    @FXML
    private TextArea TextArea;
    @FXML
    private MenuItem Dekete;
    @FXML
    private MenuItem About;
    @FXML
    private JFXHamburger haml;
    @FXML
    private JFXDrawer drawer;

    static NetworkConnection connect;
    static String username;
    String [] sharedusers = null;
    static String filename;
    static String msg = null;
    static String User;
    boolean shift;
    
    
    public static void setnetwork(NetworkConnection nc , String Username, String personwriting, String f , String text) {
        username = Username;
        User = personwriting;
        connect = nc;
        filename = f;
        msg = text;
//        System.out.println(msg);
         
    }
    
    boolean caps , backbool;
    int backspace = 0;
    

   @FXML
    private void handletyped(KeyEvent event) {
        
        String[] charac = new String[5];
        charac[0] = "#$@txt#$";
        charac[1] = filename;
        charac[2] = username;
        charac[3] = event.getText();
        charac[4] = User;
        if(event.getCode().toString().equals("ENTER")){
            System.out.println(event.getCode().toString());
            charac[3] = "ENTER";
        }
        else if(event.getCode().toString().equals("SHIFT")){
            System.out.println(event.getCode().toString());
            shift = true;
        }
        else if(event.getCode().toString().equals("CAPS")){
            System.out.println(event.getCode().toString());
            if(caps){caps = false;}
            else{caps = true; }
        }
        else if(event.getCode().toString().equals("BACK_SPACE")){
            System.out.println(event.getCode().toString());
            backspace++;
            backbool = false;
        }
        if(backbool){
        
        if(event.getCode().toString().equals(""))
        {
            return;
        }
        if(shift  || caps){
            charac[3] = charac[3].toUpperCase();
            charac[3] = convertspecial(charac[3]); // converts all special characters here
        }
        System.out.println(Arrays.toString(charac));
        connect.write(charac);
        }
    }
    
    private String convertspecial(String txt){
        if(txt.equals("`")){txt = "~" ;}
        else if(txt.equals("1")){txt = "!" ;}
        else if(txt.equals("2")){txt = "@" ;}
        else if(txt.equals("3")){txt = "#" ;}
        else if(txt.equals("4")){txt = "$" ;}
        else if(txt.equals("5")){txt = "%" ;}
        else if(txt.equals("6")){txt = "^" ;}
        else if(txt.equals("7")){txt = "&" ;}
        else if(txt.equals("8")){txt = "*" ;}
        else if(txt.equals("9")){txt = "(" ;}
        else if(txt.equals("0")){txt = ")" ;}
        else if(txt.equals("-")){txt = "_" ;}
        else if(txt.equals("=")){txt = "+" ;}
        else if(txt.equals("[")){txt = "{" ;}
        else if(txt.equals("]")){txt = "}" ;}
        else if(txt.equals(";")){txt = ":" ;}
        else if(txt.equals("\'")){txt = "\"" ;}
        else if(txt.equals(",")){txt = "<" ;}
        else if(txt.equals(".")){txt = ">" ;}
        else if(txt.equals("/")){txt = "?" ;}
        else if(txt.equals("\\")){txt = "|" ;}
        
        return txt;
    }
    
     @FXML
    private void handlereleased(KeyEvent event) {
        
        String[] charac = new String[5];
        charac[0] = "#$@txt#$";
        charac[1] = filename;
        charac[2] = username;
        charac[3] = "";
        charac[4] = User;
        
        if(event.getCode().toString().equals("SHIFT")){
            System.out.println(event.getCode().toString());
            shift = false;
        }
        else if(event.getCode().toString().equals("BACK_SPACE")){
            System.out.println(event.getCode().toString());
            System.out.println("Backspace :"+backspace);
            charac[3] = "B#AckSpace:"+backspace;
            backspace = 0;
            connect.write(charac);
            System.out.println("Write backspace to server");
            backbool = true;
        }
        
    }
    
    private void changeacc() throws IOException{
        if(User.equals(username)){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeShared.fxml"));
            Pane root = loader.load();
            Scene scene = new Scene(root, 500, 350);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
            ChangeSharedController homeController = (ChangeSharedController)loader.getController();
            homeController.setnetwork(connect,username ,filename);
        }
        else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Hello "+User+",\nyou do not have permission to change access for this file.");
        alert.showAndWait();
        
        }
    
    
    }
    void Backbtn() throws IOException{
            Stage stage = null; 
            Parent root = null;
            stage=(Stage) haml.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
     }
    
    void home() throws IOException{
    Stage stage = null; 
            Pane root = null;
            stage=(Stage) haml.getScene().getWindow();
           
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("Home.fxml"));
            System.out.println("Setting root");
            stage.setTitle(User);
            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
            
            HomeController homeController = loader.<HomeController>getController();
            HomeController.setnetwork(connect, User);
    
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       caps = false;
       backbool = true;
       flagg = true;
        
        try {
          
        VBox box = FXMLLoader.load(getClass().getResource("NavBar.fxml"));
        drawer.setSidePane(box);
        
        for(Node node : box.getChildren()){
            if(node.getId()!= null){
                node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                   switch(node.getId()){
                   case "btn1" :
                   {
                        System.out.println("Pressed 1");
                       try {
                           ((Node)e.getSource()).getScene().getWindow().hide();
                           home();
                       } catch (IOException ex) {
                           Logger.getLogger(OptionsMenuController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                        break;
                   case "btn2" : 
                   {
                       System.out.println("Pressed 2");
                       try {
                           ((Node)e.getSource()).getScene().getWindow().hide();
                           Backbtn();
                       } catch (IOException ex) {
                           Logger.getLogger(OptionsMenuController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                        break;
                   case "btn3" : 
                       System.out.println("Pressed 3");
                       if(flagg){
                            int pos = msg.length()-2;

                            TextArea.setText(msg);
                            TextArea.positionCaret(pos);

                            new syncdoc(connect, TextArea);
                            flagg =false;
                        }
                        break;
                   case "btn4" : 
                   {
                       System.out.println("Pressed 4");
                       try {
                           changeacc();
                       } catch (IOException ex) {
                           Logger.getLogger(OptionsMenuController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
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

    @FXML
    private void onNew(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OptionsMenu.fxml"));
        Scene scene = new Scene(root, 1300, 600);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onLoad(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file= fileChooser.showOpenDialog(null);
        if(file != null){
            
           IOResult<Textfile> io= model.load(file.toPath());
            if(io.isok() && io.hasdata()){
                currentText = io.getdata();
                TextArea.clear();
                currentText.getcontent().forEach(TextArea::appendText);
             
            }
           
        }
        
    }

    @FXML
    private void onSave(ActionEvent event) throws IOException {
        Textfile textfile = new Textfile(currentText.getfile(),Arrays.asList(TextArea.getText().split("\n")));
        model.save(textfile);
        
        
    }

    @FXML
    private void onSaveAs(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File("./"));
//        File file= fileChooser.showSaveDialog(null);
//        if(file != null){
//            
//           IOResult<Textfile> io= model.load(file.toPath());
//            if(io.isok() && io.hasdata()){
//                Textfile textfile = io.getdata();
//                TextArea.clear();
//                textfile.getcontent().forEach(TextArea::appendText);
//             }
//        }
            FileChooser fileChooser = new FileChooser();
             
            //Set extension filter
//            FileChooser.ExtensionFilter extFilter = 
//            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//            fileChooser.getExtensionFilters().add(extFilter);
             fileChooser.setInitialDirectory(new File("./"));
            //Show save file dialog
            File file = fileChooser.showSaveDialog(null);
             
            if(file != null){
                try {
                    //SaveFile(textArea.getText(), file);
                    
                    FileWriter fileWriter;
                    fileWriter = new FileWriter(file);
                    fileWriter.write(TextArea.getText());
                    fileWriter.close();
                } catch (IOException ex) {
                    
                    Logger.getLogger(OptionsMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
        } 
      }

    @FXML
    private void onClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onDeletefile(ActionEvent event) {
        if(flagg){
        int pos = msg.length()-2;
        
        TextArea.setText(msg);
        TextArea.positionCaret(pos);

        new syncdoc(connect, TextArea);
        flagg =false;
        }
    }

    @FXML
    private void onAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This a collaborative Text Editor app with file management system \n"
                + "Made by : Md Awsaf Alam Id: 1505114\n"+
                "Project Advisor : Tanmoy Sen\n"
                +  "Version 2.5.0 last updated 15th October 2016\n"
                );
        alert.show();
        
        
    }

  
}
