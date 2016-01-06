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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import treeviewclasses.FileSystemTree;
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
    private static String selectedAlbum;
    private boolean isMoveToAlbumButtonPressed = false;
    private static String type;
    private static String selectedImageName;
    private List<File> choosenFiles;

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
    private TextField albumNameTextField;

    @FXML
    private TextField albumDescriptionTextField;

    @FXML
    private Button createAlbum;

    @FXML
    private Button createAlbumPopUpButton;

    @FXML
    private ChoiceBox moveImagePopUpChoiceBox;

    @FXML
    private Button explorerMoveImageButton;

    @FXML
    private Label albumsNavigationLabel;

    @FXML
    private Label albumNameLabel;

    @FXML
    private Label albumDescriptionLabel;

    @FXML
    private Label albumOrImageNameLabel;

    @FXML
    private Label descriptionTempLabel;

    @FXML
    private Button renameAlbumPopUpButton;

    @FXML
    private TextField renameAlbumPopUpTextField;

    @FXML
    private FlowPane albumsFlowPane;

    @FXML
    private FlowPane imagesFlowPane;
    
    @FXML
    private ChoiceBox albumsImportPopUpChoiseBox;

    @FXML
    private Button albumsImportPopUpAddToAlbumButton;

    @FXML
    private Button albumsImportPopUpBrowseButton;

    @FXML
    private Label albumsImportPopUpChoosenNumLabel;

    @FXML
    private ScrollPane albumsScrollPane;
    
    @FXML
    private ScrollPane imagesScrollPane;
    
    
    
    // Kreiranje novog foldera //
    @FXML
    void explorerBtn1Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Select: " + selectedPath);
        if (selectedPath != null) {
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
    void createButtonAction(ActionEvent event) throws IOException {
        folderName = folderNameTextField.getText();

        if (folderName.equals("")) {
            System.out.println("Unesite naziv foldera!");
        } else {
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
    void explorerBtn2Action(ActionEvent event) {
        if (!explorerPathTextField.getText().equals("")) {
            File file = new File(explorerPathTextField.getText());
            deleteFile(file);
        }
    }

    public static void deleteFile(File file) {
        if (!file.isDirectory()) {
            file.delete();
        } else {
            if (file.list().length == 0) {
                file.delete();
            } else {
                deleteNoEmptyFolder(file);
            }
        }

        fst.removeOneNode(file);
    }

    public static boolean deleteNoEmptyFolder(File file) {
        File[] filesInFolder = file.listFiles();
        for (File f : filesInFolder) {
            if (f.isFile()) {
                f.delete();
                System.out.println("Deleted file: " + f.getPath());
            } else if (f.isDirectory()) {
                if (f.list().length == 0) {
                    f.delete();
                    System.out.println("Deleted empty folder: " + f.getPath());
                } else {
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
        if (selectedPath != null) {
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

        if ("".equals(newName)) {
            System.out.println("Unesite naziv novog fajla!");
        } else {
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

    public void renameFile(File oldFile, File newFile) {

        try {
            if (newFile.exists()) {
                throw new java.io.IOException("file exists");
            }
        } catch (Exception ex) {
            System.out.println("Naziv vec postoji!");
        }

        // Rename file (or directory)
        boolean success = oldFile.renameTo(newFile);

        if (!success) {
            // File was not successfully renamed
            System.out.println("File was not successfully renamed!");
        } else {
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

    
    ///////////////////  ADD IMAGE TO ALBUM FROM EXPLORER  //////////////////////
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
        if (nameOfAlbum != null && !"".equals(nameOfAlbum)) {
            System.out.println("Choosen value: " + nameOfAlbum);
            AlbumImage image = new AlbumImage(new File(selectedPath).getName(), new File(selectedPath));
            VirtualAlbum album = virtualAlbumsController.getAlbumForString(nameOfAlbum);
            album.addImage(image);

            app_stage = (Stage) explorerMoveImageButton.getScene().getWindow();
            app_stage.close();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////
    
    

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

    /**
     * **************************** ALBUM TAB **********************************
     */
    
    ///////////////////////  CREATE NEW VIRTUAL ALBUM  /////////////////////////
    @FXML
    void albumsNewAlbumButtonAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLNewAlbumForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(explorerBtn1.getScene().getWindow());
        app_stage.showAndWait();
    }

    @FXML
    void createAlbumPopUpButtonAction(ActionEvent event) {
        String albumName = albumNameTextField.getText();
        String albumDescription = albumDescriptionTextField.getText();

        if (!albumName.equals("")) {
            if (virtualAlbumsController.getVirtualAlbumList().size() != 0) {
                boolean isValidName = virtualAlbumsController.isAlbumNameValid(albumName);
                if (!isValidName) {
                    //Ako ime vec postoji//
                } else {
                    VirtualAlbum album = new VirtualAlbum(albumName, albumDescription);
                    virtualAlbumsController.addVirtualAlbum(album);
                    virtualAlbumsController.addAlbumToAlbumsFlowPane(album);
                    app_stage = (Stage) createAlbumPopUpButton.getScene().getWindow();
                    app_stage.close();
                }
            } else {
                VirtualAlbum album = new VirtualAlbum(albumName, albumDescription);
                virtualAlbumsController.addVirtualAlbum(album);
                virtualAlbumsController.addAlbumToAlbumsFlowPane(album);

                app_stage = (Stage) createAlbumPopUpButton.getScene().getWindow();
                app_stage.close();
            }
        } else {
            //Ako nije uneseno ime//
        }
    }
    ///////////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////////// ALBUM BACK BUTTON /////////////////////////////
    @FXML
    void albumsBackButtonAction(ActionEvent event) {
        albumsNavigationLabel.setText("Albums");
        albumOrImageNameLabel.setText("Album name:");
        albumNameLabel.setText("");
        descriptionTempLabel.setText("Description:");
        albumsFlowPane.setVisible(true);
        imagesFlowPane.setVisible(false);
    }
    ///////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////// DELETE ALBUM /////////////////////////////////
    @FXML
    void albumsDeleteAction(ActionEvent event) {
        String albumName = albumNameLabel.getText();
        if (albumOrImageNameLabel.getText().equals("Album name:")) {
            // brisanje albuma //
            virtualAlbumsController.removeVirtualAlbum(albumName);
        } else {
            // brisanje slike iz albuma //
            String albumN = albumsNavigationLabel.getText();
            String imageN = albumNameLabel.getText();
            virtualAlbumsController.removeImageFromAlbum(albumN, imageN);
        }

    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////// OPEN ALBUM OR IMAGE BUTTON  /////////////////////////
    @FXML
    void albumsOpenAction(ActionEvent event) {
        String type = albumsNavigationLabel.getText();
        if ("Albums".equals(type)) {
            ///// Selektovan je album //////
            String albumName = albumNameLabel.getText();
            if (!"".equals(albumName)) {
                virtualAlbumsController.openAlbumOrImage(albumName, null);
            } else {
                //Ako nije selektovan ni jedan album//
            }
        }
        else{
            ////// Selektovana je slika //////
            String imageName = albumNameLabel.getText();
            if (!"".equals(imageName)) {
                String parentFolder = albumsNavigationLabel.getText();
                virtualAlbumsController.openAlbumOrImage(parentFolder, imageName);
            } else {
                //Ako nije selektovana ni jedna slika//
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////// RENAME ALBUM OR IMAGE  //////////////////////////
    @FXML
    void albumsRenameAction(ActionEvent event) throws IOException {
        type = albumsNavigationLabel.getText();
        selectedImageName = albumNameLabel.getText();
        if ("Albums".equals(type)) {
            // Selektovan je album //
            selectedAlbum = albumNameLabel.getText();
            System.out.println("Selected: " + selectedAlbum);
            if (!"".equals(selectedAlbum)) {
                Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLRenameAlbumForm.fxml"));
                Scene create_folder_scene = new Scene(home_page_parent);
                app_stage = new Stage();
                app_stage.setScene(create_folder_scene);
                app_stage.initModality(Modality.APPLICATION_MODAL);
                app_stage.initOwner(explorerBtn1.getScene().getWindow());
                app_stage.show();
            }
        }
        else{
            // Selektovana je slika //
            selectedAlbum = type;
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLRenameAlbumForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.show();
        }
    }
    
    @FXML
    void renameAlbumPopUpButtonAction(ActionEvent event) {
        String newName = renameAlbumPopUpTextField.getText();
        if ("Albums".equals(type)) {
            if (!newName.equals("")) {
                virtualAlbumsController.renameAlbumOrImage(selectedAlbum, null, newName);
                app_stage = (Stage) renameAlbumPopUpButton.getScene().getWindow();
                app_stage.close();
            } else {
                //Potrebno ponovo unijeti naziv albuma //
            }
        } else {
            if (!newName.equals("")) {
                //String selectedImageName = albumNameLabel.getText();
                virtualAlbumsController.renameAlbumOrImage(selectedAlbum, selectedImageName, newName);
                app_stage = (Stage) renameAlbumPopUpButton.getScene().getWindow();
                app_stage.close();
            } else {
                //Potrebno ponovo unijeti naziv albuma //
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //////////////////////// ALBUMS IMPORT BUTTON /////////////////////////////
    @FXML
    void albumsImportButtonAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAlbumsImportPopUpForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(explorerBtn1.getScene().getWindow());
        app_stage.showAndWait();
        
    }
    
    @FXML
    void albumsImportPopUpBrowseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose images...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        choosenFiles = fileChooser.showOpenMultipleDialog(app_stage);
        if(choosenFiles != null){
            albumsImportPopUpChoosenNumLabel.setText("You choosen " + choosenFiles.size() + " images");
        } 
        else{
            albumsImportPopUpChoosenNumLabel.setText("You didnt choose any images");
        }
    }
    
    @FXML
    void albumsImportPopUpAddToAlbumButtonAction(ActionEvent event) {
        String nameOfAlbum = (String) albumsImportPopUpChoiseBox.getValue();
        if (nameOfAlbum != null && !"".equals(nameOfAlbum)) {
            System.out.println("Choosen value: " + nameOfAlbum);
            for (File f : choosenFiles) {
                AlbumImage image = new AlbumImage(f.getName(), f);
                VirtualAlbum album = virtualAlbumsController.getAlbumForString(nameOfAlbum);
                album.addImage(image);
            }

            app_stage = (Stage) albumsImportPopUpAddToAlbumButton.getScene().getWindow();
            app_stage.close();
        }
        
    }
    ///////////////////////////////////////////////////////////////////////////
    

    
    
    /***************************** INITIALIZE  *******************************/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize");
        if (isFirstTime) {
            isFirstTime = false;
            fst = new FileSystemTree(explorerTreeView, explorerImgView, explorerPathTextField, explorerImageLabel);
            fst.start();
            virtualAlbumsController = new VirtualAlbumsController(albumsNavigationLabel, albumNameLabel, albumDescriptionLabel,
                    albumOrImageNameLabel, descriptionTempLabel, albumsFlowPane, imagesFlowPane, albumsScrollPane, imagesScrollPane);
        }
        ObservableList<String> albumNames = virtualAlbumsController.getAllAlbumsName();
        try {
            //System.out.println("add albums to choise box 1");
            if (moveImagePopUpChoiceBox != null) {
                moveImagePopUpChoiceBox.getItems().addAll(albumNames);
            }
            //System.out.println("add albums to choise box 2");
            if (albumsImportPopUpChoiseBox != null) {
                albumsImportPopUpChoiseBox.getItems().addAll(albumNames);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

}
