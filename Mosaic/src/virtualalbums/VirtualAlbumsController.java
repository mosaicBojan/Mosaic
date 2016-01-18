package virtualalbums;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VirtualAlbumsController {

    private Button lastClickedButton = null;    //remembers last album clicked

    private ArrayList<VirtualAlbum> virtualAlbumList;
    private static int numberOfAlbums = 0;
    private int nextEmpty = 0;
    private Label albumsNavigationLabel;
    private Label albumNameLabel;
    private Label albumDescriptionLabel;
    private Label albumOrImageNameLabel;
    private Label descriptionTempLabel;
    private Label albumsDateLabel;
    private Label albumsDateCreatedLabel;
    private FlowPane albumsFlowPane;
    private FlowPane imagesFlowPane;
    private ScrollPane albumsScrollPane;
    private ScrollPane imagesScrollPane;
    private HashMap<String, Label> buttonNameLabelHashMap = new HashMap<>();
    
    private ImageView albumsImageView;
    private double originalImageHeight = 0;
    private double originalImageWidth = 0;
    private double albumsStackPaneWidth = 0;
    private double albumsStackPaneHeight = 0;
    private double resizedImageHeight = 0;
    private double resizedImageWidth = 0;
    
    private Button albumsImportButton;
    private Button albumsCreateAlbumButton;
    private Button albumsDeleteButton;
    private Button albumsRenameButton;
    private Button albumsOpenButton;
    private Button albumsCopyButton;
    private Button albumsMoveButton;
    private Button albumsFullscreenButton;
    private Button albumsBackButton;

    public void setAlbumsBackButton(Button albumsBackButton) {
        this.albumsBackButton = albumsBackButton;
    }
    
    

    public void setAlbumsImportButton(Button albumsImportButton) {
        this.albumsImportButton = albumsImportButton;
    }

    public void setAlbumsCreateAlbumButton(Button albumsCreateAlbumButton) {
        this.albumsCreateAlbumButton = albumsCreateAlbumButton;
    }

    public void setAlbumsDeleteButton(Button albumsDeleteButton) {
        this.albumsDeleteButton = albumsDeleteButton;
    }

    public void setAlbumsRenameButton(Button albumsRenameButton) {
        this.albumsRenameButton = albumsRenameButton;
    }

    public void setAlbumsOpenButton(Button albumsOpenButton) {
        this.albumsOpenButton = albumsOpenButton;
    }

    public void setAlbumsCopyButton(Button albumsCopyButton) {
        this.albumsCopyButton = albumsCopyButton;
    }

    public void setAlbumsMoveButton(Button albumsMoveButton) {
        this.albumsMoveButton = albumsMoveButton;
    }

    public void setAlbumsFullscreenButton(Button albumsFullscreenButton) {
        this.albumsFullscreenButton = albumsFullscreenButton;
    }

    // KONSTRUKTOR //
    public VirtualAlbumsController(Label albumsNavigationLabel,
            Label albumNameLabel, Label albumDescriptionLabel, Label albumOrImageNameLabel, Label descriptionTempLabel,
            FlowPane albumsFlowPane, FlowPane imagesFlowPane, ScrollPane albumsScrollPane, ScrollPane imagesScrollPane, Label albumsDateCreatedLabel, Label albumsDateLabel,
            ImageView albumsImageView) {
        virtualAlbumList = new ArrayList<>();
        this.albumsNavigationLabel = albumsNavigationLabel;
        this.albumNameLabel = albumNameLabel;
        this.albumDescriptionLabel = albumDescriptionLabel;
        this.albumOrImageNameLabel = albumOrImageNameLabel;
        this.descriptionTempLabel = descriptionTempLabel;
        this.albumsFlowPane = albumsFlowPane;
        this.imagesFlowPane = imagesFlowPane;
        this.albumsScrollPane = albumsScrollPane;
        this.imagesScrollPane = imagesScrollPane;
        this.imagesScrollPane.setVisible(false);
        this.imagesFlowPane.setVisible(false);
        this.albumsDateCreatedLabel = albumsDateCreatedLabel;
        this.albumsDateLabel = albumsDateLabel;
        this.albumsImageView = albumsImageView;
    }

    ///////////////////   ADD VIRTUAL ALBUM    /////////////////////
    public void addVirtualAlbum(VirtualAlbum album) {
        virtualAlbumList.add(album);
        numberOfAlbums++;
        nextEmpty++;
    }

    public void addAlbumToAlbumsFlowPane(VirtualAlbum album) {
        Button button = new Button(album.getName());
        button.setAlignment(Pos.CENTER_LEFT);
        button.getStyleClass().add("albumNotSelected");
        button.wrapTextProperty().setValue(true);
        button.setPrefWidth(150);
        button.setPrefHeight(60);
        button.setTooltip(new Tooltip(album.getName()));
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        /////// Double click //////
                        System.out.println("Double clicked");
                        
                        /* DISABLING BUTTONS */
                        albumsImportButton.setDisable(false);
                        albumsCreateAlbumButton.setDisable(false);
                        albumsDeleteButton.setDisable(true);
                        albumsRenameButton.setDisable(true);
                        albumsOpenButton.setDisable(true);
                        albumsCopyButton.setDisable(true);
                        albumsMoveButton.setDisable(true);
                        albumsFullscreenButton.setDisable(true);
                        albumsBackButton.setDisable(false);
                        
                        /* removing album labels when you enter the album */
                        albumNameLabel.setVisible(false);
                        albumOrImageNameLabel.setVisible(false);
                        albumDescriptionLabel.setVisible(false);
                        descriptionTempLabel.setVisible(false);
                        albumsDateCreatedLabel.setVisible(false);
                        albumsDateLabel.setVisible(false);
                        
                        albumOrImageNameLabel.setText("Image name:");
                        albumNameLabel.setText("");
                        albumDescriptionLabel.setText("");
                        descriptionTempLabel.setText("");
                        albumsNavigationLabel.setText(album.getName());
                        albumsFlowPane.setVisible(false);
                        imagesFlowPane.setVisible(true);
                        albumsScrollPane.setVisible(false);
                        imagesScrollPane.setVisible(true);
                        setImagesToImagesFlowPane(album);
                    } else if (event.getClickCount() == 1) {
                        /* DISABLING BUTTONS */
                        albumsImportButton.setDisable(false);
                        albumsCreateAlbumButton.setDisable(false);
                        albumsDeleteButton.setDisable(false);
                        albumsRenameButton.setDisable(false);
                        albumsOpenButton.setDisable(false);
                        albumsCopyButton.setDisable(false);
                        albumsMoveButton.setDisable(true);
                        albumsFullscreenButton.setDisable(true);
                        
                        /* enabling album labels if they were disabled */
                        albumNameLabel.setVisible(true);
                        albumOrImageNameLabel.setVisible(true);
                        albumDescriptionLabel.setVisible(true);
                        descriptionTempLabel.setVisible(true);
                        albumsDateCreatedLabel.setVisible(true);
                        albumsDateLabel.setVisible(true);

                        //////// Single click  ////////
                        // setting single click highlight
                        lastClickedButton = button;
                        // end highlight
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
                        albumsDateLabel.setText((sdf.format(new Date(Long.parseLong(album.getCreationTimeMillis())))));
                        albumNameLabel.setText(album.getName());
                        albumDescriptionLabel.setText(album.getDescription());
                    }
                }
            }
        });
        albumsFlowPane.setPadding(new Insets(10, 5, 5, 10));
        albumsFlowPane.setVgap(10);
        albumsFlowPane.setHgap(15);
        albumsFlowPane.getChildren().add(button);
    }
    ///////////////////////////////////////////////////////////////////////////////

    //////////////////////// ADD ALL ALBUMS TO FLOW PANE  /////////////////////////
    public void addAllAlbumsToAlbumsFlowPane(){
        for(VirtualAlbum va: virtualAlbumList){
            this.addAlbumToAlbumsFlowPane(va);
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    
    
    ///////////////   SET IMAGES OF REQUIRED ALBUM TO FLOW PANE   //////////////
    public void setImagesToImagesFlowPane(VirtualAlbum album) {

        VirtualAlbum va = getAlbumForString(album.getName());
        imagesFlowPane.getChildren().clear();
        for (AlbumImage image : va.getImages()) {
            Button button = new Button();
            button.getStyleClass().add("imageNotSelected");
            button.setPrefWidth(100);
            button.setPrefHeight(130);
            button.setWrapText(true);
            button.setTooltip(new Tooltip(image.getName()));
            button.setAlignment(Pos.TOP_CENTER);
            button.setContentDisplay(ContentDisplay.TOP);
            button.setText(image.getName());

            Image iconImage = new Image(new File(image.getPath().getPath()).toURI().toString(), 72, 72, true, true, true);
            ImageView iv = new ImageView(iconImage);
            button.setGraphic(iv);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            System.out.println("Double clicked");
                            try {
                                Desktop.getDesktop().open(image.getPath());
                                /* DISABLING BUTTONS */
                                albumsImportButton.setDisable(false);
                                albumsCreateAlbumButton.setDisable(false);
                                albumsDeleteButton.setDisable(true);
                                albumsRenameButton.setDisable(true);
                                albumsOpenButton.setDisable(true);
                                albumsCopyButton.setDisable(true);
                                albumsMoveButton.setDisable(true);
                                albumsFullscreenButton.setDisable(true);
                                albumsBackButton.setDisable(false);
                                imagesFlowPane.requestFocus();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        } else if (event.getClickCount() == 1) {
                             /* DISABLING BUTTONS */
                            albumsImportButton.setDisable(false);
                            albumsCreateAlbumButton.setDisable(false);
                            albumsDeleteButton.setDisable(false);
                            albumsRenameButton.setDisable(false);
                            albumsOpenButton.setDisable(false);
                            albumsCopyButton.setDisable(false);
                            albumsMoveButton.setDisable(false);
                            albumsFullscreenButton.setDisable(false);
                            
                            /* disabling album description labels */
                            /* enabling album labels if they were disabled */
                            albumNameLabel.setVisible(false);
                            albumOrImageNameLabel.setVisible(false);
                            albumDescriptionLabel.setVisible(false);
                            descriptionTempLabel.setVisible(false);
                            albumsDateCreatedLabel.setVisible(false);
                            albumsDateLabel.setVisible(false);
                            
                            /* PREVIEWING SELECTED IMAGE */
                            albumsImageView.setVisible(true);

                            albumsImageView.setCache(true);
                            albumsImageView.setCacheHint(CacheHint.SPEED);
                            
                            albumNameLabel.setText(image.getName());

                            Image albumImage = new Image(new File(image.getPath().getPath()).toURI().toString());
                            if (albumImage.getHeight() > 1700 || albumImage.getWidth() > 1700) {
                                albumImage = new Image(new File(image.getPath().getPath()).toURI().toString(), 1700, 1700, true, true);
                            }
                            originalImageHeight = albumImage.getHeight();
                            originalImageWidth = albumImage.getWidth();

                            albumsStackPaneWidth = ((StackPane) (albumsImageView.getParent())).getWidth() - 20;
                            albumsStackPaneHeight = ((StackPane) (albumsImageView.getParent())).getHeight() - 20;

                            //((StackPane)(imageView.getParent())).setStyle("-fx-background-color: red;");
                            if (originalImageWidth / albumsStackPaneWidth > originalImageHeight / albumsStackPaneHeight) {
                                if (albumImage.getWidth() < albumsStackPaneWidth) {
                                    //System.out.println("FIT PO SIRINI SLIKE: " + image.getWidth());
                                    albumsImageView.setFitWidth(albumImage.getWidth());
                                } else {
                                    //System.out.println("FIT PO SIRINI PANELA: " + imageStackPaneWidth);
                                    albumsImageView.setFitWidth(albumsStackPaneWidth);
                                }
                            } else if (albumImage.getHeight() < albumsStackPaneHeight) {
                                //System.out.println("FIT PO DUZINI SLIKE: " + image.getHeight());
                                albumsImageView.setFitHeight(albumImage.getHeight());
                            } else {
                                //System.out.println("FIT PO DUZINI PANELA: " + imageStackPaneHeight);
                                albumsImageView.setFitHeight(albumsStackPaneHeight);
                            }
                            albumsImageView.setImage(albumImage);
                            
                            
                            // setting single click highlight
                            //lastClickedButton = button;
                            // end highlight
                            //albumNameLabel.setText(image.getName());
                        }
                    }
                    ((AnchorPane) (albumsImageView.getParent().getParent())).widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldStackPaneWidth, Number newStackPaneWidth) {
                            albumsStackPaneWidth = newStackPaneWidth.doubleValue();
                            if (originalImageWidth >= newStackPaneWidth.doubleValue() & resizedImageHeight <= albumsStackPaneHeight) {
                                albumsImageView.setFitWidth(newStackPaneWidth.doubleValue() - 40);
                                resizedImageWidth = newStackPaneWidth.doubleValue() - 20;
                                resizedImageHeight = 0 / (originalImageWidth / (newStackPaneWidth.doubleValue() - 20));
                            }
                        }
                    });
                    ((AnchorPane) (albumsImageView.getParent().getParent())).heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldStackPaneHeight, Number newStackPaneHeight) {
                            albumsStackPaneHeight = newStackPaneHeight.doubleValue();
                            if (originalImageHeight >= newStackPaneHeight.doubleValue() & resizedImageWidth <= albumsStackPaneWidth) {
                                albumsImageView.setFitHeight(newStackPaneHeight.doubleValue() - 40);
                                resizedImageHeight = newStackPaneHeight.doubleValue() - 20;
                                resizedImageWidth = 0 / (originalImageHeight / (newStackPaneHeight.doubleValue() - 20));
                            }
                        }
                    });
                }
            });
            imagesFlowPane.setPadding(new Insets(10, 5, 5, 10));
            imagesFlowPane.setVgap(20);
            imagesFlowPane.setHgap(20);
            imagesFlowPane.getChildren().add(button);
        }
        imagesFlowPane.requestFocus();      //first image selected patch
    }
    ///////////////////////////////////////////////////////////////////////////////

    /////////////////////////  REMOVE VIRTUAL ALBUM  //////////////////////////
    public void removeVirtualAlbum(String name) {
        System.out.println("Remove album: " + name);
        VirtualAlbum va = getAlbumForString(name);
        virtualAlbumList.remove(va);
        albumsFlowPane.getChildren().clear();
        for (VirtualAlbum v : virtualAlbumList) {
            addAlbumToAlbumsFlowPane(v);
        }
    }
    ///////////////////////////////////////////////////////////////////////////

    /////////////////////////  REMOVE IMAGE FROM ALBUM  /////////////////////////
    public void removeImageFromAlbum(String album, String image) {
        VirtualAlbum virtualAlbum = getAlbumForString(album);
        System.out.println("Before");
        for(AlbumImage al: virtualAlbum.getImages()){
            System.out.println("*****" + al.getName());
        }
        virtualAlbum.deleteImage(image);
        /*for (String name : buttonNameLabelHashMap.keySet()) {
            buttonNameLabelHashMap.remove(name);
        }*/
        imagesFlowPane.getChildren().clear();
        setImagesToImagesFlowPane(virtualAlbum);
        System.out.println("After");
        for(AlbumImage al: virtualAlbum.getImages()){
            System.out.println("*****" + al.getName());
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    /////////////////////////  OPEN ALBUM OR IMAGE  ////////////////////////////
    public void openAlbumOrImage(String albumName, String imageName) {
        System.out.println("albumName: " + albumName);
        System.out.println("imageName: " + imageName);
        if (imageName == null) {
            // Selektovan je album //
            albumOrImageNameLabel.setText("Image name:");
            albumNameLabel.setText("");
            albumDescriptionLabel.setText("");
            descriptionTempLabel.setText("");
            albumsNavigationLabel.setText(albumName);
            albumsFlowPane.setVisible(false);
            imagesFlowPane.setVisible(true);
            albumsScrollPane.setVisible(false);
            imagesScrollPane.setVisible(true);
            VirtualAlbum va = getAlbumForString(albumName);
            setImagesToImagesFlowPane(va);
        } else {
            // Selektovana je slika //
            VirtualAlbum va = getAlbumForString(albumName);
            AlbumImage image = va.getImageFromAlbumForString(imageName);
            try {
                Desktop.getDesktop().open(new File(imageName));
            } catch (IOException ex) {
                Logger.getLogger(VirtualAlbumsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////// RENAME ALBUM OR IMAGE ///////////////////////////
    
    public boolean renameAlbumOrImage(String oldName, String imageOldName, String newName) {
        if (imageOldName == null) {
            // rename album //
            for(VirtualAlbum album: virtualAlbumList){
                if(album.getName().equals(newName)){
                    return false;
                }
            }
            VirtualAlbum va = getAlbumForString(oldName);
            va.setName(newName);
            albumNameLabel.setText(newName);
            lastClickedButton.setText(newName);
            return true;
        } else {
            // rename image //
            VirtualAlbum va = getAlbumForString(oldName);
            AlbumImage im = va.getImageFromAlbumForString(imageOldName);
            
            for(AlbumImage img: va.getImages()){
                System.out.println(img.getName() + " --- " + newName + im.getExtensionOfImage() );
                System.out.println("img.getName().equals(newName) " + img.getName().equals(newName + im.getExtensionOfImage()));
                if(img.getName().equals(newName + im.getExtensionOfImage())){
                    return false;
                }
            }
            im.setName(newName + im.getExtensionOfImage());
            System.out.println("DOSAOOOOO");
            albumNameLabel.setText(newName);
            setImagesToImagesFlowPane(va);
            return true;
        }
    }
   
    ////////////////////////////////////////////////////////////////////////////

    ///////////////////////  IS ALBUM NAME VALID  //////////////////////////////
    public boolean isAlbumNameValid(String name) {
        boolean isOK = true;
        for (VirtualAlbum va : virtualAlbumList) {
            if (va.getName().equals(name)) {
                isOK = false;
            }
        }
        return isOK;
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////  GET LIST OF ALBUMS NAME (STRING)  /////////////////////
    public ObservableList<String> getAllAlbumsName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (VirtualAlbum va : virtualAlbumList) {
            list.add(va.getName());
        }

        return list;
    }
    ////////////////////////////////////////////////////////////////////////////

    /////////////// GET REQUIRED ALBUM FROM VIRTUAL ALBUM LIST /////////
    public VirtualAlbum getAlbumForString(String name) {
        VirtualAlbum album = null;
        for (VirtualAlbum va : virtualAlbumList) {
            System.out.println(va.getName() + " - " + name);
            if (va.getName().equals(name)) {
                album = va;
                break;
            }
        }
        return album;
    }
    ///////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////  SERIALIZE ALL ALBUMS  /////////////////////////////
    public void serializeAllAlbums(){
        File folder = new File("VirtualAlbumsSerialize");
        if (folder.exists()) {
            File[] files = folder.listFiles();
            for (File ff : files) {
                ff.delete();
            }
        }
        System.out.println("ALBUMS SERIALIZATION");
        for(VirtualAlbum va: virtualAlbumList){
            va.serializeVirtualAlbum();
        }
    }
    //////////////////////////////////////////////////////////////////////////
    
    
    //////////////////////// DESERIALIZE ALL ALBUMS  ///////////////////////////
    public void deserializeAllAlbums(){
        File folder = new File("VirtualAlbumsSerialize");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                VirtualAlbum album = null;
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    album = (VirtualAlbum) ois.readObject();
                    ois.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                virtualAlbumList.add(album);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    

  
    
    //////////////////    ALL GETERS    //////////////////
    public ArrayList<VirtualAlbum> getVirtualAlbumList() {
        return virtualAlbumList;
    }

    public static int getNumberOfAlbums() {
        return numberOfAlbums;
    }
}
