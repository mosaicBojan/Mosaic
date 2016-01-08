package users;

import java.net.Socket;
import java.util.ArrayList;

public class UsersController {
    private ArrayList<User> onlineUsersList;

    public UsersController() {
        onlineUsersList = new ArrayList<>();
    }

    public ArrayList<User> getOnlineUsersList() {
        return onlineUsersList;
    }
    
}
