package javafxgui;

public class IOResult<T> {

    public IOResult(boolean ok, T data ) {
        this.data = data;
        this.ok = ok;
    }
    private T data;
    private boolean ok;
    
    public boolean isok(){
        return ok;
    }
    public boolean hasdata(){
        return data != null;
    }
    public T getdata(){
         return data;
    }
    
    
}
