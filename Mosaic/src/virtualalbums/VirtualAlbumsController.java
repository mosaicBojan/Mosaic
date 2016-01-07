package virtualalbums;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
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
    private FlowPane albumsFlowPane;
    private FlowPane imagesFlowPane;
    private ScrollPane albumsScrollPane;
    private ScrollPane imagesScrollPane;
    private HashMap<String, Label> buttonNameLabelHashMap = new HashMap<>();

    // KONSTRUKTOR //
    public VirtualAlbumsController(Label albumsNavigationLabel,
            Label albumNameLabel, Label albumDescriptionLabel, Label albumOrImageNameLabel, Label descriptionTempLabel,
            FlowPane albumsFlowPane, FlowPane imagesFlowPane, ScrollPane albumsScrollPane, ScrollPane imagesScrollPane) {
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
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        /////// Double click //////
                        System.out.println("Double clicked");
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
                        //////// Single click  ////////
                        // setting single click highlight
                        lastClickedButton = button;
                        // end highlight

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
    
    
    ///////////////   SET IMAGES OF REQUIRED ALBUM TO FLOW PANE   //////////////
    public void setImagesToImagesFlowPane(VirtualAlbum album) {

        VirtualAlbum va = getAlbumForString(album.getName());
        imagesFlowPane.getChildren().clear();
        //int numOfImages = va.getImages().size();
        //int tempCount = 1;
        for (AlbumImage image : va.getImages()) {
            Button button = new Button();
            button.getStyleClass().add("imageNotSelected");
            /*Label buttonNameLabel = new Label(image.getName());
            buttonNameLabel.setWrapText(true);
            buttonNameLabel.setMaxWidth(100);
            buttonNameLabel.setMaxHeight(50);*/
            button.setPrefWidth(100);
            button.setPrefHeight(125);
            button.setContentDisplay(ContentDisplay.TOP);
            button.setText(image.getName());
            /*VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(button);
            vBox.getChildren().add(buttonNameLabel);*/
            
            Image iconImage = new Image(new File(image.getPath().getPath()).toURI().toString(), 72, 72, true, true, true);
            ImageView iv = new ImageView(iconImage);
            //iv.setFitHeight(72);
            //iv.setFitWidth(72);
            button.setGraphic(iv);
            //buttonNameLabelHashMap.put(image.getName(), buttonNameLabel);
            //button.setGraphic(new ImageView(new Image("icons/move.png")));
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            System.out.println("Double clicked");
                            try {
                                Desktop.getDesktop().open(image.getPath());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        } else if (event.getClickCount() == 1) {
                            // setting single click highlight
                            lastClickedButton = button;
                            // end highlight
                            albumNameLabel.setText(image.getName());
                        }
                    }
                }

            });
            imagesFlowPane.setPadding(new Insets(5, 5, 5, 5));
            imagesFlowPane.setVgap(20);
            imagesFlowPane.setHgap(20);
            imagesFlowPane.getChildren().add(button);
        }
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
        virtualAlbum.deleteImage(image);
        /*for (String name : buttonNameLabelHashMap.keySet()) {
            buttonNameLabelHashMap.remove(name);
        }*/
        imagesFlowPane.getChildren().clear();
        setImagesToImagesFlowPane(virtualAlbum);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////////  OPEN ALBUM OR IMAGE  ////////////////////////////
    public void openAlbumOrImage(String albumName, String imageName) {
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
                Desktop.getDesktop().open(image.getPath());
            } catch (IOException ex) {
                Logger.getLogger(VirtualAlbumsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////// RENAME ALBUM OR IMAGE ///////////////////////////
    public void renameAlbumOrImage(String oldName, String imageOldName, String newName) {
        if (imageOldName == null) {
            // rename album //
            VirtualAlbum va = getAlbumForString(oldName);
            va.setName(newName);
            //ObservableList<Node> list = albumsFlowPane.getChildren();
            albumNameLabel.setText(newName);
            lastClickedButton.setText(newName);
        }
        else{
            // rename image //
            VirtualAlbum va = getAlbumForString(oldName);
            AlbumImage im = va.getImageFromAlbumForString(imageOldName);
            im.setName(newName);
            //ObservableList<Node> list = albumsFlowPane.getChildren();
            albumNameLabel.setText(newName);
            lastClickedButton.setText(newName);
            //lastClickedButton.setText(newName);
            //((Parent)lastClickedButton.getParent()).getChildren();
            //Label selLabel = buttonNameLabelHashMap.get(imageOldName);
            /*for (String name : buttonNameLabelHashMap.keySet()) {
                if(name.equals(imageOldName)){
                    name = newName;
                    break;
                }
            }*/
            //System.out.println("LABEL: " + selLabel);
            //selLabel.setText(newName);
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
    
    
    //////////////////    ALL GETERS    //////////////////
    public ArrayList<VirtualAlbum> getVirtualAlbumList() {
        return virtualAlbumList;
    }

    public static int getNumberOfAlbums() {
        return numberOfAlbums;
    }
}
