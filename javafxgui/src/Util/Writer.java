/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.Scanner;

/**
 *
 * @author user
 */
public class Writer implements Runnable{
    public NetworkConnection netConnection;
    
    public Writer(NetworkConnection nc){
        netConnection=nc;
    }
    
    @Override
    public void run() {
        
        Data data=new Data();
        
        while(true){
            
            Scanner in=new Scanner(System.in);
            
            data.message=in.nextLine();            
            try{
                netConnection.write(data.clone());
            }            
            catch(Exception ex){
                System.out.println("sending failed");
            }
        }
    }
    
}
