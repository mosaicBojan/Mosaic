package virtualalbums;

import com.sun.media.jfxmedia.control.VideoDataBuffer;
import java.awt.Desktop;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class VirtualAlbumsController {

    private Button lastClickedButton = null;    //remembers last album clicked
    
    private ArrayList<VirtualAlbum> virtualAlbumList;
    private GridPane albumsGridPane;
    private GridPane albumImagesGridPane;
    private static int numberOfAlbums = 0;
    private int nextEmpty = 0;
    private Label albumsNavigationLabel;
    private Label albumNameLabel;
    private Label albumDescriptionLabel;
    private Label albumOrImageNameLabel;
    private Label descriptionTempLabel;

    public VirtualAlbumsController(GridPane albumsGridPane, GridPane albumImagesGridPane, Label albumsNavigationLabel,
            Label albumNameLabel, Label albumDescriptionLabel, Label albumOrImageNameLabel, Label descriptionTempLabel) {
        virtualAlbumList = new ArrayList<>();
        this.albumsGridPane = albumsGridPane;
        this.albumImagesGridPane = albumImagesGridPane;
        this.albumsNavigationLabel = albumsNavigationLabel;
        this.albumNameLabel = albumNameLabel;
        this.albumDescriptionLabel = albumDescriptionLabel;
        this.albumOrImageNameLabel = albumOrImageNameLabel;
        this.descriptionTempLabel = descriptionTempLabel;
        albumImagesGridPane.setDisable(true);
        albumImagesGridPane.setVisible(false);
    }

    public void addVirtualAlbum(VirtualAlbum album) {
        virtualAlbumList.add(album);
        numberOfAlbums++;
        nextEmpty++;
    }

    public void removeVirtualAlbum(String name) {
        for (VirtualAlbum va : virtualAlbumList) {
            if (va.getName().equals(name)) {
                virtualAlbumList.remove(va);
                
                removeGridElement(va.getName());
                break;
            }
        }
    }

    public void removeGridElement(String name) {
        ObservableList<Node> list = albumsGridPane.getChildren();
        albumNameLabel.setText("");
        for (Node n : list) {
            int firstIndex = n.toString().indexOf("'");
            int lastIndex = n.toString().lastIndexOf("'");
            String subName = n.toString().substring(firstIndex + 1, lastIndex);
            if (subName.equals(name)) {
                this.arrangeAlbums();
                break;
            }
        }
    }

    public void arrangeAlbums() {
        albumsGridPane.getChildren().clear();
        addAllAlbumsToGridPane();
    }
    
    public void removeImageFromAlbum(String album, String image){
        VirtualAlbum virtualAlbum = getAlbumByString(album);
        printAlbumInfos(virtualAlbum);
        virtualAlbum.deleteImage(image);
        removeImageGridElement(image);
    }
    
    public void printAlbumInfos(VirtualAlbum album){
        for(AlbumImage image: album.getImages()){
            System.out.println("***" + image.getName());
        }
    }

    public void removeImageGridElement(String name) {
        ObservableList<Node> list = albumImagesGridPane.getChildren();
        albumNameLabel.setText("");
        for (Node n : list) {
            int firstIndex = n.toString().indexOf("'");
            int lastIndex = n.toString().lastIndexOf("'");
            String subName = n.toString().substring(firstIndex + 1, lastIndex);
            if (subName.equals(name)) {
                this.arrangeImages();
                break;
            }
        }
    }
    
    public void arrangeImages(){
        albumImagesGridPane.getChildren().clear();
        VirtualAlbum album = getAlbumByString(albumsNavigationLabel.getText());
        addAllImagesToGridPane(album);
    }

    
    public void addAllAlbumsToGridPane() {
        int nextEmpty = 1;
        for (VirtualAlbum va : virtualAlbumList) {
            Button button = new Button(va.getName());
            button.setStyle("-fx-border-width: 0; -fx-graphic: url('icons/album.png');");
            //button.getStyleClass().add("albumSelected");
            button.wrapTextProperty().setValue(true);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    
                    // setting single click highlight
                    if ( lastClickedButton != null ){
                        lastClickedButton.getStyleClass().remove("albumSelected");
                        lastClickedButton.getStyleClass().add("albumNotSelected");
                    }
                    button.getStyleClass().remove("albumNotSelected");
                    button.getStyleClass().add("albumSelected");
                    lastClickedButton = button;
                    // end highlight
                    
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            System.out.println("Double clicked");
                            albumOrImageNameLabel.setText("Image name:");
                            albumNameLabel.setText("");
                            albumDescriptionLabel.setText("");
                            descriptionTempLabel.setText("");
                            albumsNavigationLabel.setText(va.getName());
                            albumsGridPane.setDisable(true);
                            albumImagesGridPane.setDisable(false);
                            albumsGridPane.setVisible(false);
                            albumImagesGridPane.setVisible(true);
                            setImagesToGridPane(va);
                        } else if (event.getClickCount() == 1) {
                            albumNameLabel.setText(va.getName());
                            albumDescriptionLabel.setText(va.getDescription());
                        }
                    }
                }

            });

            int x = 0;
            int y = 0;
            if (nextEmpty > 0 & nextEmpty <= 6) {
                y = 0;
                x = nextEmpty - 1;
            } else if (nextEmpty > 6 & nextEmpty <= 12) {
                y = 1;
                if (nextEmpty == 12) {
                    x = 5;
                } else {
                    x = nextEmpty % 6 - 1;
                }
            } else if (nextEmpty > 12 & nextEmpty <= 18) {
                y = 2;
                if (nextEmpty == 12) {
                    x = 5;
                } else {
                    x = nextEmpty % 6 - 1;
                }
            } else if (nextEmpty > 18 & nextEmpty <= 24) {
                y = 3;
                if (nextEmpty == 12) {
                    x = 5;
                } else {
                    x = nextEmpty % 6 - 1;
                }
            } else {
                y = 4;
                if (nextEmpty == 12) {
                    x = 5;
                } else {
                    x = nextEmpty % 6 - 1;
                }
            }
            albumsGridPane.add(button, x, y);
            nextEmpty++;
        }
    }
    
    public void addAllImagesToGridPane(VirtualAlbum album) {
        albumImagesGridPane.getChildren().clear();
        int numOfImages = album.getImages().size();
        int tempCount = 1;
        for (AlbumImage image : album.getImages()) {
            Button button = new Button(image.getName());
            //button.setStyle("-fx-border-width: 0; -fx-graphic: url('icons/album.png');");
            //button.wrapTextProperty().setValue(true);
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
                            albumNameLabel.setText(image.getName());
                        }
                    }
                }

            });

            int x = 0;
            int y = 0;
            if (tempCount > 0 & tempCount <= 6) {
                y = 0;
                x = tempCount - 1;
            } else if (tempCount > 6 & tempCount <= 12) {
                y = 1;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            } else if (tempCount > 12 & tempCount <= 18) {
                y = 2;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            } else if (tempCount > 18 & tempCount <= 24) {
                y = 3;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            } else {
                y = 4;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            }
            albumImagesGridPane.add(button, x, y);
            tempCount++;
        }
    }
    
    
    public VirtualAlbum getAlbumByString(String name){
        VirtualAlbum album = null;
        for(VirtualAlbum va: virtualAlbumList){
            if(va.getName().equals(name)){
                album = va;
                break;
            }
        }
        return album;
    }
    

    public ArrayList<VirtualAlbum> getVirtualAlbumList() {
        return virtualAlbumList;
    }

    public void setImagesToGridPane(VirtualAlbum album) {
        VirtualAlbum va = null;
        for (VirtualAlbum temp : virtualAlbumList) {
            if (temp.getName().equals(album.getName())) {
                va = temp;
                break;
            }
        }
        albumImagesGridPane.getChildren().clear();
        int numOfImages = va.getImages().size();
        int tempCount = 1;
        for (AlbumImage image : va.getImages()) {
            Button button = new Button(image.getName());
            //button.setStyle("-fx-border-width: 0; -fx-graphic: url('icons/album.png');");
            //button.wrapTextProperty().setValue(true);
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
                            albumNameLabel.setText(image.getName());
                        }
                    }
                }

            });

            int x = 0;
            int y = 0;
            if (tempCount > 0 & tempCount <= 6) {
                y = 0;
                x = tempCount - 1;
            } else if (tempCount > 6 & tempCount <= 12) {
                y = 1;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            } else if (tempCount > 12 & tempCount <= 18) {
                y = 2;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            } else if (tempCount > 18 & tempCount <= 24) {
                y = 3;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            } else {
                y = 4;
                if (tempCount == 12) {
                    x = 5;
                } else {
                    x = tempCount % 6 - 1;
                }
            }
            albumImagesGridPane.add(button, x, y);
            tempCount++;
        }
    }

    public void addAlbumToGridPane(VirtualAlbum album) {
        Button button = new Button(album.getName());
        //button.setGraphic(new ImageView(new Image("icons/album.png")));
        //button.setStyle("-fx-border-width: 0; -fx-graphic: url('icons/album.png');");
        button.getStyleClass().add("albumNotSelected");
        button.wrapTextProperty().setValue(true);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            
                // setting single click highlight
                if ( lastClickedButton != null ){
                    lastClickedButton.getStyleClass().remove("albumSelected");
                    lastClickedButton.getStyleClass().add("albumNotSelected");
                }
                button.getStyleClass().remove("albumNotSelected");
                button.getStyleClass().add("albumSelected");
                lastClickedButton = button;
                // end highlight
                
                
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        System.out.println("Double clicked");
                        albumOrImageNameLabel.setText("Image name:");
                        albumNameLabel.setText("");
                        albumDescriptionLabel.setText("");
                        descriptionTempLabel.setText("");
                        albumsNavigationLabel.setText(album.getName());
                        albumsGridPane.setDisable(true);
                        albumImagesGridPane.setDisable(false);
                        albumsGridPane.setVisible(false);
                        albumImagesGridPane.setVisible(true);
                        setImagesToGridPane(album);
                    } else if (event.getClickCount() == 1) {
                        albumNameLabel.setText(album.getName());
                        albumDescriptionLabel.setText(album.getDescription());
                    }
                }
            }

        });

        int x = 0;
        int y = 0;
        if (nextEmpty > 0 & nextEmpty <= 6) {
            y = 0;
            x = nextEmpty - 1;
        } else if (nextEmpty > 6 & nextEmpty <= 12) {
            y = 1;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        } else if (nextEmpty > 12 & nextEmpty <= 18) {
            y = 2;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        } else if (nextEmpty > 18 & nextEmpty <= 24) {
            y = 3;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        } else {
            y = 4;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        }
        albumsGridPane.add(button, x, y);
    }

    public boolean isAlbumNameValid(String name) {
        boolean isOK = true;
        for (VirtualAlbum va : virtualAlbumList) {
            if (va.getName().equals(name)) {
                isOK = false;
            }
        }
        return isOK;
    }

    public static int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public ObservableList<String> getAllAlbumsName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (VirtualAlbum va : virtualAlbumList) {
            list.add(va.getName());
        }

        return list;
    }

}
