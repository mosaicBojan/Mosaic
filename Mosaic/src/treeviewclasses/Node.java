/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeviewclasses;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Asus
 */
public class Node extends TreeItem<String> {

    private File nodeFile;

    public Node(File file) {
        super(file.getName());
        nodeFile = file;
    }

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
    
    public boolean equals(Node n){
        if(this.getNodeFile().toString().equals(n.getNodeFile().toString())){
            return true;
        }
        return false;
    }

}
