package javafxgui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
 public void save(Textfile textfile) throws IOException{
        Files.write(textfile.getfile(), textfile.getcontent(), StandardOpenOption.CREATE);
    }
    public IOResult<Textfile> load(Path file){
    
        try {
            List<String> lines = Files.readAllLines(file);
            return  new IOResult<>(true , new Textfile(file, lines));
        } catch (IOException ex) {
            Logger.getLogger(OptionsMenuController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception in saving file "+ ex);
            return new IOResult<>(false,null);
        }
        
    }


    
}
