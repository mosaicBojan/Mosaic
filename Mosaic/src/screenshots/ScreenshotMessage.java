package screenshots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Asus
 */
public class ScreenshotMessage implements Serializable, Comparable<ScreenshotMessage> {

    private String sender;
    private String receiver;
    private File path;
    private LocalDate sentTime;
    private String sentTimeString;
    private int isAccepted = 0;     //0: jos se ne zna;  1: prihvacena; -1: nije prihvacena
    private boolean isISentThisMessage;
    private boolean isUserLogout = false;
    

    public ScreenshotMessage() {
    }

    public ScreenshotMessage(File path) {
        this.path = path;
    }

    public ScreenshotMessage(String sender, String receiver, File path) {
        this.sender = sender;
        this.receiver = receiver;
        this.path = path;
    }

    
    
    public boolean isIsISentThisMessage() {
        return isISentThisMessage;
    }

    public boolean isIsUserLogout() {
        return isUserLogout;
    }

    public void setIsUserLogout(boolean isUserLogout) {
        this.isUserLogout = isUserLogout;
    }

    public String getSentTimeString() {
        return sentTimeString;
    }

    public void setSentTimeString(String sentTimeString) {
        this.sentTimeString = sentTimeString;
    }

    public void setIsISentThisMessage(boolean isISentThisMessage) {
        this.isISentThisMessage = isISentThisMessage;
    }

    public boolean getIsISentThisMessage() {
        return isISentThisMessage;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    public LocalDate getSentTime() {
        return sentTime;
    }

    public int getIsAccepted() {
        return isAccepted;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public File getPath() {
        return path;
    }

    public void setSentTime(LocalDate sentTime) {
        this.sentTime = sentTime;
    }

    public void serializeScreenshotMessage() {
        File folder = new File("ScreenshotMessagesSerialization");
        if (folder.exists() == false) {
            folder.mkdir();
        }

        File izvjestaj = new File("ScreenshotMessagesSerialization" + File.separator + sender + "_" + receiver + "_" + randomGeneratorString() + ".ser");
        try {
            izvjestaj.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(izvjestaj));
            oos.writeObject(this);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        String status;
        if ( 0 == isAccepted ){
            status = "Pending";
        }
        else if ( 1 == isAccepted ){
            status = "Accepted";
        }
        else {
            status = "Declined";
        }
        return String.format("%-25s%-25s%-25s%-18s", ("from: " + sender), ("to: " + receiver), (sdf.format(new Date(Long.parseLong(sentTimeString)))), ("status: " + status));
    }
    
    public String randomGeneratorString(){
        Random rand = new Random();
        String retStr = "";
        for(int i=0; i<5; i++){
            retStr += rand.nextInt(9);
        }
        return retStr;
    }
    
    @Override
    public int compareTo(ScreenshotMessage screenshotMessage){
        return screenshotMessage.getSentTimeString().compareTo(sentTimeString);
    }
}
