package treeviewclasses;

import java.io.File;
import java.io.FilenameFilter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class FileSystemTree extends Thread {

    private TreeView treeView;
    private ImageView imageView;
    private ObservableList<NodeOfTree> allNodes = FXCollections.observableArrayList();
    private ObservableList<NodeOfTree> rootNodes = FXCollections.observableArrayList();
    private ObservableList<NodeOfTree> alltempNodes = FXCollections.observableArrayList();
    private NodeOfTree rootNode;
    private String hostName;
    private NodeOfTree tempRootNode;
    private HashMap<NodeOfTree, ListOfFiles> hashNodeListOfFiles = new HashMap<>();
    private TextField explorerPathTextField;
    private boolean isFirstPicture = true;
    private double originalWidth;
    private double originalHeight;
    private NodeOfTree selectedItem;
    private Label explorerImageLabel;
    private boolean rootAlreadySet = false;
    private int numOfRootDirs = 0;
    private  double originalImageHeight = 0;
    private double originalImageWidth = 0;
    private double imageStackPaneWidth = 0;
    private double imageStackPaneHeight = 0;
    private double resizedImageHeight = 0;
    private double resizedImageWidth = 0;

    public FileSystemTree() {
    }

    public FileSystemTree(TreeView treeView, ImageView imageView, TextField explorerPathTextField, Label explorerImageLabel) {
        this.treeView = treeView;
        this.imageView = imageView;
        this.explorerPathTextField = explorerPathTextField;
        this.explorerImageLabel = explorerImageLabel;
    }

    public void run() {
        /* set host Node to Tree */
        if (!rootAlreadySet) {
            rootAlreadySet = true;
            hostName = "computer";
            try {
                hostName = InetAddress.getLocalHost().getHostName();    //Pronadji naziv racunara
            } catch (UnknownHostException x) {
            }
            rootNode = new NodeOfTree(new File(hostName));    //Kreiranje root node-a

            treeView.setRoot(rootNode);                 //Dodavanje root node-a u treeView

            rootNodes.add(rootNode);                     //Dodavanje root node-a u allNodes listu
            rootNode.setExpanded(true);
        }
        ObservableList<NodeOfTree> rootDirs = this.getRootDirectories();
        numOfRootDirs = rootDirs.size();
        numOfRootDirs++;
        numOfRootDirs++;
        for (NodeOfTree n : rootDirs) {
            rootNode.getChildren().add(n);
            int br = 0;
            for (NodeOfTree part : rootNodes) {
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

        treeView.setCellFactory(drvo -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        //setText(item);
                        String value = this.getTreeItem().getValue();
                        this.setDisclosureNode(null);
                        this.setText(value);

                        if (value.equals(hostName)) {
                            Image myCompImage = new Image("icons/explorerTreeViewIcons/myComputer.png");
                            ImageView iv = new ImageView();
                            iv.setImage(myCompImage);
                            this.setGraphic(iv);
                        } else if (value.endsWith(".jpg") || value.endsWith(".png")) {
                            Image myCompImage = new Image("icons/explorerTreeViewIcons/imagePlaceholder.png");
                            ImageView iv = new ImageView();
                            iv.setImage(myCompImage);
                            this.setGraphic(iv);
                        } else {
                            if (this.getTreeItem().isExpanded() == false) {
                                Image myCompImage = new Image("icons/explorerTreeViewIcons/treeFolderClosed.png");
                                ImageView iv = new ImageView();
                                iv.setImage(myCompImage);
                                this.setGraphic(iv);
                            } else {
                                Image myCompImage = new Image("icons/explorerTreeViewIcons/treeFolderOpen.png");
                                ImageView iv = new ImageView();
                                iv.setImage(myCompImage);
                                this.setGraphic(iv);
                            }
                        }
                    }

                }
            };

            cell.setOnMousePressed(event -> {
                if (!cell.isEmpty()) {
                    TreeItem<String> si = cell.getTreeItem();
                    selectedItem = (NodeOfTree) si;      //Selektovani Node
                    explorerPathTextField.setText(selectedItem.getPathWithoutHost());   //Ispisi putanju u textField
                    /**
                     * **************************************************
                     */
                    //      U slucaju da se klikne na root node          //
                    if (selectedItem.getPathWithoutHost().equals("")) {
                        ObservableList<NodeOfTree> nodes = getRootDirectories();  //Ubacivanje root node-ova u listu
                        rootNode.getChildren().removeAll(rootNodes);        //Brisanje trenutnih node-ova
                        rootNode.getChildren().addAll(nodes);               //Dodavanje refresh-ovane liste kao nodove
                        for (NodeOfTree n : nodes) {                              //U slucaju da je folder potrebno mu je dodati neki node,
                            tempRootNode = n;                               //da bi se treeitem prikazao kao folder
                            checkIsDirAndAddTempNode();                     //pozivanje metode koja dodaje temp root
                        }
                        rootNodes = nodes;                                  //Zamjena nodova u rootNodes listi
                    } else {                                                //else predstavlja da nije selektovan rootNode
                        //  U slucaju da se kllikne na bilo koji drugi fajl   //
                        if (selectedItem.getPathWithoutHost().toLowerCase().endsWith(".jpg") || selectedItem.getPathWithoutHost().toLowerCase().endsWith(".png")) {
                            //Ovdje se definise koje akcije ce se izvrsiti ako je selektovani fajl slika
                            imageView.setVisible(true);
                            
                            imageView.setCache(true);
                            imageView.setCacheHint(CacheHint.SPEED);
                            
                            //imageView.setFitHeight(0);
                            //imageView.setFitHeight(0);
                            
                            explorerImageLabel.setVisible(false);
                            Image image = new Image(new File(selectedItem.getPathWithoutHost()).toURI().toString());
                            if ( image.getHeight() > 1600 || image.getWidth() > 1600 ){
                                image = new Image(new File(selectedItem.getPathWithoutHost()).toURI().toString(), 1600, 1600, true, true);
                            }
                            originalImageHeight = image.getHeight();
                            originalImageWidth = image.getWidth();
                            
                            
                            
                            imageStackPaneWidth = ((StackPane)(imageView.getParent())).getWidth();
                            imageStackPaneHeight = ((StackPane)(imageView.getParent())).getHeight();
                            
                            //((StackPane)(imageView.getParent())).setStyle("-fx-background-color: red;");
                            
                           
                            
                            
                            if ( originalImageWidth/imageStackPaneWidth > originalImageHeight/imageStackPaneHeight ) {
                                if (image.getWidth() < imageStackPaneWidth){
                                    //System.out.println("FIT PO SIRINI SLIKE: " + image.getWidth());
                                    imageView.setFitWidth(image.getWidth());
                                }
                                else {
                                    //System.out.println("FIT PO SIRINI PANELA: " + imageStackPaneWidth);
                                    imageView.setFitWidth(imageStackPaneWidth);
                                }
                            }
                            else {
                                if (image.getHeight() < imageStackPaneHeight){
                                    //System.out.println("FIT PO DUZINI SLIKE: " + image.getHeight());
                                    imageView.setFitHeight(image.getHeight());
                                }
                                else {
                                    //System.out.println("FIT PO DUZINI PANELA: " + imageStackPaneHeight);
                                    imageView.setFitHeight(imageStackPaneHeight);
                                }
                            }
                            imageView.setImage(image);
                        } else {
                            // Ovdje se definise koje akcije ce se izvrsiti ako je selektovan folder //
                            Image img = imageView.getImage();
                            if (img != null) {
                                //ovdje se definisu akcije koje ce skloniti sliku kada se klikne na folder
                                imageView.setVisible(false);
                                explorerImageLabel.setVisible(true);
                            }
                            ListOfFiles lof = new ListOfFiles();
                            File[] selectedItemFiles = null;    // U ovaj niz ce se upisati svi fajlovi koji budu pronadjeni u sljedecim instrukcijama
                            selectedItemFiles = new File(selectedItem.getPathWithoutHost() + File.separator).listFiles(new FilenameFilter() {
                                public boolean accept(File dir, String name) {
                                    return (name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png")
                                            || new File(dir.toString() + File.separator + name).isDirectory())
                                            & !(new File(dir.toString() + File.separator + name).isHidden());
                                }
                            });
                            NodeOfTree currentlyTempNode = null;      //U ovaj Node se upisuje tempNode koji se nalazi na selektovanoj putanji
                            try {
                                for (NodeOfTree n : alltempNodes) {
                                    if (n.getPathWithoutHost().equals(selectedItem.getPathWithoutHost() + File.separator + "temp_node_speed_up_tree_view")) {
                                        currentlyTempNode = n;                      //Trenutni temp node se prebacuje u currentlyTempNode
                                        alltempNodes.remove(currentlyTempNode);     //Uklanjanje tog noda iz allTempNode liste
                                        break;
                                    }
                                }
                            } catch (NullPointerException ex) {
                                System.out.println("Nema temp_node_speed_up_tree_view nodova!");
                            }

                            if (currentlyTempNode != null) {
                                selectedItem.getChildren().remove(currentlyTempNode);   //Uklanjanje temp noda iz treeView-a
                            }

                            if (selectedItemFiles != null) {    //U ovom bloku koda se obradjuju akcije ako folder ima podfajlova
                                for (File file : selectedItemFiles) {
                                    //Ovdje se definise koje akcije ce se izvrsiti ako je selektovan folder koji nije prazan
                                    lof.getListOfFiles().add(new NodeOfTree(new File(hostName + File.separator + file.getPath() + File.separator)));   //Kreiraj djecu selektovanog node-a
                                }
                                ListOfFiles listFromHash = null;    //Potrebno je pronaci odgovarajucu listu iz hash mape
                                listFromHash = hashNodeListOfFiles.get(selectedItem);   //Lista se upisuje u listFromHash
                                if (listFromHash != null) {
                                    //Ovdje se definisu akcije ako je selektovana putanja vec u hash mapi
                                    selectedItem.getChildren().removeAll(listFromHash.getListOfFiles());    //Obrisi sve nodove iz treeView-a
                                    selectedItem.getChildren().addAll(lof.getListOfFiles());                //Dodaj nove nodove, koji su gore upisani u lof
                                    hashNodeListOfFiles.remove(selectedItem);                               //Brisanje starog noda iz hash mape
                                    hashNodeListOfFiles.put(selectedItem, lof);                             //Postavljanje novog noda u hash mapu
                                    for (NodeOfTree node : lof.getListOfFiles()) {                                //Ponovo postavi temp nodove ako je folder
                                        tempRootNode = node;
                                        checkIsDirAndAddTempNode();
                                    }
                                } else {
                                    //Ovdje se definisu akcije ako selektovana putanja nije u hash mapi
                                    selectedItem.getChildren().addAll(lof.getListOfFiles());                //Dodavanje novih nodova u treeView
                                    hashNodeListOfFiles.put(selectedItem, lof);                             //Dodavanje novih nodova u hash map
                                    for (NodeOfTree node : lof.getListOfFiles()) {                                //Ponovo postavi temp nodove
                                        tempRootNode = node;                                                //Jedina razlika od gore napisanog koda je sto se ne brise nista
                                        //System.out.println("Temp: " + tempRootNode);
                                        checkIsDirAndAddTempNode();
                                    }
                                }
                            } else {
                                //Napisati sta ako je fajl prazan
                                System.out.println("Folder je prazan");
                            }
                        }
                    }
                    
                    ((AnchorPane)(imageView.getParent().getParent())).widthProperty().addListener(new ChangeListener<Number>() {
                                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldStackPaneWidth, Number newStackPaneWidth) {
                                    imageStackPaneWidth = newStackPaneWidth.doubleValue();
                                    if ( originalImageWidth >= newStackPaneWidth.doubleValue() & resizedImageHeight <= imageStackPaneHeight) {
                                        imageView.setFitWidth(newStackPaneWidth.doubleValue() - 40);
                                        resizedImageWidth = newStackPaneWidth.doubleValue() - 20;
                                        resizedImageHeight = originalHeight/(originalImageWidth/(newStackPaneWidth.doubleValue() - 20));
                                    }
                                }
                            });
                    ((AnchorPane)(imageView.getParent().getParent())).heightProperty().addListener(new ChangeListener<Number>() {
                                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldStackPaneHeight, Number newStackPaneHeight) {
                                    imageStackPaneHeight = newStackPaneHeight.doubleValue();
                                    if ( originalImageHeight >= newStackPaneHeight.doubleValue()  & resizedImageWidth <= imageStackPaneWidth) {
                                        imageView.setFitHeight(newStackPaneHeight.doubleValue() - 40);
                                        resizedImageHeight = newStackPaneHeight.doubleValue() - 20;
                                        resizedImageWidth = originalWidth/(originalImageHeight/(newStackPaneHeight.doubleValue() - 20));
                                    }
                                }
                            });
                    if (selectedItem.isExpanded()) {          //Ovaj kod omogucava da se otvaraju tree item-i na klik
                        selectedItem.setExpanded(false);
                    } else {
                        selectedItem.setExpanded(true);
                    }
                }
            });
            return cell;
        });

        /*treeView.addTreeListener(new TreeListener() {
         public void treeExpanded(TreeEvent e) {
         TreeItem ti = (TreeItem) e.item;
         //      ti.setImage(new Image(d, "c:\\icons\\open.gif"));
         }

         public void treeCollapsed(TreeEvent e) {
         TreeItem ti = (TreeItem) e.item;
         //        ti.setImage(new Image(d, "c:\\icons\\folder.gif"));
         }
         });*/
    }

    /* Metoda koja vraca listu root node direktorijuma */
    public ObservableList<NodeOfTree> getRootDirectories() {
        ObservableList<NodeOfTree> retList = FXCollections.observableArrayList();
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path name : rootDirectories) {
            NodeOfTree tempNode = new NodeOfTree(new File(rootNode + File.separator + name.toString()));
            retList.add(tempNode);
        }
        return retList;
    }

    public void checkIsDirAndAddTempNode() {
        if (new File(tempRootNode.getPathWithoutHost()).list() != null && new File(tempRootNode.getPathWithoutHost()).isDirectory() && new File(tempRootNode.getPathWithoutHost()).list().length > 0) {
            NodeOfTree tmpNode = new NodeOfTree(new File(tempRootNode + File.separator + "temp_node_speed_up_tree_view"));
            tempRootNode.getChildren().add(tmpNode);
            try {
                for (NodeOfTree n : alltempNodes) {
                    if (n.getPathWithoutHost().equals(tmpNode.getPathWithoutHost())) {
                        alltempNodes.remove(n);
                    }
                }
            } catch (Exception ex) {
                //System.out.println("EX");
            }
            alltempNodes.add(tmpNode);
        } else {
            //System.out.println("Ovo je fajl: " + tempRootNode.getPathWithoutHost());
        }
    }

    // For DELETE Action //
    public void removeOneNode(File file) {
        ListOfFiles lof = null;
        NodeOfTree tmpNd = null;
        NodeOfTree nodeForRemove = null;
        for (NodeOfTree name : hashNodeListOfFiles.keySet()) {

            String key = name.getPathWithoutHost();
            String fullPath = file.getPath();
            int index = fullPath.lastIndexOf("\\");
            String tempPath = fullPath.substring(0, index);
            if (key.equals(tempPath)) {
                lof = hashNodeListOfFiles.get(name);
                tmpNd = name;
            }
        }
        ListOfFiles lofTemp = new ListOfFiles();
        lofTemp.getListOfFiles().addAll(lof.getListOfFiles());
        for (NodeOfTree n : lofTemp.getListOfFiles()) {
            if (n.getPathWithoutHost().equals(file.getPath())) {
                lof.getListOfFiles().remove(n);
                nodeForRemove = n;
            }
        }
        tmpNd.getChildren().remove(nodeForRemove);
        explorerPathTextField.setText(selectedItem.getPathWithoutHost());
    }

    // For CREATE action //
    public void addNewFolderNodeToTree(File folder) {
        String newNodePath = hostName + File.separator + folder.getPath();
        NodeOfTree nodeForAdd = new NodeOfTree(new File(newNodePath));
        ListOfFiles lof = null;
        NodeOfTree tmpNd = null;

        for (NodeOfTree n : hashNodeListOfFiles.keySet()) {
            String key = n.getPathWithoutHost();
            String fullPath = folder.getPath();
            int index = fullPath.lastIndexOf("\\");
            String tempPath = fullPath.substring(0, index);
            if (key.equals(tempPath)) {
                lof = hashNodeListOfFiles.get(n);
                tmpNd = n;
                break;
            }
        }

        lof.getListOfFiles().add(nodeForAdd);
        tmpNd.getChildren().add(nodeForAdd);
    }

    // For RENAME action //
    public void exchangeTwoNodes(File oldFile, File newFile) {
        String newNodePath = hostName + File.separator + newFile.getPath();
        NodeOfTree nodeForAdd = new NodeOfTree(new File(newNodePath));
        ListOfFiles lof = null;
        NodeOfTree tmpNd = null;

        for (NodeOfTree n : hashNodeListOfFiles.keySet()) {
            String key = n.getPathWithoutHost();
            String fullPath = oldFile.getPath();
            int index = fullPath.lastIndexOf("\\");
            String tempPath = fullPath.substring(0, index);
            if (key.equals(tempPath)) {
                lof = hashNodeListOfFiles.get(n);
                tmpNd = n;
                break;
            }
        }

        for (NodeOfTree n : lof.getListOfFiles()) {
            String oldStr = oldFile.getPath();
            String cmpStr = n.getPathWithoutHost();
            if (oldStr.equals(cmpStr)) {
                lof.getListOfFiles().remove(n);
                tmpNd.getChildren().remove(n);
                lof.getListOfFiles().add(nodeForAdd);
                tmpNd.getChildren().add(nodeForAdd);
                tmpNd.setExpanded(true);
                break;
            }
        }

        ListOfFiles lofSelected = null;
        for (NodeOfTree n : hashNodeListOfFiles.keySet()) {
            String key = n.getPathWithoutHost();
            String fullPath = oldFile.getPath();
            if (key.equals(fullPath)) {
                lofSelected = hashNodeListOfFiles.get(n);
                hashNodeListOfFiles.remove(n);
                hashNodeListOfFiles.put(nodeForAdd, lofSelected);
                nodeForAdd.getChildren().addAll(lofSelected.getListOfFiles());
                nodeForAdd.setExpanded(false);
                break;
            }
        }
    }

    public NodeOfTree getSelectedItem() {
        return selectedItem;
    }

    public String getSelectedPath() {
        if (selectedItem != null) {
            return selectedItem.getPathWithoutHost();
        } else {
            return null;
        }
    }

}
