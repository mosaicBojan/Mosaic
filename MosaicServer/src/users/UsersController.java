package users;

import java.net.Socket;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UsersController {
    private ObservableList<User> onlineUsersList;
    private ListView<User> onlineUsersListView;

    public UsersController(ListView<User> onlineUsersListView) {
        onlineUsersList = FXCollections.observableArrayList();
        this.onlineUsersListView = onlineUsersListView;
    }

    public ObservableList<User> getOnlineUsersList() {
        return onlineUsersList;
    }
    
    public boolean isUsernameAvailable(String username){
        boolean isAvaiable = true;
        for(User u: onlineUsersList){
            if(u.getUsername().equals(username)){
                isAvaiable = false;
            }
        }
        return isAvaiable;
    }
    
    public void addNewOnlineUser(Socket socket, String username){
        User user = new User(socket, username);
        onlineUsersList.add(user);
        onlineUsersListView.setItems(onlineUsersList);
    }
}
