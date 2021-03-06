/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicserver;

import activationkeys.ActivationKeyController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import users.User;
import users.UsersController;

/**
 *
 * @author John
 */
public class FXMLServerDocumentController implements Initializable {
    private static boolean isFirstTime = true;
    private static ActivationKeyController activationKeyController;
    private static UsersController usersController;
    private Stage app_stage;
    private String selectedItem;
    private static Server server;
    
    @FXML
    private Button keysAddButton;

    @FXML
    private Button keysRemoveButton;
    
    @FXML
    private Button addKeyFormButton;

    @FXML
    private TextField addKeyFormTextField;
    
    @FXML
    private ListView<String> keysListView;
    
    @FXML
    private ListView<User> onlineUsersListView;
    
    @FXML
    private TextArea trafficTextArea;

    public void setTextToTrafficTextArea(String s){
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                trafficTextArea.appendText(s);
            }
        });
        
    }
    
    
    @FXML
    void addKeyFormButtonAction(ActionEvent event) throws IOException {
        String key = addKeyFormTextField.getText();
        if(activationKeyController.isValidKey(key)){
            activationKeyController.addActivationKey(key);
            app_stage = (Stage) addKeyFormButton.getScene().getWindow();
            app_stage.close();
        }
    }
    
    @FXML
    public void keysAddButtonAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAddKeyForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(keysAddButton.getScene().getWindow());
        app_stage.showAndWait();
    }
    
    
     @FXML
    void keysRemoveButtonAction(ActionEvent event) {
        activationKeyController.removeActivationKey(selectedItem);
    }
    
    @FXML
    void shutDownButtonAction(ActionEvent event) throws IOException {
        app_stage = (Stage) keysAddButton.getScene().getWindow();
        app_stage.close();
        Server.getServerSocket().close();
        server.interrupt();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Sever Initialize");
        if(isFirstTime){
            System.out.println("Server set up!");
            activationKeyController = new ActivationKeyController(keysListView);
            usersController = new UsersController(onlineUsersListView);
            server = new Server(activationKeyController, usersController, this);
            server.start();
            isFirstTime = false;
            
            keysListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    selectedItem = keysListView.getSelectionModel().getSelectedItem();
                } 

            });
        }
    }    
    
}
