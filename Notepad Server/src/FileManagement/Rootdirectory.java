package FileManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Rootdirectory {
    public String maindir =  "C:/Test";
    
    
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
    
    private void handlebackspace(String filename ,String Username , String txt) throws IOException{
        File f = new File(maindir+"/"+Username+"/"+filename);
        
        int backnum = Integer.valueOf(txt) +2 ;
        
        BufferedReader reader = new BufferedReader(new FileReader (f));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");
        String filetxt;
        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            filetxt = stringBuilder.toString();
            System.out.println("<From file>"+filetxt +"<Removw>"+backnum);
        } finally {
            reader.close();
        }
//        f.delete();
        File fil = new File(maindir+"/"+Username+"/"+filename);
        filetxt  = filetxt.substring(0, filetxt.length()-backnum);
        System.out.println(filetxt);
        FileWriter fileWriter;
        
        fileWriter = new FileWriter(fil);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(filetxt);
        bufferedWriter.flush();
//        fil.createNewFile();
        
        bufferedWriter.close();
        fileWriter.close();
     
    }
    
    public void writetofile(String filename ,String Username , String txt) throws IOException{
        File f = new File(maindir+"/"+Username+"/"+filename);
        FileWriter fileWriter;
        if(f.exists()){
        fileWriter = new FileWriter(f,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        if(txt.equals("ENTER")){
        bufferedWriter.newLine();
        }
        else if(txt.length()>10){
            String [] parse = txt.split(":");
//            System.out.println("Backspace count "+ parse[1]);
            bufferedWriter.close();
            fileWriter.close();
            handlebackspace(filename, Username, parse[1]);
        }
        else{
        bufferedWriter.write(txt);
//        System.out.println("Write to file complete "+txt);
        }
        bufferedWriter.close();
        fileWriter.close();
        }
    }
    
    public String readFile(String Username, String docname) throws IOException {
    
    File f = new File(maindir+"/"+Username+"/"+docname);
    BufferedReader reader = new BufferedReader(new FileReader (f));
    String         line = null;
    StringBuilder  stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");

    try {
        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return stringBuilder.toString();
    } finally {
        reader.close();
    }
    }
    
    public String[] getsharedwith(String docname, String Owner) throws IOException {
        ArrayList<String> sharedusers = new ArrayList<>();
        String[] list = null;
        File f = new File(maindir+"/"+Owner+"/Sharedwith.txt");
        BufferedReader reader = new BufferedReader(new FileReader (f));
        String  line = null;
    
    try {
        while((line = reader.readLine()) != null) {
            String file[] = line.split(":",2);
            String docnam = file[1];
            String shareduser = file[0];
//            System.out.println(file[1]);
            if(docnam.equals(docname)){
            sharedusers.add(shareduser);
            }
        }
        
        list=sharedusers.toArray(new String[0]);
        return list;
        
    }catch( IOException e){
        System.out.println("In list of shared "+e);
    }
    finally {
        reader.close();
        
    }
    return list;
    
    }
    
    public void sharedwith(String Username,String docname, String user) throws IOException{
        File f = new File(maindir+"/"+Username+"/Sharedwith.txt");
        FileWriter fileWriter;
        fileWriter = new FileWriter(f,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(user+":"+docname);
        bufferedWriter.newLine();
//        fileWriter.write(user+":"+docname+"\r\n");
        bufferedWriter.close();
        fileWriter.close();
        sharedto(user, docname, Username);
    }
    
    private void sharedto(String Username, String docname , String sharedby) throws IOException{
        File f = new File(maindir+"/"+Username+"/Sharedtome.txt");
        FileWriter fileWriter;
        fileWriter = new FileWriter(f, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(sharedby+":"+docname);
        bufferedWriter.newLine();
        bufferedWriter.close();
//        fileWriter.write(sharedby+":"+docname+";");
        fileWriter.close();
    }
    
    public boolean userreg(String Username) throws IOException{
    File f = new File(maindir+"/"+Username+"/Sharedtome.txt");
    File fw = new File(maindir+"/"+Username+"/Sharedwith.txt");
    
         if(!f.exists()){
            f.createNewFile();
            fw.createNewFile();
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
    
    public boolean userdir(String Username) throws FileNotFoundException{
        File f = new File(maindir+"/"+Username);
         if(!f.exists())
       {
           f.mkdir();
           System.out.println("Directory Created");
           return true;
       }
       else{
           System.out.println("Directory Already exists");
           return false;
       
       }
    }
    
    public boolean checkfileexist(String name , String filename){
         File f = new File(maindir+"/"+name+"/"+filename+".txt");
         if(f.exists()){
             
             return false;
         }
         else{
             return true;
         }
    
    
    }
    
    public boolean makenewfile(String name , String filename) throws IOException{
         File f = new File(maindir+"/"+name+"/"+filename+".txt");
         if(!f.exists()){
             f.createNewFile();
             return true;
         }
         else{
             return false;
         }
    }
    
    public File[] listofdir(String Username){
    File dir= new File(maindir+"/"+Username);
        
    File [] list = dir.listFiles();
       
       return list;

    }
    
    public File[] listofshared(String Username) throws FileNotFoundException, IOException{
    ArrayList<File> sharedusers = new ArrayList<>();
    File [] list = null ;
    int i=0;
    File shared = new File(maindir +"/"+Username+"/Sharedtome.txt");
    BufferedReader reader = new BufferedReader(new FileReader (shared));
    String  line = null;
    
    try {
        while((line = reader.readLine()) != null) {
            String file[] = line.split(":",2);
            String Owner = file[0];
            String File = file[1];
//            System.out.println(file[1]);
            File myfile = new File(maindir +"/"+Owner+"/"+File);
            sharedusers.add(myfile);
            
        }
        list=sharedusers.toArray(new File[0]);
        return list;
    }catch( IOException e){
        System.out.println("In list of shared "+e);
    }
    finally {
        reader.close();
        
    }
    
    return list;
    
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

    public File getfileobj(String name, String filename) {
        File f = new File(maindir+"/"+name+"/"+filename);
        if(f.exists()){
            return f;
        }
        else{
            return null;
        }
    }

    public String[] getspecificshared(String file, String Owner) throws FileNotFoundException, IOException {
        ArrayList<String> sharedusers = new ArrayList<>();
        File f = new File(maindir+"/"+Owner+"/Sharedwith.txt");
        BufferedReader reader = new BufferedReader(new FileReader (f));
        String  line = null;
        String [] list = null;
    
        try {
        while((line = reader.readLine()) != null) {
            String info[] = line.split(":",2);
           
            System.out.println(info[1]);
            if(file.equals(info[1])){
            sharedusers.add(info[0]);
            }
            
        }
        list=sharedusers.toArray(new String[0]);
        return list;
    }catch( IOException e){
        System.out.println("In list of shared "+e);
    }
    finally {
        reader.close();
        
    }
    
    return list;
    
    
    }

    public boolean deletefile(String Username, String filename) throws FileNotFoundException, IOException {
        File f = new File(maindir+"/"+Username+"/"+filename);
        if(!f.exists()){return false;}
        else{
            boolean confirm = f.delete();
            if(!confirm){return false;}
            else{
                File shared = new File(maindir+"/"+Username+"/Sharedwith.txt");
                BufferedReader reader = new BufferedReader(new FileReader (shared));
                String  line = null;
                String msg = null ;
                StringBuilder  stringBuilder = new StringBuilder();
                String ls = System.getProperty("line.separator");

                try {
                while((line = reader.readLine()) != null) {
                    String info[] = line.split(":",2);
                    if(filename.equals(info[1])){
                        System.out.println("Found File:"+info[1]);
                        appendshareduser(info[0], filename);
                        continue;
                    }
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                    
                }
                msg = stringBuilder.toString();
                    System.out.println(msg);
                }catch( IOException e){
                    System.out.println("In list of shared "+e);
                }
                finally {
                    reader.close();
                    
                }
                
                FileWriter fileWriter;
                fileWriter = new FileWriter(shared);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(msg);
                bufferedWriter.flush();
//                bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
                
            }
        
        }
    
        return true;
        
    }
    
    private boolean appendshareduser(String Username , String filename) throws IOException{
        File shared = new File(maindir+"/"+Username+"/Sharedtome.txt");
        if(!shared.exists()){return false;}
        else{
            BufferedReader reader = new BufferedReader(new FileReader (shared));
            String  line = null;
            String msg = null ;
            StringBuilder  stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

                try {
                while((line = reader.readLine()) != null) {
                    String info[] = line.split(":",2);
                    if(filename.equals(info[1])){
                        System.out.println("Found File:"+info[1]);
                       
                        continue;
                    }
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                    
                }
                msg = stringBuilder.toString();
                
                }catch( IOException e){
                    System.out.println("In list of shared "+e);
                }
                finally {
                    reader.close();
                    
                }
                
                FileWriter fileWriter;
                fileWriter = new FileWriter(shared);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(msg);
//                bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
              
        
        }
    
        return true;
    
    }

    public void updateaccess(String filename, String Username, ArrayList<String> list) throws FileNotFoundException, IOException {
    
        File shared = new File(maindir+"/"+Username+"/Sharedwith.txt");      
        BufferedReader reader = new BufferedReader(new FileReader (shared));
        String  line = null;
        String msg = null ;
        StringBuilder  stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        String[] users = list.toArray(new String[0]);
        
        try {
            while((line = reader.readLine()) != null) {
                String info[] = line.split(":",2);
                if(filename.equals(info[1])){
                    System.out.println("Found File:"+info[0]+":"+info[1]);
                    removeaccess(info[0], filename, Username);
                    continue;
                }
                stringBuilder.append(line);
                stringBuilder.append(ls);
                
            }
            for(String x :users){
                line = x +":"+filename;
                updateshared(x,filename , Username);
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            msg = stringBuilder.toString();

            }catch( IOException e){
                System.out.println("In list of shared "+e);
            }
            finally {
                reader.close();

            }

            FileWriter fileWriter;
            fileWriter = new FileWriter(shared);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(msg);
//                bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

        
        }
    
    private void removeaccess(String Username, String filename, String Owner) throws FileNotFoundException, IOException{
    File shared = new File(maindir+"/"+Username+"/Sharedtome.txt");      
        BufferedReader reader = new BufferedReader(new FileReader (shared));
        String  line = null;
        String msg = null ;
        StringBuilder  stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        
        try {
            while((line = reader.readLine()) != null) {
                String info[] = line.split(":",2);
                if(filename.equals(info[1])){
                    System.out.println("Found File:"+info[1]);

                    continue;
                }
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
             
                msg = stringBuilder.toString();

            }catch( IOException e){
                System.out.println("In list of shared "+e);
            }
            finally {
                reader.close();

            }

            FileWriter fileWriter;
            fileWriter = new FileWriter(shared);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(msg);
            bufferedWriter.flush();
//                bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

    }
     private void updateshared(String Username, String filename, String Owner) throws FileNotFoundException, IOException{

        File shared = new File(maindir+"/"+Username+"/Sharedtome.txt");      
        BufferedReader reader = new BufferedReader(new FileReader (shared));
        String  line = null;
        String msg = null ;
        StringBuilder  stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        
        try {
            while((line = reader.readLine()) != null) {
                String info[] = line.split(":",2);
                if(filename.equals(info[1])){
                    System.out.println("Found File:"+info[1]);

                    continue;
                }
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
                line = Owner +":"+filename;
              
                stringBuilder.append(line);
                stringBuilder.append(ls);
            
                msg = stringBuilder.toString();

            }catch( IOException e){
                System.out.println("In list of shared "+e);
            }
            finally {
                reader.close();

            }

            FileWriter fileWriter;
            fileWriter = new FileWriter(shared);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(msg);
            bufferedWriter.flush();
//                bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

     }
    
    }
   
