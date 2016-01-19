package users;

import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UsersController {
    private ObservableList<User> onlineUsersList;
    private ListView<User> onlineUsersListView;
    //boolean flag koji za pozitivnu vrijednost signalizira da se vrsi testiranje, te ne koristi elemente vezane za GUI
    private boolean testFlag = false;
    
    public void setTestFlag(boolean testFlag){
        this.testFlag = testFlag;
    }

    public UsersController(ListView<User> onlineUsersListView) {
        onlineUsersList = FXCollections.observableArrayList();
        this.onlineUsersListView = onlineUsersListView;
    }
    
    //defaultni konstruktor dodan u svrhe testiranja
    public UsersController(){
        this.onlineUsersList = FXCollections.observableArrayList();
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
    
    public boolean addNewOnlineUser(Socket socket, String username){
        if (isUsernameAvailable(username)){
            User user = new User(socket, username);
            if (false == testFlag) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        onlineUsersList.add(user);
                    }
                });
                //onlineUsersList.add(user);
                onlineUsersListView.setItems(onlineUsersList);
            } else if (true == testFlag) {
                onlineUsersList.add(user);
            }
            return true;
        }
        else
            return false;
    }
    
    public boolean removeUser(String username){
        if (!isUsernameAvailable(username)) {
            User user = getUserFromListForString(username);
            if (false == testFlag) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        onlineUsersList.remove(user);
                    }
                });

                onlineUsersListView.setItems(onlineUsersList);
            } else if (true == testFlag) {
                onlineUsersList.remove(user);
            }
            return true;
        } else {
            return false;
        }
    }
    
    public User getUserFromListForString(String username){
        User user = null;
        for(User u: onlineUsersList){
            if(u.getUsername().equals(username)){
                user = u;
                break;
            }
        }
        
        return user;
    }
    
}
