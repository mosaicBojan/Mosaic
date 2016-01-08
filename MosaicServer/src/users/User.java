/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import java.net.Socket;

/**
 *
 * @author Asus
 */
public class User {

    private Socket mySocket;
    private String username;

    public User() {
    }

    public User(Socket mySocket, String username) {
        this.mySocket = mySocket;
        this.username = username;
    }

    public Socket getMySocket() {
        return mySocket;
    }

    public String getUsername() {
        return username;
    }

    public void setMySocket(Socket mySocket) {
        this.mySocket = mySocket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        String retString = "";
        retString = String.format("%-10s\t\t:\t\t%s", username, mySocket.getLocalSocketAddress().toString());
        return retString;
    }
    

}
