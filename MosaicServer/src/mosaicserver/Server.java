/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicserver;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Asus
 */
public class Server extends Thread {
    
    
    public Server() {
        super();
        System.out.println("Server created!");
    }

    @Override
    public void run() {
        try {
            System.out.println("Server running...");
            ServerSocket ss = new ServerSocket(9000);
            while (true) {
                Socket sc = ss.accept();
                ServerCommunicationThread sct = new ServerCommunicationThread(sc);
                sct.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
