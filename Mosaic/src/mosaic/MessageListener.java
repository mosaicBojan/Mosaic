/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Asus
 */
public class MessageListener extends Thread{
    private PrintWriter out;
    private BufferedReader in;
    private Stage stage;
    
    public MessageListener(PrintWriter out, BufferedReader in){
        super();
        this.out = out;
        this.in = in;
    }
    
    public void run(){
        boolean test = true;
        while(test){
            
            try {
                String typeOfMsg = "";
                typeOfMsg = in.readLine();
                if("registration".equals(typeOfMsg.split("#")[0])){
                    if("keyIsOK".equals(typeOfMsg.split("#")[1])){
                        //Uspjesna registracija
                        System.out.println("Uspjena registracija");
                        ActivationTest at = new ActivationTest();
                        at.setIsAvtivated(true);
                        at.serializeActivationTest();
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                stage.hide();
                            }
                        });
                    }
                    else{
                        //Neuspjesna registracija
                        System.out.println("Neuspjesna registracija");
                    }
                }
                else if("login".equals(typeOfMsg.split("#")[0])){
                    if("usernameIsAvailable".equals(typeOfMsg.split("#")[1])){
                        System.out.println("Uspjena prijava");
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                stage.hide();
                            }
                        });
                    }
                    else{
                        System.out.println("Neuspjesna prijava");
                    }
                }
                else if("QUIT".equals(typeOfMsg.split("#")[0])){
                    try{
                        out.close();
                        in.close();
                        test = false;
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
}
