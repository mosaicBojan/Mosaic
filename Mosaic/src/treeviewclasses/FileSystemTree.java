package treeviewclasses;

import java.io.File;
import java.io.FilenameFilter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FileSystemTree extends Thread {

    private TreeView treeView;
    private ImageView imageView;
    private ObservableList<Node> allNodes = FXCollections.observableArrayList();
    private ObservableList<Node> rootNodes = FXCollections.observableArrayList();
    private ObservableList<Node> alltempNodes = FXCollections.observableArrayList();
    private Node rootNode;
    private String hostName;
    private Node tempRootNode;
    private HashMap<Node, ListOfFiles> hashNodeListOfFiles = new HashMap<>();

    public FileSystemTree() {
    }

    public FileSystemTree(TreeView treeView, ImageView imageView) {
        this.treeView = treeView;
        this.imageView = imageView;
    }

    public void run() {
        /* set host Node to Tree */
        hostName = "computer";
        try {
            hostName = InetAddress.getLocalHost().getHostName();    //Pronadji naziv racunara
        } catch (UnknownHostException x) {
        }
        rootNode = new Node(new File(hostName));    //Kreiranje root node-a
        treeView.setRoot(rootNode);                 //Dodavanje root node-a u treeView
        rootNodes.add(rootNode);                     //Dodavanje root node-a u allNodes listu
        rootNode.setExpanded(true);

        ObservableList<Node> rootDirs = this.getRootDirectories();
        for (Node n : rootDirs) {
            rootNode.getChildren().add(n);
            int br = 0;
            for (Node part : rootNodes) {
                if (n.getPathWithoutHost().equals(part.getPathWithoutHost())) {
                    br++;
                }
                if (br != 0) {
                    break;
                }
            }
            if (br == 0) {
                rootNodes.add(n);
            }
            tempRootNode = n;
            this.checkIsDirAndAddTempNode();
        }
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //System.out.println("CLIKED ON TREE ITEM");
                
                Node selectedItem = (Node) newValue;
                hashNodeListOfFiles.put(rootNode, new ListOfFiles());
                /*****************************************************/
                //      U slucaju da se klikne na root node          //
                if (selectedItem.getPathWithoutHost().equals("")) {
                    ObservableList<Node> nodes = getRootDirectories();
                    rootNode.getChildren().removeAll(rootNodes);
                    rootNode.getChildren().addAll(nodes);
                    for(Node n: nodes){
                        tempRootNode = n;
                        checkIsDirAndAddTempNode();
                    }
                    rootNodes = nodes;
                }else{
                /******************************************************/
                
                /******************************************************/
                //  U slucaju da se kllikne na bilo koji drugi fajl   //
                    if (selectedItem.getPathWithoutHost().toLowerCase().endsWith(".jpg") || selectedItem.getPathWithoutHost().toLowerCase().endsWith(".png")) {
                        //Ovdje se definise koje akcije ce se izvrsiti ako je selektovani fajl slika
                        //System.out.println("IMAGE PREVIEW");
                        Image image = new Image(new File(selectedItem.getPathWithoutHost()).toURI().toString());
                        imageView.setImage(image);
                    }
                    else{
                        //Ovdje se definise koje akcije ce se izvrsiti ako je selektovan folder
                        ListOfFiles lof = new ListOfFiles();
                        File[] selectedItemFiles = null;
                        selectedItemFiles = new File(selectedItem.getPathWithoutHost() + File.separator).listFiles(new FilenameFilter() {
                            public boolean accept(File dir, String name) {
                                return (name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png")
                                        || new File(dir.toString() + File.separator + name).isDirectory())
                                        & !(new File(dir.toString() + File.separator + name).isHidden());
                            }
                        });
                        
                        Node currentlyTempNode = null;
                        try {
                            for (Node n : alltempNodes) {
                                if (n.getPathWithoutHost().equals(selectedItem.getPathWithoutHost() + File.separator + "temp_node_speed_up_tree_view")) {
                                    currentlyTempNode = n;
                                    alltempNodes.remove(currentlyTempNode);
                                    break;
                                }
                            }
                        } catch (NullPointerException ex) {
                            System.out.println("Nema temp_node_speed_up_tree_view nodova!");
                        }
                        
                        
                        if(currentlyTempNode != null){
                            selectedItem.getChildren().remove(currentlyTempNode);
                        }
                        
                        if (selectedItemFiles != null) {
                            for (File file : selectedItemFiles) {
                                //Ovdje se definise koje akcije ce se izvrsiti ako je selektovan folder koji nije prazan
                                lof.getListOfFiles().add(new Node(new File(hostName + File.separator + file.getPath() + File.separator)));
                            }
                            ListOfFiles listFromHash = null;
                            listFromHash = hashNodeListOfFiles.get(selectedItem);
                            if(listFromHash != null){
                                //Ovdje se definisu akcije ako je selektovana putanja vec u hash mapi
                                selectedItem.getChildren().removeAll(listFromHash.getListOfFiles());
                                selectedItem.getChildren().addAll(lof.getListOfFiles());
                                hashNodeListOfFiles.remove(selectedItem);
                                hashNodeListOfFiles.put(selectedItem, lof);
                                for(Node node: lof.getListOfFiles()){
                                    tempRootNode = node;
                                    checkIsDirAndAddTempNode();
                                }
                            }
                            else{
                                //Ovdje se definisu akcije ako selektovana putanja nije u hash mapi
                                selectedItem.getChildren().addAll(lof.getListOfFiles());
                                hashNodeListOfFiles.put(selectedItem, lof);
                                for(Node node: lof.getListOfFiles()){
                                    tempRootNode = node;
                                    checkIsDirAndAddTempNode();
                                }
                            }
                        } else {
                            //Napisati sta ako je fajl prazan
                            System.out.println("Folder je prazan");
                        }
                    }
                    
                /******************************************************/
                }
            }
            
        });
    }

    /* Metoda koja vraca listu root node direktorijuma */
    public ObservableList<Node> getRootDirectories() {
        ObservableList<Node> retList = FXCollections.observableArrayList();
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path name : rootDirectories) {
            Node tempNode = new Node(new File(rootNode + File.separator + name.toString()));
            retList.add(tempNode);
        }
        return retList;
    }

    public void checkIsDirAndAddTempNode() {
        if (new File(tempRootNode.getPathWithoutHost()).isDirectory()) {
            Node tmpNode = new Node(new File(tempRootNode + File.separator + "temp_node_speed_up_tree_view"));
            tempRootNode.getChildren().add(tmpNode);
            try{
                for (Node n : alltempNodes) {
                    if (n.getPathWithoutHost().equals(tmpNode.getPathWithoutHost())) {
                        alltempNodes.remove(n);
                    }
                }
            } catch(Exception ex){
                //System.out.println("EX");
            }
            alltempNodes.add(tmpNode);
        } else {
            //System.out.println("Ovo je fajl: " + tempRootNode.getPathWithoutHost());
        }
    }

}
