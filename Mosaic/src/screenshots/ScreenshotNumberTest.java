package screenshots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import mosaic.ActivationTest;

/**
 *
 * @author Asus
 */
public class ScreenshotNumberTest implements Serializable {

    private int screenshotSend;
    private int screenshotReceive;

    public ScreenshotNumberTest() {
    }

    public ScreenshotNumberTest(int screenshotSend, int screenshotReceive) {
        this.screenshotSend = screenshotSend;
        this.screenshotReceive = screenshotReceive;
    }

    public int getScreenshotSend() {
        return screenshotSend;
    }

    public int getScreenshotReceive() {
        return screenshotReceive;
    }

    public void setScreenshotSend(int screenshotSend) {
        this.screenshotSend = screenshotSend;
    }

    public void setScreenshotReceive(int screenshotReceive) {
        this.screenshotReceive = screenshotReceive;
    }

    public void serializeScreenshotNumbers() {
        File folder = new File("ScreenshotNumbersReport");
        if (folder.exists() == false) {
            folder.mkdir();
        } else {
            File[] files = folder.listFiles();
            for(File f: files){
                f.delete();
            }
        }

        File izvjestaj = new File("ScreenshotNumbersReport" + File.separator + "numbers.ser");
        try {
            izvjestaj.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(izvjestaj));
            oos.writeObject(this);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getNumOfSent() {
        int retVal = 0;

        File izvjestaj = new File("ScreenshotNumbersReport" + File.separator + "numbers.ser");
        ScreenshotNumberTest test = null;
        if (izvjestaj.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(izvjestaj));
                test = (ScreenshotNumberTest) ois.readObject();
                ois.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (test != null) {
            retVal = test.getScreenshotSend();
            System.out.println("GET SSS: " + test.getScreenshotSend());
        }
        return retVal;
    }

    public int getNumOfReceive() {
        int retVal = 0;

        File izvjestaj = new File("ScreenshotNumbersReport" + File.separator + "numbers.ser");
        ScreenshotNumberTest test = null;
        if (izvjestaj.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(izvjestaj));
                test = (ScreenshotNumberTest) ois.readObject();
                ois.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (test != null) {
            retVal = test.getScreenshotReceive();
        }

        return retVal;
    }
}
