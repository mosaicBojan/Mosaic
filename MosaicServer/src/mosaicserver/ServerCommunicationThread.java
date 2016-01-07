/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Asus
 */
public class ServerCommunicationThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerCommunicationThread() {
        super();
    }

    public ServerCommunicationThread(Socket socket) {
        super();
        this.socket = socket;
        System.out.println("Server thread creating...");
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Server thread created!");
    }

    public void run() {
        try {
            System.out.println("Server running... " + super.getId());
            ActivationKeyController k = new ActivationKeyController();
            
            boolean test = true;
            //while (test) {
                try {
                    System.out.println("Send message to client");
                    out.println("Hello Client");
                    String msg = in.readLine();
                    System.out.println("SERVER MSG: " + msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            //}
            
            
            in.close();
            out.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
