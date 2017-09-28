package FileManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Rootdirectory {
    public String maindir =  "C:/My Drive";
    
    
    public boolean userlogedin(String Username) throws IOException{
        File f = new File(maindir+"/"+Username+".txt");
        if(!f.exists()){
            f.createNewFile();
            FileWriter fileWriter;
            fileWriter = new FileWriter(f);
            fileWriter.write(Username);
            fileWriter.close();
             return true;
         }
         else{
             return false;
         }
    }
    public boolean userreg(String Username,String Firstname,String Lastname,String Password) throws IOException{
    File f = new File(maindir+"/Reginfo.txt");
         if(!f.exists()){
            f.createNewFile();
            FileWriter fileWriter;
            fileWriter = new FileWriter(f);
            fileWriter.write(Username+"\n");
            fileWriter.write(Firstname+"\n");
            fileWriter.write(Lastname+"\n");
            fileWriter.write(Password+"\n");
           
            
            fileWriter.close();
             return true;
         }
         else{
             return false;
         }
    }
    
    public void makeroot(){
     File f = new File(maindir);
       if(!f.exists())
       {
           f.mkdir();
           System.out.println("Directory Created");
       }
       else{
           System.out.println("Directory Already exists");
       
       }
    }
    
    public boolean getauth(String Username ,String pasword) throws FileNotFoundException{
        File f = new File(maindir+"/Username");
        if(f.exists()){
            File log = new File(maindir+"/Username"+"loginfo.txt");
            FileReader reader = new FileReader(log);
        
        
        }
        
    
        return false;
    }
    
    public boolean makenewfile(String name) throws IOException{
         File f = new File(maindir+"/"+name+".txt");
         if(!f.exists()){
             f.createNewFile();
             return true;
         }
         else{
             return false;
         }
    }
    
    public File[] listofdir(){
    File dir= new File("E:/Test");
        
    File [] list = dir.listFiles();
       
       return list;
//       for(File file : list)
//       {
//           if(file.isDirectory()){
//               System.out.println("Directory: "+ file.getName());
//           }
//           else
//           {
//               System.out.println("File: "+ file.getName());
//           }
//           
//       
//       }
//    
    
    }
    
    
    public void copyDirectory(File sourceLocation , File targetLocation)
    throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    
    public boolean Fileexists(String name){
     File f = new File(maindir+"/"+name);
        return f.exists();
    }
    
   


    
}
