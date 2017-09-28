package javafxgui;

import javafx.beans.property.SimpleStringProperty;




public class User  {
    private final SimpleStringProperty name;
    private final SimpleStringProperty owner;
    private final SimpleStringProperty lastmodified;
    private final SimpleStringProperty size;
    

   

    public User(String name, String owner, String lastmodified, String size) {
        this.name = new SimpleStringProperty(name);
        this.owner = new SimpleStringProperty(owner);
        this.lastmodified = new SimpleStringProperty(lastmodified);
        this.size = new SimpleStringProperty(size);
    }
 
    public String getName() {
        return name.get();
    }

    public String getOwner() {
        return owner.get();
    }

    public String getLastmodified() {
        return lastmodified.get();
    }

    public String getSize() {
        return size.get();
    }


    

}
