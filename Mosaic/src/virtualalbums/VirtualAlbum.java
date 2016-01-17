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
    private String creationTimeInMillis;
    private ArrayList<AlbumImage> images;

    public VirtualAlbum(String name) {
        images = new ArrayList<AlbumImage>();
        this.name = name;
        this.creationTimeInMillis = Long.toString(System.currentTimeMillis());
    }

    public VirtualAlbum(String name, String description) {
        images = new ArrayList<AlbumImage>();
        this.name = name;
        this.description = description;
        this.creationTimeInMillis = Long.toString(System.currentTimeMillis());
    }

    public VirtualAlbum(VirtualAlbum album){
        this.name = album.getName();
        this.description = album.getDescription();
        this.creationTime = album.getCreationTime();
        this.creationTimeInMillis = album.getCreationTimeInMillis();
        this.images = new ArrayList<AlbumImage>();
        for ( AlbumImage img : album.images ){
            this.images.add(new AlbumImage(img));
        }
       
    }
    
    public boolean isImageNameValid(String imageName){
        boolean retVal = true;
        for(AlbumImage im: images){
            if(im.getName().equals(imageName)){
                retVal = false;
                break;
            }
        }
        return retVal;
    }
    //////////////  RETURN REQUIRED IMAGE FROM ALBUM  ////////////////
    public AlbumImage getImageFromAlbumForString(String imageName){
        System.out.println("FIND: " + imageName);
        AlbumImage image = null;
        for(AlbumImage i: images){
            System.out.println("TEMP: " + i.getName());
            if(i.getName().equals(imageName)){
                image = i;
                break;
            }
        }
        
        return image;
    }
    
    public void serializeVirtualAlbum() {
        File folder = new File("VirtualAlbumsSerialize");
        if (folder.exists() == false) {
            folder.mkdir();
        }
        

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

    public String getCreationTimeInMillis() {
        return creationTimeInMillis;
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
