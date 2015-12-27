
package treeviewclasses;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListOfFiles {
    private ObservableList<Node> listOfFiles = FXCollections.observableArrayList();

    public ListOfFiles() {
    }

    public ObservableList<Node> getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(ObservableList<Node> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }
    
}
