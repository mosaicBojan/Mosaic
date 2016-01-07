/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Asus
 */
public class MessageListener extends Thread{
    private PrintWriter out;
    private BufferedReader in;
    
    public MessageListener(PrintWriter out, BufferedReader in){
        super();
        this.out = out;
        this.in = in;
    }
    
    public void run(){
        boolean test = true;
        //while(test){
            try {
                System.out.println("Receive message from server");
                
                String msg = in.readLine();
                System.out.println("MSG: " + msg);
                out.println("Hello Server");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        //}
    }
}
