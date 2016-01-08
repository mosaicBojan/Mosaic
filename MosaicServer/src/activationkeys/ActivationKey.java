package activationkeys;

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
public class ActivationKey implements Serializable{

    private final String value;   //activation key value

    public ActivationKey(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivationKey) {
            ActivationKey toCompare = (ActivationKey) obj;
            return this.value.equals(toCompare.value);
        }
        return false;
    }
    
    public void serializeKey() {
        File folder = new File("ListOfKey");
        if (folder.exists() == false) {
            folder.mkdir();
        }
        File keyPath = new File("ListOfKey" + File.separator + value + ".ser");
        try {
            keyPath.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(keyPath));
            oos.writeObject(this);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
}
