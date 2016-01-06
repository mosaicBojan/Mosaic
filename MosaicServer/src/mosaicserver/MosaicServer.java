/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author John
 */
public class MosaicServer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        
        ActivationKeyController k = new ActivationKeyController();
        System.out.println(k.addActivationKey("12341.341234234s"));
        System.out.println(k.getActivationKeyList().size());
        System.out.println(k.addActivationKey("123412341234234s"));
        System.out.println(k.getActivationKeyList().size());
        //System.out.println(k.sadrzi("123412341234234s"));
        System.out.println(k.addActivationKey("123412341234234s"));
        System.out.println(k.getActivationKeyList().size());
        System.out.println(k.addActivationKey("1111111111111111"));
        System.out.println(k.getActivationKeyList().size());
        System.out.println(k.addActivationKey("1111111111111111"));
        System.out.println(k.getActivationKeyList().size());
        System.out.println(k.removeActivationKey("123412341234234s"));
        System.out.println(k.getActivationKeyList().size());
        System.out.println(k.removeActivationKey("123412341234234s"));
        System.out.println(k.getActivationKeyList().size());
        
        
        
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
