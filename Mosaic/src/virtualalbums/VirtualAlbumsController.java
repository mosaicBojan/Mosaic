package virtualalbums;

import java.awt.Desktop;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class VirtualAlbumsController {

    private ArrayList<VirtualAlbum> virtualAlbumList;
    private GridPane albumsGridPane;
    private GridPane albumImagesGridPane;
    private static int numberOfAlbums = 0;
    private int nextEmpty = 0;

    public VirtualAlbumsController(GridPane albumsGridPane, GridPane albumImagesGridPane) {
        virtualAlbumList = new ArrayList<>();
        this.albumsGridPane = albumsGridPane;
        this.albumImagesGridPane = albumImagesGridPane;
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
                break;
            }
        }
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
        int numOfImages = va.getImages().size();
        int tempCount = 1;
        for (AlbumImage image : va.getImages()) {
            Button button = new Button(image.getName());
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
        }
    }

    public void addAlbumToGridPane(VirtualAlbum album) {
        Button button = new Button(album.getName());
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        System.out.println("Double clicked");
                        albumsGridPane.setDisable(true);
                        albumImagesGridPane.setDisable(false);
                        albumsGridPane.setVisible(false);
                        albumImagesGridPane.setVisible(true);
                        setImagesToGridPane(album);
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
