/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Asus
 */
public class Mosaic extends Application {
    private static HashMap<String, Stage> stringStageHashMap = new HashMap<String, Stage>();
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        // setting min stage dimensions
        stage.setMinHeight(700);
        stage.setMinWidth(600);

        stage.setScene(scene);
        stage.show();
        //stage.hide();
        stage.hide();
        
        ActivationTest activationTest = new ActivationTest();
        boolean isActivated = activationTest.isValidate();
        if (!isActivated) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLValidationScreen.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            Stage app_stage = new Stage();
            stringStageHashMap.put("validationScreen", app_stage);
            app_stage.setScene(create_folder_scene);
            app_stage.showAndWait();
            System.out.println("Wait");
            if (!FXMLDocumentController.getIsRegistrationQuit()) {
                Parent home_page_parent_2 = FXMLLoader.load(getClass().getResource("FXMLLoginScreen.fxml"));
                Scene create_folder_scene_2 = new Scene(home_page_parent_2);
                Stage app_stage_2 = new Stage();
                app_stage_2.setScene(create_folder_scene_2);
                //FXMLDocumentController.setIsLoginEnd();
                app_stage_2.showAndWait();
                if(!FXMLDocumentController.getIsLoginQuit()){
                    stage.show();
                }
                else{
                    stage.close();
                }
                
            }
            else{
                stage.close();
            }
        } else {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLLoginScreen.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            Stage app_stage = new Stage();
            app_stage.setScene(create_folder_scene);
            //FXMLDocumentController.setIsLoginEnd();
            app_stage.showAndWait();
            if (!FXMLDocumentController.getIsLoginQuit()) {
                stage.show();
            } else {
                stage.close();
            }
        }
        
    }

    public static void showRootStage(){
        stage.show();
    }
    
    public static Stage getCurrentStage(String nameOfStage){
        return stringStageHashMap.get(nameOfStage);
    }
    
    public static void closeStage(String stageName){
        getCurrentStage(stageName).close();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
