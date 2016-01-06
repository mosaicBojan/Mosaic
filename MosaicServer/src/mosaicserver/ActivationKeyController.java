package mosaicserver;

import java.util.ArrayList;

public class ActivationKeyController {
    private final ArrayList<ActivationKey> activationKeyList;
    
    // constructor
    ActivationKeyController(){
        activationKeyList = new ArrayList<ActivationKey>();
    }
    
    // checks if handed argument is valid ActivationKey
    // every key is consisted of 16 alphanumeric characters
    public boolean isValidKey(String key){
        if (16 != key.length()) {
            return false;
        }
        
        for (char ch : key.toCharArray()){
            if (!Character.isLetterOrDigit(ch)){
                return false;
            }
        }
        return true;
    }
    
    // add new ActivationKey to the list
    public boolean addActivationKey(String key){
        return isValidKey(key)&(!activationKeyList.contains(new ActivationKey(key)))?activationKeyList.add(new ActivationKey(key)):false;
    }
    
    // remove ActivationKey from the list if present
    public boolean removeActivationKey(String key){
        return activationKeyList.contains(new ActivationKey(key))?activationKeyList.remove(new ActivationKey(key)):false;
    }
    
    // return key list
    public ArrayList<ActivationKey> getActivationKeyList(){
        return activationKeyList;
    }
    
    // ActivationKey class
    class ActivationKey {
        private final String value;   //activation key value
        
        ActivationKey(String value){
            this.value = value;
        }
        
        @Override
        public String toString(){
            return value;
        }
        
        @Override
        public boolean equals(Object obj){
            if(obj instanceof ActivationKey){
                ActivationKey toCompare = (ActivationKey) obj;
                return this.value.equals(toCompare.value);
            }
            return false;
        }
    }
}
