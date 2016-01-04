package virtualalbums;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class VirtualAlbumsController {
   private ArrayList<VirtualAlbum> virtualAlbumList;
   private GridPane albumsGridPane;
   private static int numberOfAlbums = 0;
   private int nextEmpty = 0;

    public VirtualAlbumsController(GridPane albumsGridPane) {
        virtualAlbumList = new ArrayList<>();
        this.albumsGridPane = albumsGridPane;
    }
   
   public void addVirtualAlbum(VirtualAlbum album){
       virtualAlbumList.add(album);
       numberOfAlbums++;
       nextEmpty++;
   }
   
   public void removeVirtualAlbum(String name){
       for(VirtualAlbum va: virtualAlbumList){
           if(va.getName().equals(name)){
               virtualAlbumList.remove(va);
               break;
           }
       }
   }

    public ArrayList<VirtualAlbum> getVirtualAlbumList() {
        return virtualAlbumList;
    }
   
    public void addAlbumToGridPane(VirtualAlbum album){
        Button button = new Button(album.getName());
        //albumsGridPane.getChildren().add(button);
        int x = 0;
        int y = 0;
        if(nextEmpty > 0 & nextEmpty <= 6){
            y = 0;
            x = nextEmpty - 1;
        }
        else if(nextEmpty > 6 & nextEmpty <= 12){
            y = 1;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        }
        else if(nextEmpty > 12 & nextEmpty <= 18){
            y = 2;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        }
        else if(nextEmpty > 18 & nextEmpty <= 24){
            y = 3;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        }
        else{
            y = 4;
            if (nextEmpty == 12) {
                x = 5;
            } else {
                x = nextEmpty % 6 - 1;
            }
        }
        albumsGridPane.add(button, x, y);
    }

    public boolean isAlbumNameValid(String name){
        boolean isOK = true;
        for(VirtualAlbum va: virtualAlbumList){
            if(va.getName().equals(name)){
                isOK = false;
            }
        }
        return isOK;
    }
    
    public static int getNumberOfAlbums() {
        return numberOfAlbums;
    }
    
    public ObservableList<String> getAllAlbumsName(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(VirtualAlbum va: virtualAlbumList){
            list.add(va.getName());
        }
        
        return list;
    }
   
}
