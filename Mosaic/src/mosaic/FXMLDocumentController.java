/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import treeviewclasses.FileSystemTree;
import treeviewclasses.NodeOfTree;
import virtualalbums.*;


/**
 *
 * @author Asus
 */
public class FXMLDocumentController implements Initializable {
    
    private static FileSystemTree fst = null;
    private static VirtualAlbumsController virtualAlbumsController = null;
    private static boolean isFirstTime = true;
    private Stage app_stage;
    private String folderName;
    private static String selectedPath;
    private boolean isMoveToAlbumButtonPressed = false;
    
    
    @FXML
    private TreeView explorerTreeView;
    
    @FXML
    private ImageView explorerImgView;
    
    @FXML
    private TextField explorerPathTextField;
    
    @FXML
    private Button explorerBtn2;
    
    @FXML
    private Label explorerImageLabel;
    
    @FXML
    private TextField folderNameTextField;

    @FXML
    private Button createButton;
    
    @FXML
    private Button explorerBtn1;

    @FXML
    private TextField explorerRenameTextField;

    @FXML
    private Button explorerRenameBtn;

    @FXML
    private Button exitFullscreenBtn;

    @FXML
    private ImageView fullscreenImageView;
    
    @FXML
    private GridPane albumsGridPane1;
    
    @FXML
    private TextField albumNameTextField;

    @FXML
    private TextField albumDescriptionTextField;

    @FXML
    private Button createAlbum;
    
    @FXML
    private Button createAlbumButton;

    @FXML
    private ChoiceBox moveImagePopUpChoiceBox;

    @FXML
    private Button explorerMoveImageButton;
    
    @FXML
    private GridPane albumImagesGridPane1;

    
    
    
    // Kreiranje novog foldera //
    @FXML
    void explorerBtn1Action(ActionEvent event) throws IOException{
        selectedPath = fst.getSelectedPath();
        System.out.println("Select: " + selectedPath);
        if(selectedPath != null){
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLCreateNewFolderForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.showAndWait();
        }
    }
    
    @FXML
    void createButtonAction(ActionEvent event) throws IOException{
        folderName = folderNameTextField.getText();
        
        if(folderName.equals("")){
            System.out.println("Unesite naziv foldera!");
        }
        else{
            app_stage = (Stage) createButton.getScene().getWindow();
            app_stage.close();
            createNewFolder(folderName);
        }
    }
    
    private void createNewFolder(String folderName) {
        String path = selectedPath;
        path += File.separator;
        path += folderName;
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        fst.addNewFolderNodeToTree(file);
    }
    
    
    // Brisanje fajlova i foldera //
    @FXML
    void explorerBtn2Action(ActionEvent event){
        if (!explorerPathTextField.getText().equals("")) {
            File file = new File(explorerPathTextField.getText());
            deleteFile(file);
        }
    }
    
    public static void deleteFile(File file){
        if(!file.isDirectory()){
            file.delete();
        }
        else{
            if(file.list().length == 0){
                file.delete();
            }
            else{
                deleteNoEmptyFolder(file);
            }
        }
        
        fst.removeOneNode(file);
    }
    
    public static boolean deleteNoEmptyFolder(File file){
        File[] filesInFolder = file.listFiles();
        for(File f: filesInFolder){
            if(f.isFile()){
                f.delete();
                System.out.println("Deleted file: " + f.getPath());
            }
            else if(f.isDirectory()){
                if(f.list().length == 0){
                    f.delete();
                    System.out.println("Deleted empty folder: " + f.getPath());
                }
                else{
                    deleteNoEmptyFolder(f);
                }
            }
        }
        return (file.delete());
    }
    
    
    // Rename file or folder //
    @FXML
    void explorerBtn3Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if(selectedPath != null){
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLRenameFileForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.showAndWait();
        }
    }
    
    @FXML
    void explorerRenameBtnAction(ActionEvent event) throws IOException {
        String newName = explorerRenameTextField.getText();
        
        if("".equals(newName)){
            System.out.println("Unesite naziv novog fajla!");
        }
        else{
            app_stage = (Stage) explorerRenameBtn.getScene().getWindow();
            app_stage.close();
            int index = selectedPath.lastIndexOf("\\");
            String tempPath = selectedPath.substring(0, index);
            tempPath += File.separator;
            tempPath += newName;
            System.out.println("Old file: " + selectedPath);
            System.out.println("New file: " + tempPath);
            renameFile(new File(selectedPath), new File(tempPath));
        }
    }
    
    public void renameFile(File oldFile, File newFile){
        
        try{
        if (newFile.exists()) {
            throw new java.io.IOException("file exists");
        }
        } catch(Exception ex){
            System.out.println("Naziv vec postoji!");
        }

        // Rename file (or directory)
        boolean success = oldFile.renameTo(newFile);

        if (!success) {
            // File was not successfully renamed
            System.out.println("File was not successfully renamed!");
        }
        else{
            fst.exchangeTwoNodes(oldFile, newFile);
        }
    }
    
    
    // Copy file or folder //
    @FXML
    void explorerBtn5Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if (selectedPath != null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Where you want to save file");
            dirChooser.setInitialDirectory(new File(File.separator));
            File choosenFolder = dirChooser.showDialog(app_stage);
            System.out.println(choosenFolder.getPath());
            if (new File(selectedPath).isDirectory()) {
                File srcDir = new File(selectedPath);
                File destDir = new File(choosenFolder.getPath() + File.separator + srcDir.getName());
                copyFolder(srcDir, destDir);
            } else {
                File srcDir = new File(selectedPath);
                File destDir = new File(choosenFolder.getPath() + File.separator + srcDir.getName());
                copyFile(srcDir, destDir);
            }
        }
    }
    
    public static void copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }
            
            String files[] = source.list();
            
            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                
                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = null;
            OutputStream out = null;
            
            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);
                
                byte[] buffer = new byte[1024];
                
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    
    private static void copyFile(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    // MOVE ACTION //
    @FXML
    void explorerBtn6Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if (selectedPath != null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Where you want to move file");
            dirChooser.setInitialDirectory(new File("\\"));
            File choosenFolder = dirChooser.showDialog(app_stage);
            //System.out.println(choosenFolder.getPath());
            if (choosenFolder != null) {
                if (new File(selectedPath).isDirectory()) {
                    File srcDir = new File(selectedPath);
                    File destDir = new File(choosenFolder.getPath() + File.separator + srcDir.getName());
                    moveFolder(srcDir, destDir);
                    File file = new File(explorerPathTextField.getText());
                    //deleteFile(file);
                } else {
                    File srcFile = new File(selectedPath);
                    File destDir = new File(choosenFolder.getPath() + File.separator + srcFile.getName());
                    moveFile(srcFile, destDir);
                    //deleteFile(srcFile);
                }
            }
        }
        deleteFile(new File(selectedPath));
    }
    
    
    
    public static void moveFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }
            
            String files[] = source.list();
            
            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                
                moveFolder(srcFile, destFile);
                //deleteFile(srcFile);
            }
        } else {
            InputStream in = null;
            OutputStream out = null;
            
            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);
                
                byte[] buffer = new byte[1024];
                
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                
                try {
                    out.close();
                    //deleteFile(source);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    
    private static void moveFile(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
            deleteFile(source);
        }
    }
    
    // for OPEN acion //
    @FXML
    void explorerBtn4Action(ActionEvent event) {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if (selectedPath != null) {
            if (!new File(selectedPath).isDirectory()) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File(selectedPath);
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    
    @FXML
    void explorerBtn7Action(ActionEvent event) throws IOException {
        isMoveToAlbumButtonPressed = true;
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);

        if (selectedPath != null && new File(selectedPath).isFile()) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLExplorerMoveToAlbum.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.show();
        }
        
    }
    
    @FXML
    private void explorerMoveImageButtonAction(ActionEvent event) {
        String nameOfAlbum = (String) moveImagePopUpChoiceBox.getValue();
        System.out.println("Choosen value: " + nameOfAlbum);
        ArrayList<VirtualAlbum> albums = virtualAlbumsController.getVirtualAlbumList();
        AlbumImage image = new AlbumImage(new File(selectedPath).getName(), new File(selectedPath));
        for(VirtualAlbum v: albums){
            if(v.getName().equals(nameOfAlbum)){
                v.addImage(image);
            }
        }
        app_stage = (Stage) explorerMoveImageButton.getScene().getWindow();
        app_stage.close();
    }
    
    
    // FULLSCREEN Action //
    @FXML
    void explorerBtn8Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        if (selectedPath != null && new File(selectedPath).isFile()) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLFullscreenForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent, width, height);
            app_stage = new Stage();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.show();
        }
        if (selectedPath != null) {
            File file = new File(selectedPath);
            Image image = new Image(file.toURI().toString());
            fullscreenImageView.setImage(image);
        }
        
    }

     @FXML
    void exitFullscreenBtnAction(ActionEvent event) {
        app_stage = (Stage) exitFullscreenBtn.getScene().getWindow();
        app_stage.close();
    }
    
    
    
    /****************************** ALBUM TAB *******************************/
    @FXML
    void albumsNewAlbumButtonAction(ActionEvent event) throws IOException {
        if(virtualAlbumsController.getNumberOfAlbums() != 30){
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLNewAlbumForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(explorerBtn1.getScene().getWindow());
        app_stage.showAndWait();
        }
        else{
            //Ako ne moze da stane vise albuma//
        }
    }
    
    @FXML
    void createAlbumButtonAction(ActionEvent event) {
        String albumName = albumNameTextField.getText();
        String albumDescription = albumDescriptionTextField.getText();
        
        if(!albumName.equals("")){
            if (virtualAlbumsController.getVirtualAlbumList().size() != 0) {
                boolean isValidName = virtualAlbumsController.isAlbumNameValid(albumName);
                    if (!isValidName) {
                        //Ako ime vec postoji//
                    } else {
                        VirtualAlbum album = new VirtualAlbum(albumName, albumDescription);
                        virtualAlbumsController.addVirtualAlbum(album);
                        virtualAlbumsController.addAlbumToGridPane(album);
                        app_stage = (Stage) createAlbumButton.getScene().getWindow();
                        app_stage.close();
                    }
            }
            else{
                VirtualAlbum album = new VirtualAlbum(albumName, albumDescription);
                virtualAlbumsController.addVirtualAlbum(album);
                virtualAlbumsController.addAlbumToGridPane(album);
                
                app_stage = (Stage) createAlbumButton.getScene().getWindow();
                app_stage.close();
            }
        }
        else{
            //Ako nije uneseno ime//
        }
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize");
        if (isFirstTime) {
            isFirstTime = false;
            fst = new FileSystemTree(explorerTreeView, explorerImgView, explorerPathTextField, explorerImageLabel);
            fst.start();
            virtualAlbumsController = new VirtualAlbumsController(albumsGridPane1, albumImagesGridPane1);
        }
        ObservableList<String> albumNames = virtualAlbumsController.getAllAlbumsName();
        try {
            moveImagePopUpChoiceBox.getItems().addAll(albumNames);
        } catch (Exception ex) {

        }
    }    
    
}
