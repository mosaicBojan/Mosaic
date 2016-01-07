/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicserver;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author John
 */
public class FXMLDocumentController implements Initializable {
    //private static boolean isFirstTimeRun = true;
    private Stage app_stage;
    
    @FXML
    private Button keysAddButton;

    @FXML
    private ListView keysListView;

    @FXML
    private Button keysRemoveButton;

    @FXML
    private Button addKeyFormButton;

    @FXML
    private TextField addKeyFormTextField;

    @FXML
    private Label addKeyFormLabel;

    @FXML
    public void keysAddButtonAction(ActionEvent event) {

    }
    
    /*@FXML
    void keysAddButtonAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAddKeyForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(keysAddButton.getScene().getWindow());
        app_stage.showAndWait();
    }*/
    
    /*@FXML
    void keysAddButtonAction(ActionEvent event) {

    }*/
    /*@FXML
    public void addKeyFormButtonAction(ActionEvent event) {
        
    }*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
