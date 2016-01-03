
package treeviewclasses;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListOfFiles {
    private ObservableList<NodeOfTree> listOfFiles = FXCollections.observableArrayList();

    public ListOfFiles() {
    }

    public ObservableList<NodeOfTree> getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(ObservableList<NodeOfTree> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    @Override
    public String toString() {
        String retStr = "";
        for(NodeOfTree n : listOfFiles){
            retStr += n.getPathWithoutHost();
        }
        return retStr;
    }
    
    
}
