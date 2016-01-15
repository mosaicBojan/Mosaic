package screenshots;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Asus
 */
public class ScreenshotMessageController {

    private ObservableList<ScreenshotMessage> screenshotMessageList = FXCollections.observableArrayList();

    public ScreenshotMessageController() {
        System.out.println("ScreenshotMessageController created!");
    }

    public void addScreenshotMessage(ScreenshotMessage message) {
        screenshotMessageList.add(message);
        System.out.println("Currently Screenshots: ");
        System.out.println("********************************************************");
        for(ScreenshotMessage s: screenshotMessageList){
            System.out.println("   " + s.getPath());
        }
        System.out.println("********************************************************");
    }
    
    public void removeScreenshotMessage(ScreenshotMessage message){
        screenshotMessageList.remove(message);
    }

    public void deserializeScreenshotMessages() {
        File folder = new File("ScreenshotMessagesSerialization");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                ScreenshotMessage msg = null;
                //System.out.println("***" + f.getPath());
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    msg = (ScreenshotMessage) ois.readObject();
                    ois.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                screenshotMessageList.add(msg);
            }
        }
    }

    public void serializeScreenshotMessageList() {
        File folder = new File("ScreenshotMessagesSerialization");
        if (folder.exists() == true) {
            File[] files = folder.listFiles();
            for(File f: files){
                f.delete();
            }
        }
        for (ScreenshotMessage msg : screenshotMessageList) {
            //System.out.println("Serialize message: " + msg.getPath());
            msg.serializeScreenshotMessage();
        }
    }

    public ObservableList<ScreenshotMessage> getScreenshotMessageList() {
        return screenshotMessageList;
    }

}
