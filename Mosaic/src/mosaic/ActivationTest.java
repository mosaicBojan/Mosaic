/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Asus
 */
public class ActivationTest implements Serializable {

    private boolean isAvtivated;

    public ActivationTest() {

    }

    public void serializeActivationTest() {
        File folder = new File("ActivationReport");
        if (folder.exists() == false) {
            folder.mkdir();
        }
        File izvjestaj = new File("ActivationReport" + File.separator + "isValidateFile.ser");
        try {
            izvjestaj.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(izvjestaj));
            oos.writeObject(this);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isValidate() {
        boolean retVal = false;

        File izvjestaj = new File("ActivationReport" + File.separator + "isValidateFile.ser");
        ActivationTest at = null;
        if (izvjestaj.exists()) {
            try {
                //izvjestaj.createNewFile();
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(izvjestaj));
                at = (ActivationTest) ois.readObject();
                ois.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (at != null && at.getIsAvtivated()) {
            retVal = true;
        } else {
            retVal = false;
        }

        return retVal;
    }

    public boolean getIsAvtivated() {
        return isAvtivated;
    }

}
