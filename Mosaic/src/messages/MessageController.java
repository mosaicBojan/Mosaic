
package messages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import screenshots.ScreenshotMessage;
import screenshots.ScreenshotMessageController;

public class MessageController {
    private ListView messagesListView;
    private ScreenshotMessageController screenshotMessageController;
    private ScreenshotMessage tabScreenshotListViewSelectedItem;
    
    public MessageController(){
        
    }

    public void setMessagesListView(ListView messagesListView) {
        this.messagesListView = messagesListView;
    }

    public void setScreenshotMessageController(ScreenshotMessageController screenshotMessageController) {
        this.screenshotMessageController = screenshotMessageController;
    }

    public void setTabScreenshotListViewSelectedItem(ScreenshotMessage tabScreenshotListViewSelectedItem) {
        this.tabScreenshotListViewSelectedItem = tabScreenshotListViewSelectedItem;
    }
    
    
    
    public void addMessagesToListView(){
        ObservableList<ScreenshotMessage> tempList2 = FXCollections.observableArrayList();
        for (ScreenshotMessage m : screenshotMessageController.getScreenshotMessageList()) {
            if (m.getIsAccepted() == 1) {
                System.out.println("Message: " + m.getSender() + " - " + m.getReceiver() + " - " + m.getSentTimeString());
                tempList2.add(m);
            }
        }
        javafx.collections.FXCollections.sort(tempList2);
        System.out.println("zzzzzzzzzzzzzzz");
        messagesListView.setItems(tempList2);
        messagesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                tabScreenshotListViewSelectedItem = (ScreenshotMessage) messagesListView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + tabScreenshotListViewSelectedItem);
            }

        });
    }
}
