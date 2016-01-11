package activationkeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ActivationKeyController {

    private ArrayList<ActivationKey> activationKeyList;
    private ObservableList<String> keyListString;
    private ListView<String> keysListView;

    // constructor
    public ActivationKeyController(ListView<String> keysListView) {
        activationKeyList = new ArrayList<ActivationKey>();
        keyListString = FXCollections.observableArrayList();
        this.keysListView = keysListView;
        File folder = new File("ListOfKey");
        if (folder.exists() && folder.list().length != 0) {
            deserializeKeys();
            this.keysListView.setItems(keyListString);
        } else {
            System.out.println("Nema raspolozivih aktivacionih kljuceva u bazi!");
        }
    }

    public void deserializeKeys() {
        File folder = new File("ListOfKey");
        File[] files = folder.listFiles();
        for (File f : files) {
            ActivationKey key = null;
            //System.out.println("***" + f.getPath());
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                key = (ActivationKey) ois.readObject();
                ois.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            activationKeyList.add(key);
            keyListString.add(key.getValue());
        }
    }

    public void serializeActivationKeyList() {
        for (ActivationKey a : activationKeyList) {
            a.serializeKey();
        }
    }

    
    public boolean isKeyInList(String key){
        boolean retValue = false;
        ActivationKey ak = getActivationKeyForString(key);
        if(ak != null){
            retValue = true;
        }
        return retValue;
    }
    // checks if handed argument is valid ActivationKey
    // every key is consisted of 16 alphanumeric characters
    public boolean isValidKey(String key) {
        if (16 != key.length()) {
            return false;
        }

        for (char ch : key.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    // add new ActivationKey to the list
    public void addActivationKey(String key) {
        ActivationKey ak = new ActivationKey(key);
        activationKeyList.add(ak);
        keyListString.add(ak.toString());
        keysListView.setItems(keyListString);
        Thread t = new Thread() {
            public void run() {
                ak.serializeKey();
            }
        };
        t.start();
    }

    // remove ActivationKey from the list if present
    public void removeActivationKey(String key) {
        ActivationKey ak = getActivationKeyForString(key);
        activationKeyList.remove(ak);
        String s = getStringValueOfKeyForString(key);
        System.out.println("Key for remove: " + s);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                //stage.hide();
                keyListString.remove(s);
            }
        });
        
        keysListView.setItems(keyListString);
        (new File("ListOfKey" + File.separator +  key + ".ser")).delete();
    }

    public ActivationKey getActivationKeyForString(String key){
        ActivationKey ak = null;
        for(ActivationKey a: activationKeyList){
            if(a.getValue().equals(key)){
                ak = a;
                break;
            }
        }
        
        return ak;
    }
    
    public String getStringValueOfKeyForString(String key){
        String str = null;
        for(String a: keyListString){
            if(a.equals(key)){
                str = a;
                break;
            }
        }
        
        return str;
    }
    
    // return key list
    public ArrayList<ActivationKey> getActivationKeyList() {
        return activationKeyList;
    }

    public ObservableList<String> getKeyListString() {
        return keyListString;
    }

}
