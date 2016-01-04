/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeviewclasses;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Asus
 */
public class NodeOfTree extends TreeItem<String> {

    private File nodeFile;
   

    public NodeOfTree(File file) {
        super(file.getName());
        nodeFile = file;
        //this.setIcon();
    }
    
    public NodeOfTree(File file, Node image) {
        super(file.getName(), image);
        nodeFile = file;
        //this.setIcon();
    }

    /*public void setIcon(){
        if("Asus-PC".equals(this.getNodeFile().toString())){
            //System.out.println("URL: " + getClass().getResourceAsStream("myComputer.png"));
            System.out.println("ROOT IMAGE");
            Image myCompImage = new Image(getClass().getResourceAsStream("myComputer.png"));
            this.setGraphic(new ImageView(myCompImage));
        } else{
            System.out.println("FOLDER IMAGE");
            Image folderImage = new Image(getClass().getResourceAsStream("treeFolderClosed.png"));
            this.setGraphic(new ImageView(folderImage));
        }
    }*/
    
    public File getNodeFile() {
        return nodeFile;
    }

    public void setNodeFile(File nodeFile) {
        this.nodeFile = nodeFile;
    }

    @Override
    public String toString() {
        return nodeFile.toString();
    }

    public String getPathWithoutHost() {
        String s = "";
        String hostName = "computer";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException x) {
        }
        if (!nodeFile.toString().equals(hostName) & nodeFile.toString().startsWith(hostName)) {
            s = nodeFile.toString().substring(hostName.length() + 1);
        }
        return s;
    }
    
    public void addSeparatorToPath(){
        String temp = nodeFile.getPath() + File.separator;
        nodeFile = new File(temp);
    }
    
    public boolean equals(NodeOfTree n){
        if(this.getNodeFile().toString().equals(n.getNodeFile().toString())){
            return true;
        }
        return false;
    }

}
