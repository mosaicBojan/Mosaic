package mosaicserver;

import activationkeys.ActivationKeyController;
import java.net.ServerSocket;
import java.net.Socket;
import users.UsersController;


/**
 *
 * @author Asus
 */
public class Server extends Thread {
    private ActivationKeyController activationKeyController;
    private UsersController usersController;
    private static ServerSocket ss;
    //private ArrayList<ServerCommunicationThread> threads = new ArrayList<>();
    
    public Server(ActivationKeyController activationKeyController, UsersController usersController) {
        super();
        this.activationKeyController = activationKeyController;
        this.usersController = usersController;
        System.out.println("Server created!");
    }

    public static ServerSocket getServerSocket() {
        return ss;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server running...");
            ss = new ServerSocket(9000);
            boolean test = true;
            while (test) {
                try {
                    Socket sc = ss.accept();
                    ServerCommunicationThread sct = new ServerCommunicationThread(sc, activationKeyController, usersController);
                    sct.start();
                } catch (Exception ex) {
                    test = false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
