package Util;

import javafx.scene.control.TextArea;

public class syncdoc implements Runnable{
    Thread t;
    public NetworkConnection netConnection;
    public TextArea ta;
    
    public syncdoc(NetworkConnection connect, TextArea textarea) {
        netConnection=connect;
        ta = textarea;
        t = new Thread(this);
        t.start();
        System.out.println("Sync thread running....");
    }

    @Override
    public void run() {
        int pos = ta.getText().length();
        while(true){
            try {
                
            pos++;
//            System.out.println("Reader thread started in client");
            Object obj=netConnection.read();
            System.out.println(obj);
            String online= (String) obj;
//                System.out.println("Length :"+online.length());
           if(online.length() > 21){
            String[] txt = online.split(":",2);
             System.out.println(txt[1]);
            int back = Integer.valueOf(txt[1]);
            String s = ta.getText().substring(0, ta.getText().length() - back);
            ta.setText(s);
            pos -= back;
           ta.positionCaret(pos);
           
            continue;
           }
            
            
            String[] txt = online.split("=",2);
            System.out.println(txt[1]);
            ta.setText(ta.getText()+txt[1]);
//            System.out.println("Cursor at: "+ta.getCaretPosition());
        
            ta.positionCaret(pos);
            
            } catch (Exception e) {
                System.out.println("Exception in syncdoc"+e);
                e.printStackTrace();
            }
           
        }
    }  
   
}