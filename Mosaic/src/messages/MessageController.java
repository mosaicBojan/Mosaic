package messages;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import screenshots.ScreenshotMessage;
import screenshots.ScreenshotMessageController;

public class MessageController {

    private ListView messagesListView;
    private ScreenshotMessageController screenshotMessageController;
    private ScreenshotMessage tabScreenshotListViewSelectedItem;
    private ImageView imageView;
    private double messagesImageStackPaneWidth = 0;
    private double messagesOriginalImageWidth = 0;
    private double messagesResizedImageWidth = 0;
    private double messagesResizedImageHeight = 0;
    private double messagesImageStackPaneHeight = 0;
    private double messagesOriginalImageHeight = 0;
    private Button messagesOpenButton;
    private Button messagesDeleteButton;
    private Label messagesPreviewLabel;

    public MessageController() {

    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setMessagesOpenButton(Button messagesOpenButton) {
        this.messagesOpenButton = messagesOpenButton;
    }

    public void setMessagesDeleteButton(Button messagesDeleteButton) {
        this.messagesDeleteButton = messagesDeleteButton;
    }

    public void setMessagesPreviewLabel(Label messagesPreviewLabel) {
        this.messagesPreviewLabel = messagesPreviewLabel;
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

    public void addMessagesToListView() {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                System.out.println("addMEssagesToListView CALLED");
                ObservableList<ScreenshotMessage> list = FXCollections.observableArrayList();
                list.addAll(screenshotMessageController.getScreenshotMessageList());
                javafx.collections.FXCollections.sort(list);
                //javafx.collections.FXCollections.sort(screenshotMessageController.getScreenshotMessageList());
                System.out.println("-----------------------------------------------------");
                for (ScreenshotMessage s : list) {
                    System.out.println("   " + s.getSentTimeString() + " " + s.getIsAccepted());
                }
                System.out.println("-----------------------------------------------------");
                try {
                    if (messagesListView.getItems() != null) {
                        messagesListView.getItems().clear();
                    }
                    messagesListView.setItems(list);
                    messagesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            tabScreenshotListViewSelectedItem = (ScreenshotMessage) messagesListView.getSelectionModel().getSelectedItem();
                            System.out.println("Selected item: " + tabScreenshotListViewSelectedItem);

                            messagesOpenButton.setDisable(false);
                            messagesDeleteButton.setDisable(false);
                            tabScreenshotListViewSelectedItem = (ScreenshotMessage) messagesListView.getSelectionModel().getSelectedItem();
                            System.out.println("Selected item: " + tabScreenshotListViewSelectedItem);
                            imageView.setVisible(true);

                            imageView.setCache(true);
                            imageView.setCacheHint(CacheHint.SPEED);

                            messagesPreviewLabel.setVisible(false);
                            Image image = new Image("file:\\" + tabScreenshotListViewSelectedItem.getPath().getAbsoluteFile());
                            //System.out.println("file:\\" + screenshotListViewSelectedItem.getPath().getAbsoluteFile());
                            if (image.getHeight() > 1600 || image.getWidth() > 1600) {
                                image = new Image("file:\\" + tabScreenshotListViewSelectedItem.getPath().getAbsoluteFile(), 1600, 1600, true, true);
                            }
                            messagesOriginalImageHeight = image.getHeight();
                            messagesOriginalImageWidth = image.getWidth();

                            messagesImageStackPaneWidth = ((StackPane) (imageView.getParent())).getWidth() - 20;
                            messagesImageStackPaneHeight = ((StackPane) (imageView.getParent())).getHeight() - 20;

                            //((StackPane)(imageView.getParent())).setStyle("-fx-background-color: red;");
                            if (messagesOriginalImageWidth / messagesImageStackPaneWidth > messagesOriginalImageHeight / messagesImageStackPaneHeight) {
                                if (image.getWidth() < messagesImageStackPaneWidth) {
                                    //System.out.println("FIT PO SIRINI SLIKE: " + image.getWidth());
                                    imageView.setFitWidth(image.getWidth());
                                } else {
                                    //System.out.println("FIT PO SIRINI PANELA: " + imageStackPaneWidth);
                                    imageView.setFitWidth(messagesImageStackPaneWidth);
                                }
                            } else if (image.getHeight() < messagesImageStackPaneHeight) {
                                //System.out.println("FIT PO DUZINI SLIKE: " + image.getHeight());
                                imageView.setFitHeight(image.getHeight());
                            } else {
                                //System.out.println("FIT PO DUZINI PANELA: " + imageStackPaneHeight);
                                imageView.setFitHeight(messagesImageStackPaneHeight);
                            }
                            imageView.setImage(image);
                            System.out.println("Setting image to imageView done.");
                        }

                    });

                    ((AnchorPane) (imageView.getParent().getParent())).widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldStackPaneWidth, Number newStackPaneWidth) {
                            messagesImageStackPaneWidth = newStackPaneWidth.doubleValue();
                            if (messagesOriginalImageWidth >= newStackPaneWidth.doubleValue() & messagesResizedImageHeight <= messagesImageStackPaneHeight) {
                                imageView.setFitWidth(newStackPaneWidth.doubleValue() - 40);
                                messagesResizedImageWidth = newStackPaneWidth.doubleValue() - 20;
                                messagesResizedImageHeight = 0 / (messagesOriginalImageWidth / (newStackPaneWidth.doubleValue() - 20));
                            }
                        }
                    });
                    ((AnchorPane) (imageView.getParent().getParent())).heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldStackPaneHeight, Number newStackPaneHeight) {
                            messagesImageStackPaneHeight = newStackPaneHeight.doubleValue();
                            if (messagesOriginalImageHeight >= newStackPaneHeight.doubleValue() & messagesResizedImageWidth <= messagesImageStackPaneWidth) {
                                imageView.setFitHeight(newStackPaneHeight.doubleValue() - 40);
                                messagesResizedImageHeight = newStackPaneHeight.doubleValue() - 20;
                                messagesResizedImageWidth = 0 / (messagesOriginalImageHeight / (newStackPaneHeight.doubleValue() - 20));
                            }
                        }
                    });

                } catch (Exception ex) {

                }
            }
        });

    }
}
