package virtualalbums;

import java.io.File;
import java.io.Serializable;

public class AlbumImage implements Serializable{
    private String name;
    private File path;
    private int timesOpened = 0;

    public AlbumImage(String name, File path) {
        this.name = name;
        this.path = path;
    }
    
    public AlbumImage(AlbumImage album){
        this.name = album.name;
        this.path = album.getPath();
    }

    public String getName() {
        return name;
    }

    public File getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public int getTimesOpened() {
        return timesOpened;
    }
    
    public void incrementTimesOpened(){
        ++this.timesOpened;
    }
    
    public String getNameWithoutExtension(){
        String retStr = "";
        int index = name.lastIndexOf(".");
        retStr = name.substring(0, index);
        
        return retStr;
    }
    
    public String getExtensionOfImage(){
        String retStr = "";
        int index = name.lastIndexOf(".");
        retStr = name.substring(index);
        
        return retStr;
    }
}
