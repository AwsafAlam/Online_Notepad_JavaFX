package javafxgui;

import FileManagement.Rootdirectory;
import Util.NetworkConnection;
import Util.Reader;
import Util.Uploadfile;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafxgui.SettingsmenuController.filename;
import static javafxgui.SettingsmenuController.owner;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

public class HomeController implements Initializable {

    @FXML
    private ImageView newdoc;

    @FXML
    private ImageView options;

    @FXML
    private JFXHamburger haml;

    @FXML
    private ImageView back;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private ImageView chatbtn;


    Stage mystage;
    
    public static String username;
    static NetworkConnection connect;
    public ObservableList<User> list = FXCollections.observableArrayList();
    ObservableList<String> userlist = FXCollections.observableArrayList();
    ObservableList<Label> users = FXCollections.observableArrayList();
    FilteredList<User> filteredData = new FilteredList<>(list, p -> true);
    SortedList<User> sortedData = new SortedList<>(filteredData);

    
    @FXML
    private FlowPane rootPane;
    @FXML
    private TableView<User> tableview;
    @FXML
    private TableColumn<User, String> filenamecol;
    @FXML
    private TableColumn<User, String> ownercol;
    @FXML
    private TableColumn<User, String> datecol;
    @FXML
    private TableColumn<User, String> sizecol;
    @FXML
    private CustomTextField searchbar;
    

    @FXML
    void handleDragOver(DragEvent event) {
        
        if(event.getDragboard().hasFiles()){
//            System.out.println("Detected");
            
            event.acceptTransferModes(TransferMode.ANY);
        
        }
    }
    
    public static void setnetwork(NetworkConnection nc , String Username) {
        username = Username;
        System.out.println("set auth...");
        connect = nc;
    }
    
    @FXML
    void handleDropped(DragEvent event) throws FileNotFoundException, IOException {
        List<File> files = event.getDragboard().getFiles();
        ArrayList<String> sharedusers = new ArrayList<>();
        
        if(files.get(0).isDirectory()){
         /*   
         Rootdirectory dest = new Rootdirectory();
         File sourceFile = new File(files.get(0).getAbsolutePath());
         File destinationFile = new File(dest.maindir + sourceFile.getName());

        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);

        int bufferSize;
        byte[] bufffer = new byte[512];
        while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
            fileOutputStream.write(bufffer, 0, bufferSize);
        }
        fileInputStream.close();
        fileOutputStream.close();*/
        
         
         
        }
        else{
        
        for(int i=0; i<files.size();i++)
        {
        
        String Message =  files.get(i).getName();
        System.out.println("Dropped "+ Message +"Path :"+files.get(i).getAbsolutePath()+"<-Lenght->"+files.get(i).length());
        sharedusers.add("Sendingnewfile");
        sharedusers.add( String.valueOf(files.get(i).length()));
        sharedusers.add( Message);
        sharedusers.add( username);
        sharedusers.add("Endofshareduser");
        
        String[] create =  sharedusers.toArray(new String[0]);
        System.out.println(Arrays.toString(create));
       
        new Uploadfile(connect , files.get(i) , create).start();
       
        
        
        }
//        refreshtable();
        
    }
        
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
    
    private void refreshtable(){
        list.removeAll(list);
//        users.removeAll(users);
        userlist.removeAll(userlist);
        boolean f = true;
        while(true){
           try {
                
            if(f){
            String [] writ = {"@#getmyfiles#", "###"}; 
            connect.write(writ);
             f = false;
                System.out.println("Requested for files");
             }  

            Object obj=connect.read();
           
            String[] online =  (String[]) obj;
             System.out.println("Rec: "+Arrays.toString(online));
             
            if(online[0].equals("Done sending file list")){
              break;
            }
            if(online[0].equals("$filename") ){
            
                 
//                String typ = online[1];
//                String ext = null;
//                int i = typ.lastIndexOf('.');
//                if (i > 0) {
//                     ext = typ.substring(i+1);
//                }
//
//                Image image;
//                if(ext.equals("txt")){
//                 image = new Image(getClass().getResourceAsStream("Textfile.png"));
//                }
//                else if(ext.equals("xls") || ext.equals("xlsx") || ext.equals("xlsb")|| ext.equals("gsheet")){
//                 image = new Image(getClass().getResourceAsStream("Excel.png"));
//                }
//                else if(ext.equals("jpg") ){
//                 image = new Image(getClass().getResourceAsStream("Jpg.png"));
//                }
//                else if(ext.equals("png")){
//                 image = new Image(getClass().getResourceAsStream("PNG.png"));
//                }
//                else if(ext.equals("pdf")){
//                 image = new Image(getClass().getResourceAsStream("Pdf.png"));
//                }
//                else if(ext.equals("ppt")){
//                 image = new Image(getClass().getResourceAsStream("Powerpoint.png"));
//                }
//                else if(ext.equals("docx") ||ext.equals("doc")){
//                 image = new Image(getClass().getResourceAsStream("Word.png"));
//                }
//                else if(ext.equals("zip") ||ext.equals("rar")){
//                 image = new Image(getClass().getResourceAsStream("Zip.png"));
//                }
//                else{
//                 image = new Image(getClass().getResourceAsStream("Defaultfile.png"));
//                }
                System.out.println(Arrays.toString(online));
                list.add(new User(online[1], online[2] , online[3] , online[4]));
                userlist.add(online[1]);
            
//                Label lbl = new Label();
//                lbl.setAlignment(Pos.CENTER_RIGHT);
//                lbl.setPadding(new Insets(10, 10, 10, 10));
//                lbl.setFont(new Font(17));
//                lbl.setTextFill(Color.DIMGREY);
//                lbl.setGraphic(new ImageView(image));
//                
//            users.add(lbl);
            }
//            System.out.println("File added..." + online[1]);
            tableview.setItems(list);
            TextFields.bindAutoCompletion(searchbar, userlist);
           
        } catch (Exception e) {
            System.out.println("Client failed to read from surver" + e);
            e.printStackTrace();
        }
        }
        sortdata();

}

    @FXML
    private void handleOptions(MouseEvent event) {
    
        refreshtable();
        
    }
    
    @FXML
    void newdoc(MouseEvent event) throws IOException {
        
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("NewDialogue.fxml"));
        Scene scene = new Scene(root, 300, 220);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        NewDialogueController homeController = (NewDialogueController)loader.getController();
        NewDialogueController.setnetwork(connect,username );
       
    }
    
    void NewDocument() throws IOException{
        Editor a = new Editor();
        
        Stage stage = null; 
        
        stage =(Stage) chatbtn.getScene().getWindow();
        stage.setTitle("New Document");
        stage.setScene(new Scene(a.createContent()));
        stage.show();
        
    }
    
    void Backbtn() throws IOException{
            Stage stage = null; 
            Parent root = null;
            stage=(Stage) back.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
     }
    
    
    @FXML
    private void handlechatbox(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("browsefile.fxml"));
        Scene scene = new Scene(root, 550, 310);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        BrowsefileController homeController = (BrowsefileController)loader.getController();
        BrowsefileController.setnetwork(connect,username );
    }
    
    
    private void initCol(){
        filenamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ownercol.setCellValueFactory(new PropertyValueFactory<>("owner"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("lastmodified"));
        sizecol.setCellValueFactory(new PropertyValueFactory<>("size"));
   }
    
    private void sortdata(){
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
    
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableview.setItems(sortedData);
    
    }
    private void Registernew() throws IOException{
    Stage stage = null; 
    Pane root = null;
    stage=(Stage) haml.getScene().getWindow();

    FXMLLoader loader = new FXMLLoader();
    root = loader.load(getClass().getResource("Register.fxml"));
    System.out.println("Setting root");

    Scene scene = new Scene(root, 1300, 600);
    stage.setScene(scene);
    stage.show();
    
    
    
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    initCol();
    list.add(new User("", "" , "" , "")); 
    tableview.setItems(list);
    
    TextFields.bindAutoCompletion(searchbar, userlist);
    
     mystage = new Stage();
     mystage.initStyle(StageStyle.TRANSPARENT);
        try {
        
        VBox box = FXMLLoader.load(getClass().getResource("NavBar.fxml"));
        drawer.setSidePane(box);
        
        for(Node node : box.getChildren()){
            if(node.getId()!= null){
                node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                   switch(node.getId()){
                   case "btn1" : {
                       try {
                           NewDocument(); // Html editor 
                       } catch (IOException ex) {
                           Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                        break;
                   case "btn2" : {
                       try {
                           Backbtn(); // Logout
                       } catch (IOException ex) {
                           Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                        break;
                   case "btn3" : 
                    {
                       System.out.println("Pressed 3");
                       try {
                           Registernew();
                       } catch (IOException ex) {
                           Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                        break;
                   case "btn4" : System.out.println("Pressed 4");
                        refreshtable();
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
             System.out.println("System interrupted :->" +ex);
             Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
             
         }
    }    



    @FXML
    private void handletablebiew(MouseEvent event) throws IOException {
        
        
        if(event.getButton() == MouseButton.PRIMARY){ 
//        mystage.getScene().getWindow().hide();
        User selected=tableview.getSelectionModel().getSelectedItem();
        String filename = selected.getName();
        String oWner = selected.getOwner();
        System.out.println("File :"+filename);
        
        String ext = null;
        int i = filename.lastIndexOf('.');
        if (i > 0) {
             ext = filename.substring(i+1);
        }
        
        if(ext.equals("txt")){
        
        
        Stage stage = new Stage();
        
        String [] query = {"~!getfiledata$$" , oWner , filename};
        connect.write(query);
        String txt = (String) connect.read();
        
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("OptionsMenu.fxml"));
        
        OptionsMenuController homeController = (OptionsMenuController)loader.getController();
        OptionsMenuController.setnetwork(connect,oWner,username , filename , txt); // Must supply owner info here
        
        Scene scene = new Scene(root, 1300, 600);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(filename);
        stage.setScene(scene);
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
        download(oWner , filename);
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
    else if(event.getButton() == MouseButton.SECONDARY){
        User selected=tableview.getSelectionModel().getSelectedItem();
        String filename = selected.getName();
        String oWner = selected.getOwner();
        System.out.println("Clicked on :"+filename);
        
        
        FXMLLoader loader = new FXMLLoader();
        
        Pane root = loader.load(getClass().getResource("Settingsmenu.fxml"));
        
        SettingsmenuController homeController = (SettingsmenuController)loader.getController();
        SettingsmenuController.setnetwork(connect,filename ,oWner,username ); // Must supply owner info here
        
        Scene scene = new Scene(root, 200, 300);
//        mystage.initStyle(StageStyle.TRANSPARENT);
//        stage.setTitle(openfile);
        mystage.setScene(scene);
        mystage.setX(event.getScreenX());
        mystage.setY(event.getScreenY());
        mystage.show();
    
    
    }
      
    }
   
  
    private  void download(String owner , String filename){
        Rootdirectory dest = new Rootdirectory();
    String [] s = new String[3];
        s[0] ="#DLfile@#";
        s[1] = owner;
        s[2] = filename;
        connect.write(s);
        
        Object obj = connect.read();
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

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connect.socket.getOutputStream() ,  (int)  pieceSize);
            bufferedInputStream = new BufferedInputStream(connect.socket.getInputStream() ,  (int) pieceSize);
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
}
        

