/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
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
    private FXMLDocumentController docController;
    
    public MessageListener(FXMLDocumentController docController, PrintWriter out, BufferedReader in, Button validationScreenValidateButton){
        super();
        this.out = out;
        this.in = in;
        this.docController = docController;
        //this.validationScreenValidateButton = validationScreenValidateButton;
        
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
                        //System.out.println("Button: " + validationScreenValidateButton);
                        //stage = (Stage) docController.getValidationScreenValidateButton().getScene().getWindow();
                        //System.out.println("Listener stage: " + stage);
                        //Mosaic.closeStage("validationScreen");
                        //(Mosaic.getCurrentStage("validationScreen")).close();
                        //stage.close();
                    }
                    else{
                        //Neuspjesna registracija
                        System.out.println("Neuspjesna registracija");
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
