package javafxgui;

import java.nio.file.Path;
import java.util.List;

public class Textfile {

    public Textfile(Path file, List<String> content) {
        this.file = file;
        this.content = content;
    }
    private final Path file;
    
    private final List<String> content;
    
    public Path getfile(){ return file;}
    public List<String> getcontent(){
        return content;
    }
    

}
