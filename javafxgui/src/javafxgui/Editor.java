
package javafxgui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;


public class Editor {
    
    public HTMLEditor htmlEditor = null;
    
    public Parent createContent() {
 
        htmlEditor = new HTMLEditor();
//        htmlEditor.setHtmlText(INITIAL_TEXT);
 
        ScrollPane htmlSP = new ScrollPane();
        htmlSP.setFitToWidth(true);
        htmlSP.setPrefWidth(htmlEditor.prefWidth(500)); // Workaround of RT-21495
        htmlSP.setPrefHeight(600);
        htmlSP.setPrefWidth(1300);
        htmlSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        htmlEditor.addEventFilter( KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            if (event.getEventType() == KeyEvent.KEY_PRESSED){
                // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
                if ( event.getCode() == KeyCode.SPACE ){ 
                    event.consume();
                }
            }
            }
        });
        htmlSP.setContent(htmlEditor);
 
        final Label htmlLabel = new Label();
        htmlLabel.setWrapText(true);
 
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setContent(htmlLabel);
        scrollPane.setFitToWidth(true);
 
        System.out.println(htmlLabel.getText());
        
        Button showHTMLButton = new Button("Show the HTML below");
        showHTMLButton.setOnAction((ActionEvent arg0) -> {
        htmlLabel.setText(htmlEditor.getHtmlText());
            System.out.println(htmlEditor.getHtmlText());
        });
        
        Button Back = new Button("Back");
        Back.setOnAction((ActionEvent arg0) -> {
            Stage stage = null; 
            Parent root = null;
            stage=(Stage) Back.getScene().getWindow();
            try {
                root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root, 1300, 600);
            stage.setScene(scene);
            stage.show();
           
        });
 
        HBox hlabel = new HBox();
        
        hlabel.setSpacing(10);
        hlabel.setAlignment(Pos.CENTER_LEFT);
        hlabel.getChildren().addAll(Back, showHTMLButton);
        hlabel.setPadding(new Insets(10, 10, 10, 20));
        
        VBox vRoot = new VBox();
        vRoot.setAlignment(Pos.CENTER);
        vRoot.setSpacing(5);
        vRoot.getChildren().addAll(hlabel,htmlSP,scrollPane);
 
        return vRoot;
    }
    
    
}
