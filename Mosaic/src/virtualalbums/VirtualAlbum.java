package virtualalbums;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class VirtualAlbum implements Serializable{
    private String name;
    private String description;
    private LocalDate creationTime;
    private ArrayList<AlbumImage> images;

    public VirtualAlbum(String name) {
        images = new ArrayList<AlbumImage>();
        this.name = name;
    }

    public VirtualAlbum(String name, String description) {
        images = new ArrayList<AlbumImage>();
        this.name = name;
        this.description = description;
    }

    //////////////  RETURN REQUIRED IMAGE FROM ALBUM  ////////////////
    public AlbumImage getImageFromAlbumForString(String imageName){
        AlbumImage image = null;
        for(AlbumImage i: images){
            if(i.getName().equals(imageName)){
                image = i;
                break;
            }
        }
        
        return image;
    }
    
    public void serializeVirtualAlbum() {
        System.out.println("SERIALIZE VIRTUAL ALBUM");
        File folder = new File("VirtualAlbumsSerialize");
        if (folder.exists() == false) {
            folder.mkdir();
        }
        /*else{
            File[] files = folder.listFiles();
            for(File ff: files){
                ff.delete();
            }
        }*/

        File album = new File("VirtualAlbumsSerialize" + File.separator + name +  ".ser");
        try {
            album.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(album));
            oos.writeObject(this);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public ArrayList<AlbumImage> getImages() {
        return images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }

    public void setImages(ArrayList<AlbumImage> images) {
        this.images = images;
    }
   
    ////////// ADD IMAGE TO this ALBUM ////////
    public void addImage(AlbumImage image){
        images.add(image);
    }
    
    ///////// DELETE IMAGE FROM this ALBUM ////////
    public void deleteImage(String name){
        for(AlbumImage im: images){
            if(im.getName().equals(name)){
                images.remove(im);
                break;
            }
        }
        
    }
}
