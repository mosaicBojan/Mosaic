package mosaicserver;

import activationkeys.ActivationKeyController;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Asus
 */
public class Server extends Thread {
    private ActivationKeyController activationKeyController;
    
    public Server(ActivationKeyController activationKeyController) {
        super();
        this.activationKeyController = activationKeyController;
        System.out.println("Server created!");
    }

    @Override
    public void run() {
        try {
            System.out.println("Server running...");
            ServerSocket ss = new ServerSocket(9000);
            while (true) {
                Socket sc = ss.accept();
                ServerCommunicationThread sct = new ServerCommunicationThread(sc, activationKeyController);
                sct.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
