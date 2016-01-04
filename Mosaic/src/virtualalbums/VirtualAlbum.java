package virtualalbums;

import java.time.LocalDate;
import java.util.ArrayList;

public class VirtualAlbum {
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
    
    public void addImage(AlbumImage image){
        images.add(image);
    }
    
    public void deleteImage(String name){
        for(AlbumImage im: images){
            if(im.getName().equals(name)){
                images.remove(im);
                break;
            }
        }
    }
}
