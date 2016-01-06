package mosaicserver;

import java.net.Socket;
import java.util.ArrayList;

public class UsersController {
    private ArrayList<User> onlineUsersList;
    
    class User {
        private Socket socket;
        private String username;
        
        // constructor
        User(Socket socket, String username){
            this.socket = socket;
            this.username = username;
        }
        
        public Socket getSocket(){
            return socket;
        }
        
        public String getUsername(){
            return username;
        }
    }
}
