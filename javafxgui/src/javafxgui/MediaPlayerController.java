package javafxgui;

import FileManagement.Rootdirectory;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaPlayerController implements Initializable {

    Rootdirectory dest;
    static String filename;
    MediaPlayer mp;
    Media me;
    
    @FXML
    private MediaView mv;
    @FXML
    private ImageView load;
    @FXML
    private ImageView stop;
    @FXML
    private ImageView slow;
    @FXML
    private ImageView fast;
    @FXML
    private ImageView reload;
    @FXML
    private ImageView pause;
   
    double rate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rate = 1;
        dest = new Rootdirectory();
        
    }    

    static void setvar(String Filename) {
    filename = Filename;
        System.out.println("file :"+filename);
    }

    @FXML
    private void handleload(MouseEvent event) {
        
//        System.out.println(dest.maindir);
        String path = new File(dest.maindir+"/"+filename).getAbsolutePath();
        System.out.println(path);
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
//        mp.setAutoPlay(true);
        mp.play();
    
    }

    @FXML
    private void handlestop(MouseEvent event) {
        mp.stop();
    }

    @FXML
    private void handlelslow(MouseEvent event) {
        rate /= 2 ;
        mp.setRate(rate);
    }

    @FXML
    private void handlefast(MouseEvent event) {
        rate *= 2;
        mp.setRate(rate);
    }

    @FXML
    private void handlereload(MouseEvent event) {
        mp.seek(mp.getStartTime());
        mp.play();
    }

    @FXML
    private void handlepause(MouseEvent event) {
        mp.pause();
    }
    

    
    
}
