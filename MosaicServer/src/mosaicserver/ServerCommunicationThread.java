package mosaicserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import users.*;
import activationkeys.*;
import javafx.stage.Stage;

/**
 *
 * @author Asus
 */
public class ServerCommunicationThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ActivationKeyController activationKeyController;

    public ServerCommunicationThread() {
        super();
    }

    public ServerCommunicationThread(Socket socket, ActivationKeyController activationKeyController) {
        super();
        this.socket = socket;
        this.activationKeyController = activationKeyController;
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
            //ActivationKeyController k = new ActivationKeyController();
            
            boolean test = true;
            while (test) {
                try {
                    String typeOfMsg = "";
                    typeOfMsg = in.readLine();
                    if("registration".equals(typeOfMsg.split("#")[0])){
                        String requestKey = typeOfMsg.split("#")[1];
                        if(activationKeyController.isKeyInList(requestKey)){
                            activationKeyController.removeActivationKey(requestKey);
                            out.println("registration#keyIsOK");
                        }
                        else{
                            out.println("registration#keyIsNotOK");
                        }
                        
                    }
                    //System.out.println("Send message to client");
                    //out.println("Hello Client");
                    //String msg = in.readLine();
                    //System.out.println("SERVER MSG: " + msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            
            in.close();
            out.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}