/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import messages.MessageController;
import screenshots.ScreenshotMessage;
import screenshots.ScreenshotMessageController;
import screenshots.ScreenshotNumberTest;
import treeviewclasses.FileSystemTree;
import virtualalbums.*;

/**
 *
 * @author Asus
 */
public class FXMLDocumentController implements Initializable {

    private static FileSystemTree fst = null;
    private static VirtualAlbumsController virtualAlbumsController = null;
    private static boolean isFirstTime = true;
    private static boolean isMainFormActive = true;
    private static Stage app_stage;
    private String folderName;
    private static String selectedPath;
    private static String selectedAlbum;
    private boolean isMoveToAlbumButtonPressed = false;
    private static String type;
    private static String selectedImageName;
    private List<File> choosenFiles;
    private static Socket mySocket;
    private static BufferedReader in;
    private static PrintWriter out;
    private static MessageListener listener;
    private static boolean isRegistrationQuit = false;
    private static boolean isLoginQuit = false;
    private static boolean isMainFormQuit = false;
    private static String myUsername;
    private static int numOfScreenshotSent;
    private static int numOfScreenshotReceive;
    private static ScreenshotMessageController screenshotMessageController;
    private static ScreenshotMessage screenshotMessage;
    private static Image iconImage;
    private static ObservableList<String> onlineUsers = FXCollections.observableArrayList();
    //private static ObservableList<ScreenshotMessage> messageList = 
    private static boolean isRequestPopUpRun = false;
    private static ScreenshotMessage screenshotListViewSelectedItem;
    private static boolean isTimeToSetListener = false;
    private static boolean isLoginEnd = false;
    private static int numOfInitialize = 1;
    private static String username = null;
    private static MessageController messageController;

    @FXML
    private TreeView explorerTreeView;

    @FXML
    private ImageView explorerImgView;

    @FXML
    private TextField explorerPathTextField;

    @FXML
    private Button explorerBtn2;

    @FXML
    private Label explorerImageLabel;

    @FXML
    private TextField folderNameTextField;

    @FXML
    private Button createButton;

    @FXML
    private Button explorerBtn1;

    @FXML
    private TextField explorerRenameTextField;

    @FXML
    private Button explorerRenameBtn;

    @FXML
    private Button exitFullscreenBtn;

    @FXML
    private ImageView fullscreenImageView;

    @FXML
    private TextField albumNameTextField;

    @FXML
    private TextField albumDescriptionTextField;

    @FXML
    private Button createAlbum;

    @FXML
    private Button createAlbumPopUpButton;

    @FXML
    private ChoiceBox moveImagePopUpChoiceBox;

    @FXML
    private Button explorerMoveImageButton;

    @FXML
    private Label albumsNavigationLabel;

    @FXML
    private Label albumNameLabel;

    @FXML
    private Label albumDescriptionLabel;

    @FXML
    private Label albumOrImageNameLabel;

    @FXML
    private Label descriptionTempLabel;

    @FXML
    private Button renameAlbumPopUpButton;

    @FXML
    private TextField renameAlbumPopUpTextField;

    @FXML
    private FlowPane albumsFlowPane;

    @FXML
    private FlowPane imagesFlowPane;

    @FXML
    private ChoiceBox albumsImportPopUpChoiseBox;

    @FXML
    private Button albumsImportPopUpAddToAlbumButton;

    @FXML
    private Button albumsImportPopUpBrowseButton;

    @FXML
    private Label albumsImportPopUpChoosenNumLabel;

    @FXML
    private ScrollPane albumsScrollPane;

    @FXML
    private ScrollPane imagesScrollPane;

    @FXML
    public Button validationScreenValidateButton;

    @FXML
    private Button validationScreenQuitButton;

    @FXML
    private TextField validationScreenTextField;

    @FXML
    private Button loginScreenQuitButton;

    @FXML
    private Button loginScreenLoginButton;

    @FXML
    private TextField loginScreenTextField;

    @FXML
    private Label validationScreenWarningLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button screenshotPopUpQuitButton;

    @FXML
    private Button screenshotPopUpSendButton;

    @FXML
    private ChoiceBox screenshotPopUpChoiseBox;

    @FXML
    private ImageView screenshotPopUpImageView;

    @FXML
    private Button newScreenshotReceived;

    @FXML
    private Button screenshotRequestDeclineButton;

    @FXML
    private Button screenshotRequestAcceptButton;

    @FXML
    private Button screenshotRequestCancelButton;

    @FXML
    private ListView screenshotRequestListView;

    @FXML
    private ListView messagesListView;

    @FXML
    private Button explorerBtn3;

    @FXML
    private Button explorerBtn4;

    @FXML
    private Button explorerBtn7;

    @FXML
    private Button explorerBtn8;

    @FXML
    private Button explorerBtn5;

    @FXML
    private Button explorerBtn6;
    
    @FXML private ImageView albumsImageView;
    
    public static void setIsLoginEnd() {
        isLoginEnd = true;
    }

    ////////////////////////  APPLICATION WINDOW BUTTONS  /////////////////////////
    @FXML
    private Button applicationWindowCloseButton;

    @FXML
    private void applicationWindowCloseButtonAction() {
        isMainFormQuit = true;
        for (ScreenshotMessage msg : screenshotMessageController.getScreenshotMessageList()) {
            if (myUsername.equals(msg.getReceiver())) {
                msg.setIsAccepted(-1);
            }
        }
        ScreenshotNumberTest test = new ScreenshotNumberTest(numOfScreenshotSent, numOfScreenshotReceive);
        System.out.println("numSent: " + numOfScreenshotSent + " -- numReceive: " + numOfScreenshotReceive);
        test.serializeScreenshotNumbers();
        screenshotMessageController.serializeScreenshotMessageList();
        virtualAlbumsController.serializeAllAlbums(); 
        app_stage = (Stage) explorerBtn1.getScene().getWindow();
        app_stage.close();
        try {
            out.println("QUIT#" + myUsername);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private javafx.scene.control.Button applicationWindowMinimizeButton;

    @FXML
    private void applicationWindowMinimizeButtonAction() {
        Stage stage = (Stage) applicationWindowCloseButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private javafx.scene.control.ToggleButton applicationWindowRestoreButton;

    @FXML
    private void applicationWindowRestoreButtonAction() {
        if (isMaximized) {
            //isMaximized = false;
            applicationWindowRestoreDown();
        } else if (!isMaximized) {
            //isMaximized = true;
            applicationWindowRestoreUp();
        }
    }

    private double restoreUpWidth;
    private double restoreUpHeight;
    private double restoreUpXPosition;
    private double restoreUpYPosition;
    private boolean isMaximized;

    private void applicationWindowRestoreDown() {
        if (isMaximized) {
            isMaximized = false;
            applicationWindowRestoreButton.setSelected(false);
            Screen screen = Screen.getPrimary();
            javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
            Stage stage = (Stage) applicationWindowCloseButton.getScene().getWindow();
            stage.setX(restoreUpXPosition);
            stage.setY(restoreUpYPosition);
            stage.setWidth(restoreUpWidth);
            stage.setHeight(restoreUpHeight);
        }
    }

    private void applicationWindowRestoreUp() {
        if (!isMaximized) {
            isMaximized = true;
            applicationWindowRestoreButton.setSelected(true);
            //applicationWindowMaximizeButton.
            /*// get a handle to the stage
            Stage stage = (Stage) applicationWindowCloseButton.getScene().getWindow();
            //maximise the stage
            stage.setMaximized(true);*/
            Screen screen = Screen.getPrimary();
            javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
            Stage stage = (Stage) applicationWindowCloseButton.getScene().getWindow();
            //storing width and height and relative window position
            restoreUpWidth = stage.getWidth();
            restoreUpHeight = stage.getHeight();
            restoreUpXPosition = stage.getX();
            restoreUpYPosition = stage.getY();
            //setting new values
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
        }
    }

    @FXML
    private javafx.scene.layout.AnchorPane applicationWindowAnchorPane;

    @FXML
    private void applicationWindowAnchorPaneAction(MouseEvent click) {
        if (click.getClickCount() == 2) {
            if (isMaximized) {
                //isMaximized = false;
                applicationWindowRestoreDown();
            } else if (!isMaximized) {
                //isMaximized = true;
                applicationWindowRestoreUp();
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////  SEND SCREENSHOT MESSAGE  /////////////////////////
    @FXML
    void sendScreenshotButtonAction(ActionEvent event) {
        try {
            System.out.println("\nCREATING SCREENSHOT...");
            Robot ro = new Robot();
            Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            BufferedImage im = ro.createScreenCapture(rect);
            System.out.println("SCREENSHOT CREATED!");
            File folder = new File("SentScreenshots");
            if (!folder.exists()) {
                System.out.println("CREATE FOLDER SentScreenshots");
                folder.mkdir();
            }
            File screenShotFile = new File("SentScreenshots" + File.separator + "screenshotMessage_" + numOfScreenshotSent + ".jpg");
            numOfScreenshotSent++;
            ImageIO.write(im, "jpg", screenShotFile);
            System.out.println("SCREENSHOT SAVE LIKE JPG");
            screenshotMessage = new ScreenshotMessage(screenShotFile);
            screenshotMessage.setSender(myUsername);
            screenshotMessage.setIsISentThisMessage(true);
            /*System.out.println("ADD TO SCREENSHOT LIST: " + screenshotMessage);
            screenshotMessageController.addScreenshotMessage(screenshotMessage);*/

            iconImage = new Image(new File(screenShotFile.getPath()).toURI().toString(), 250, 200, true, true, true);
            System.out.println("ICON IMAGE CREATED.");

            System.out.println("SENDING GET LIST REQUEST...");
            out.println("screenshot#getList#" + myUsername);
            System.out.println("SENT REQUEST: " + "screenshot#getList#" + myUsername);
            //String destinationUsername = "";

            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLScreenshotPopUpForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void screenshotPopUpSendButtonAction(ActionEvent event) {
        try {
            String destinaionUsername = (String) screenshotPopUpChoiseBox.getValue();
            if (destinaionUsername != null && !"".equals(destinaionUsername)) {
                //System.out.println("I SET RECEIVER: " + destinaionUsername);
                screenshotMessage.setReceiver(destinaionUsername);
                //LocalDate date = LocalDate.now();
                long date = System.currentTimeMillis();
                System.out.println("MILLIS:" + date);
                screenshotMessage.setSentTimeString("" + date);

                System.out.println("\nSCREENSHOT MESSAGE ATRIBUTES:");
                System.out.println("********************************************************");
                System.out.println("Sender*: " + screenshotMessage.getSender());
                System.out.println("Receiver*: " + screenshotMessage.getReceiver());
                System.out.println("Is I sent*: " + screenshotMessage.getIsISentThisMessage());
                System.out.println("Sent time string*: " + screenshotMessage.getSentTimeString());
                System.out.println("Is accepted: " + screenshotMessage.getIsAccepted());
                System.out.println("********************************************************\n");

                System.out.println("SENDING TO SERVER...");
                out.println(destinaionUsername + "#" + date);
                System.out.println("SENT: " + destinaionUsername + "#" + date);
                // slanje ka serveru //
                long duzina = screenshotMessage.getPath().length();
                System.out.println("\nSENDING DUZINA TO SERVER...");
                out.println(duzina);
                System.out.println("SENT: " + duzina);
                /*byte[] buffer = new byte[2 * 1024];
                InputStream fajl = new FileInputStream(screenshotMessage.getPath());
                int length = 0;
                OutputStream os = mySocket.getOutputStream();
                System.out.println("SENDING IMAGE TO SERVER...");
                System.out.println("**************************************************");
                while ((length = fajl.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                    System.out.println("Preostalo jos " + (duzina - length));
                }*/
                
                System.out.println("Opening new socket: ");
                Socket imageSocket = new Socket("localhost", 6066);
                
                byte[] buffer = new byte[2 * 1024];
                InputStream fajl = new FileInputStream(screenshotMessage.getPath());
                int length = 0;
                OutputStream os = imageSocket.getOutputStream();
                System.out.println("SENDING IMAGE TO SERVER...");
                System.out.println("**************************************************");
                while ((length = fajl.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                    System.out.println("Preostalo jos " + (duzina - length));
                }
                System.out.println("Closing streams and socket.");
                os.close();
                fajl.close();
                imageSocket.close();
                System.out.println("Closing streams and socket done.");
                
                System.out.println("Slanje zavrseno...");
                //fajl.close();
                System.out.println("***************************************************");
            }

            System.out.println("\nADD TO SCREENSHOT LIST: " + screenshotMessage);
            screenshotMessageController.addScreenshotMessage(screenshotMessage);
            System.out.println("*******************************************************");
            for (ScreenshotMessage s : screenshotMessageController.getScreenshotMessageList()) {
                System.out.println("Message: " + s);
            }
            System.out.println("*******************************************************");

            app_stage = (Stage) screenshotPopUpSendButton.getScene().getWindow();
            app_stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void screenshotPopUpQuitButtonAction(ActionEvent event) {
        out.println("SENDING_OF_SCREENSHOT_DECLINED#");
        app_stage = (Stage) screenshotPopUpSendButton.getScene().getWindow();
        app_stage.close();
    }

    @FXML
    void newScreenshotReceivedAction(ActionEvent event) throws IOException {
        isRequestPopUpRun = true;
        messageController.setMessagesListView(messagesListView);
        System.out.println("Received Button Clicked!");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLScreenshotRequestPopUpForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(explorerBtn1.getScene().getWindow());
        app_stage.show();

        isRequestPopUpRun = false;
    }

    public void setEnableButton() {
        Platform.runLater(() -> {
            newScreenshotReceived.setDisable(false);
        });

    }

    private ScreenshotMessage tabScreenshotListViewSelectedItem;
    
    @FXML
    void screenshotRequestAcceptButtonAction(ActionEvent event) {
        System.out.println("\nACEEPT BUTTON CLICKED");
        String sender = screenshotListViewSelectedItem.getSender();
        String receiver = screenshotListViewSelectedItem.getReceiver();
        String millis = screenshotListViewSelectedItem.getSentTimeString();
        System.out.println("SENDING ACCEPT RESPONSE");
        out.println("screenshotResponseAccept#" + sender + "#" + receiver + "#" + millis);
        System.out.println("SENT ACCEPT: " + "screenshotResponseAccept#" + sender + "#" + receiver + "#" + millis);
        ObservableList<ScreenshotMessage> msgs = screenshotMessageController.getScreenshotMessageList();
        ScreenshotMessage msg = null;
        for (ScreenshotMessage m : msgs) {
            if (m.getSender().equals(sender) && m.getReceiver().equals(receiver) && m.getSentTimeString().equals(millis)) {
                msg = m;
                break;
            }
        }
        msg.setIsAccepted(1);

        ObservableList<ScreenshotMessage> tempList = FXCollections.observableArrayList();
        for (ScreenshotMessage m : screenshotMessageController.getScreenshotMessageList()) {
            if (m.getIsAccepted() == 0 && m.getIsISentThisMessage() == false) {
                System.out.println("Message: " + m.getSender() + " - " + m.getReceiver() + " - " + m.getSentTimeString());
                tempList.add(m);
            }
        }
        screenshotRequestListView.setItems(tempList);
        screenshotRequestListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                screenshotListViewSelectedItem = (ScreenshotMessage) screenshotRequestListView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + screenshotListViewSelectedItem);
            }

        });
        
        messageController.setTabScreenshotListViewSelectedItem(tabScreenshotListViewSelectedItem);
        messageController.addMessagesToListView();
        /*ObservableList<ScreenshotMessage> tempList2 = FXCollections.observableArrayList();
        for (ScreenshotMessage m : screenshotMessageController.getScreenshotMessageList()) {
            if (m.getIsAccepted() == 1) {
                System.out.println("Message: " + m.getSender() + " - " + m.getReceiver() + " - " + m.getSentTimeString());
                tempList.add(m);
            }
        }
        System.out.println("zzzzzzzzzzzzzzz");
        messagesListView.setItems(tempList2);
        messagesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                tabScreenshotListViewSelectedItem = (ScreenshotMessage) messagesListView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + tabScreenshotListViewSelectedItem);
            }

        });*/
    }

    @FXML
    void screenshotRequestDeclineButtonAction(ActionEvent event) {
        System.out.println("\nDECLINE BUTTON CLICKED");
        screenshotListViewSelectedItem.setIsAccepted(-1);
        String sender = screenshotListViewSelectedItem.getSender();
        String receiver = screenshotListViewSelectedItem.getReceiver();
        String millis = screenshotListViewSelectedItem.getSentTimeString();
        System.out.println("SENDING ACCEPT RESPONSE");
        out.println("screenshotResponseDecline#" + sender + "#" + receiver + "#" + millis);

        ObservableList<ScreenshotMessage> msgs = screenshotMessageController.getScreenshotMessageList();
        ScreenshotMessage msg = null;
        for (ScreenshotMessage m : msgs) {
            if (m.getSender().equals(sender) && m.getReceiver().equals(receiver) && m.getSentTimeString().equals(millis)) {
                msg = m;
                break;
            }
        }
        msg.setIsAccepted(-1);

        ObservableList<ScreenshotMessage> tempList = FXCollections.observableArrayList();
        for (ScreenshotMessage m : screenshotMessageController.getScreenshotMessageList()) {
            if (m.getIsAccepted() == 0 && m.getIsISentThisMessage() == false) {
                System.out.println("Message: " + m.getSender() + " - " + m.getReceiver() + " - " + m.getSentTimeString());
                tempList.add(m);
            }
        }
        screenshotRequestListView.setItems(tempList);
        screenshotRequestListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                screenshotListViewSelectedItem = (ScreenshotMessage) screenshotRequestListView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + screenshotListViewSelectedItem);
            }

        });
    }

    @FXML
    void screenshotRequestCancelButtonAction(ActionEvent event) {
        app_stage = (Stage) screenshotRequestAcceptButton.getScene().getWindow();
        app_stage.close();
    }
    ////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////  LOGOUT  /////////////////////////////////
    @FXML
    void logoutButtonAction(ActionEvent event) {
        isMainFormQuit = true;
        for (ScreenshotMessage msg : screenshotMessageController.getScreenshotMessageList()) {
            if (myUsername.equals(msg.getReceiver())) {
                msg.setIsAccepted(-1);
            }
        }
        ScreenshotNumberTest test = new ScreenshotNumberTest(numOfScreenshotSent, numOfScreenshotReceive);
        System.out.println("numSent: " + numOfScreenshotSent + " -- numReceive: " + numOfScreenshotReceive);
        test.serializeScreenshotNumbers();
        virtualAlbumsController.serializeAllAlbums();
        screenshotMessageController.serializeScreenshotMessageList();
        app_stage = (Stage) explorerBtn1.getScene().getWindow();
        app_stage.close();
        try {
            out.println("QUIT#" + myUsername);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////  REGISTRATION  /////////////////////////////
    @FXML
    void validationScreenValidateButtonAction(ActionEvent event) throws IOException {
        app_stage = (Stage) validationScreenValidateButton.getScene().getWindow();
        String typedKey = validationScreenTextField.getText();
        boolean isOK = isValidKey(typedKey);
        if (isOK) {
            listener.setStage(app_stage);
            listener.setValidationScreenWarningLabel(validationScreenWarningLabel);
            out.println("registration#" + typedKey);  //send key to server

        } else {
            System.out.println("Bad key");
            validationScreenWarningLabel.setText("Invalid activation key.");
        }
    }

    public boolean isValidKey(String key) {
        if (16 != key.length()) {
            return false;
        }

        for (char ch : key.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    public void setWarningLabelText(String text) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                boolean test = true;
                while (test) {
                    System.out.println("Label");
                    if (validationScreenWarningLabel != null) {
                        validationScreenWarningLabel.setText(text);
                        test = false;
                    }
                }
            }
        });
        //validationScreenWarningLabel.setText(text);
    }
    ////////////////////////////////////////////////////////////////////////////

    /////////////////////////// VALIDATION QUIT  ///////////////////////////////
    @FXML
    void validationScreenQuitButtonAction(ActionEvent event) {
        isRegistrationQuit = true;
        app_stage = (Stage) validationScreenValidateButton.getScene().getWindow();
        app_stage.close();
        try {
            out.println("QUIT#nop");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean getIsRegistrationQuit() {
        return isRegistrationQuit;
    }
    ////////////////////////////////////////////////////////////////////////////

    ///////////////////////// LOGIN SCREEN LOGIN BUTTON ////////////////////////
    @FXML
    private Label loginScreenUsernameLabel;

    @FXML
    private Label usernameLabel;

    public void setUsernameLabel() {
        usernameLabel.setText(myUsername);
    }

    @FXML
    void loginScreenLoginButtonAction(ActionEvent event) throws IOException {
        String typedUsername = loginScreenTextField.getText();
        if (!"".equals(typedUsername)) {
            app_stage = (Stage) loginScreenLoginButton.getScene().getWindow();
            boolean isOK = isValidUsername(typedUsername);
            if (isOK) {
                myUsername = typedUsername;
                listener.setMyUsername(myUsername);
                listener.setStage(app_stage);
                listener.setLoginScreenWarningLabel(loginScreenUsernameLabel);
                out.println("login#" + typedUsername);  //send key to server
                username = typedUsername;

            } else {
                System.out.println("Name must contains minimum 4 letters or numbers!");
                loginScreenUsernameLabel.setText("Username must contain atleast 4 alphanumeric characters.");
                //validationScreenWarningLabel.setText("Key must contains letters and numbers (16 chars)!");
            }
        }
    }

    public boolean isValidUsername(String username) {
        if (username.length() < 4) {
            return false;
        }

        for (char ch : username.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                return false;
            }
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////////

    ///////////////////////////// LOGIN QUIT  //////////////////////////////////
    @FXML
    void loginScreenQuitButtonAction(ActionEvent event) {
        isLoginQuit = true;
        app_stage = (Stage) loginScreenLoginButton.getScene().getWindow();
        app_stage.close();
        try {
            out.println("QUIT#nop");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean getIsLoginQuit() {
        return isLoginQuit;
    }
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////  CREATE NEW FOLDER  ////////////////////////////
    @FXML
    void explorerBtn1Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Select: " + selectedPath);
        if (selectedPath != null) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLCreateNewFolderForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.setResizable(false);
            app_stage.setTitle("Create new folder");
            //app_stage.getIcons().clear();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.showAndWait();
        }
    }

    @FXML
    void createButtonAction(ActionEvent event) throws IOException {
        folderName = folderNameTextField.getText();

        if (folderName.equals("")) {
            System.out.println("Unesite naziv foldera!");
        } else {
            app_stage = (Stage) createButton.getScene().getWindow();
            app_stage.close();
            createNewFolder(folderName);
        }
    }

    @FXML
    private Button cancelButton;

    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {
        ((Stage) (cancelButton.getScene().getWindow())).close();
    }

    private void createNewFolder(String folderName) {
        String path = selectedPath;
        path += File.separator;
        path += folderName;
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        fst.addNewFolderNodeToTree(file);
    }
    ////////////////////////////////////////////////////////////////////////////

    @FXML private Label deleteDialogLabel;
    
    private static File fileToDelete;
    
    // Brisanje fajlova i foldera //
    @FXML
    void explorerBtn2Action(ActionEvent event) throws IOException {
        if (!explorerPathTextField.getText().equals("")) {
            fileToDelete = new File(explorerPathTextField.getText());
            System.out.println(fileToDelete);
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLDeleteDialog.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            //app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.setResizable(false);
            app_stage.setTitle("Delete " + explorerPathTextField.getText() + "?");
            //app_stage.getIcons().clear();
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.show();
            
            //deleteDialogLabel.setText("Are you sure you want to delete " + explorerPathTextField.getText() + "?");
            
            
           // deleteFile(file);
        }
    }
    
    @FXML private Button deleteDialogNoButton;
    @FXML private Button deleteDialogYesButton;
    
    @FXML
    void deleteDialogYesButtonAction(){
        deleteFile(fileToDelete);
        ((Stage)(deleteDialogNoButton.getScene().getWindow())).close();
    }
    
    @FXML private Button renameDialogCancelButton;
    @FXML
    void renameDialogCancelButtonAction(){
        ((Stage)(renameDialogCancelButton.getScene().getWindow())).close();
    }
    
    @FXML
    void deleteDialogNoButtonAction(){
        ((Stage)(deleteDialogNoButton.getScene().getWindow())).close();
    }
    
    @FXML private Button dialogAddToAlbumCancelButton;
    
    @FXML
    void dialogAddToAlbumCancelButtonAction(){
        ((Stage)(dialogAddToAlbumCancelButton.getScene().getWindow())).close();
    }
    
    

    public static void deleteFile(File file) {
        if (!file.isDirectory()) {
            file.delete();
        } else if (file.list().length == 0) {
            file.delete();
        } else {
            deleteNoEmptyFolder(file);
        }

        fst.removeOneNode(file);
    }

    public static boolean deleteNoEmptyFolder(File file) {
        File[] filesInFolder = file.listFiles();
        for (File f : filesInFolder) {
            if (f.isFile()) {
                f.delete();
                System.out.println("Deleted file: " + f.getPath());
            } else if (f.isDirectory()) {
                if (f.list().length == 0) {
                    f.delete();
                    System.out.println("Deleted empty folder: " + f.getPath());
                } else {
                    deleteNoEmptyFolder(f);
                }
            }
        }
        return (file.delete());
    }

    // Rename file or folder //
    @FXML
    void explorerBtn3Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if (selectedPath != null) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLRenameFileForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setResizable(false);
            app_stage.setTitle("Rename " + explorerPathTextField.getText());
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.showAndWait();
        }
    }

    @FXML
    void explorerRenameBtnAction(ActionEvent event) throws IOException {
        String newName = explorerRenameTextField.getText();

        if ("".equals(newName)) {
            System.out.println("Unesite naziv novog fajla!");
        } else {
            app_stage = (Stage) explorerRenameBtn.getScene().getWindow();
            app_stage.close();
            int index = selectedPath.lastIndexOf("\\");
            String tempPath = selectedPath.substring(0, index);
            tempPath += File.separator;
            tempPath += newName;
            System.out.println("Old file: " + selectedPath);
            System.out.println("New file: " + tempPath);
            renameFile(new File(selectedPath), new File(tempPath));
        }
    }

    public void renameFile(File oldFile, File newFile) {
        String extension = null;
        if(oldFile.getName().endsWith(".jpg")){
            extension = ".jpg";
        }
        else if(oldFile.getName().endsWith(".JPG")){
            extension = ".JPG";
        }
        else if(oldFile.getName().endsWith(".png")){
            extension = ".png";
        }
        else if(oldFile.getName().endsWith(".PNG")){
            extension = ".PNG";
        }
        else if(oldFile.getName().endsWith(".bnp")){
            extension = ".bnp";
        }
        else if(oldFile.getName().endsWith(".BNP")){
            extension = ".BNP";
        }
        else if(oldFile.getName().endsWith(".gif")){
            extension = ".gif";
        }
        else if(oldFile.getName().endsWith(".GIF")){
            extension = ".GIF";
        }
        
        try {
            if (newFile.exists()) {
                throw new java.io.IOException("file exists");
            }
        } catch (Exception ex) {
            System.out.println("Naziv vec postoji!");
        }

        // Rename file (or directory)
        boolean success = false;
        File newFileWithExtension = null;
        if(extension != null){
            String newPath = newFile.getPath();
            newPath += extension;
            newFileWithExtension = new File(newPath);
            System.out.println("Rename: " + newFileWithExtension);
            success = oldFile.renameTo(newFileWithExtension);
        } else {
            success = oldFile.renameTo(newFile);
            System.out.println("Rename: " + newFile);
        }
        if (!success) {
            // File was not successfully renamed
            System.out.println("File was not successfully renamed!");
        } else if (extension != null) {
            fst.exchangeTwoNodes(oldFile, newFileWithExtension);
        } else {
            fst.exchangeTwoNodes(oldFile, newFile);
        }
    }

    // Copy file or folder //
    @FXML
    void explorerBtn5Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if (selectedPath != null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Where you want to save file");
            dirChooser.setInitialDirectory(new File(File.separator));
            File choosenFolder = dirChooser.showDialog(app_stage);
            if (choosenFolder != null) {
                System.out.println(choosenFolder.getPath());
                if (new File(selectedPath).isDirectory()) {
                    File srcDir = new File(selectedPath);
                    File destDir = new File(choosenFolder.getPath() + File.separator + srcDir.getName());
                    copyFolder(srcDir, destDir);
                } else {
                    File srcDir = new File(selectedPath);
                    File destDir = new File(choosenFolder.getPath() + File.separator + srcDir.getName());
                    copyFile(srcDir, destDir);
                }
            }
        }
    }

    public static void copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String files[] = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        } else {
            try {
                System.out.println("This is image: " + source);
                copyFile(source, destination);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    // MOVE ACTION //
    @FXML
    void explorerBtn6Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if (selectedPath != null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Where you want to move file");
            dirChooser.setInitialDirectory(new File("\\"));
            File choosenFolder = dirChooser.showDialog(app_stage);
            //System.out.println(choosenFolder.getPath());
            if (choosenFolder != null) {
                if (new File(selectedPath).isDirectory()) {
                    File srcDir = new File(selectedPath);
                    File destDir = new File(choosenFolder.getPath() + File.separator + srcDir.getName());
                    moveFolder(srcDir, destDir);
                    File file = new File(explorerPathTextField.getText());
                    //deleteFile(file);
                } else {
                    File srcFile = new File(selectedPath);
                    File destDir = new File(choosenFolder.getPath() + File.separator + srcFile.getName());
                    moveFile(srcFile, destDir);
                    //deleteFile(srcFile);
                }
            }
        }
    }

    public static void moveFolder(File source, File destination) throws IOException {
        copyFolder(source, destination);
        deleteFile(source);
    }

    private static void moveFile(File source, File dest) throws IOException {
        copyFile(source, dest);
        deleteFile(source);
    }

    // for OPEN acion //
    @FXML
    void explorerBtn4Action(ActionEvent event) {
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);
        if (selectedPath != null) {
            if (!new File(selectedPath).isDirectory()) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File(selectedPath);
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    ///////////////////  ADD IMAGE TO ALBUM FROM EXPLORER  //////////////////////
    @FXML
    void explorerBtn7Action(ActionEvent event) throws IOException {
        isMoveToAlbumButtonPressed = true;
        selectedPath = fst.getSelectedPath();
        System.out.println("Selected: " + selectedPath);

        if (selectedPath != null && new File(selectedPath).isFile()) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLExplorerMoveToAlbum.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setResizable(false);
            app_stage.setTitle("Add to album");
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.show();
        }

    }

    @FXML
    private void explorerMoveImageButtonAction(ActionEvent event) {
        String nameOfAlbum = (String) moveImagePopUpChoiceBox.getValue();
        if (nameOfAlbum != null && !"".equals(nameOfAlbum)) {
            System.out.println("Choosen value: " + nameOfAlbum);
            AlbumImage image = new AlbumImage(new File(selectedPath).getName(), new File(selectedPath));
            VirtualAlbum album = virtualAlbumsController.getAlbumForString(nameOfAlbum);
            album.addImage(image);

            app_stage = (Stage) explorerMoveImageButton.getScene().getWindow();
            app_stage.close();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////

    // FULLSCREEN Action //
    @FXML
    void explorerBtn8Action(ActionEvent event) throws IOException {
        selectedPath = fst.getSelectedPath();
        if (selectedPath != null && new File(selectedPath).isFile()) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(explorerBtn1.getScene().getWindow());
            HBox dialogHbox = new HBox(20);
            dialogHbox.setAlignment(Pos.CENTER);
            Image image = new Image("file:\\" + selectedPath.toString());
            ImageView iv = new ImageView();

            dialogHbox.getChildren().add(iv);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            dialog.setX(bounds.getMinX());
            dialog.setY(bounds.getMinY());
            dialog.setWidth(bounds.getWidth());
            dialog.setHeight(bounds.getHeight());

            if (image.getHeight() > bounds.getHeight() || image.getWidth() > bounds.getWidth()) {
                image = new Image(("file:\\" + selectedPath.toString()), bounds.getWidth(), bounds.getHeight(), true, true);
            }

            iv.setImage(image);

            if (image.getWidth() / bounds.getWidth() > image.getHeight() / bounds.getHeight()) {
                if (image.getWidth() < bounds.getWidth()) {
                    iv.setFitWidth(image.getWidth());
                } else {
                    iv.setFitWidth(bounds.getWidth());
                }
            } else if (image.getHeight() < bounds.getHeight()) {
                iv.setFitHeight(image.getHeight());
            } else {
                iv.setFitHeight(bounds.getHeight());
            }

            Scene dialogScene = new Scene(dialogHbox, image.getWidth(), image.getHeight());
            dialog.setTitle(selectedPath);
            dialog.getIcons().add(new Image("icons/explorerTreeViewIcons/imagePlaceholder.png"));
            dialog.setResizable(false);
            dialog.setScene(dialogScene);
            dialog.show();
            System.out.println("file:\\" + selectedPath.toString());
            System.out.println("image.getWidth(): " + image.getWidth());
            System.out.println("image.getHeight(): " + image.getHeight());

        } else {
            //System.out.println("Please select a file to preview.");
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(explorerBtn1.getScene().getWindow());
            HBox dialogHbox = new HBox(20);
            dialogHbox.setAlignment(Pos.CENTER);
            Image image = new Image("icons/warningSign.png");
            ImageView iv = new ImageView();
            iv.setImage(image);
            dialogHbox.getChildren().add(iv);
            dialogHbox.getChildren().add(new Text("Please select an image to open in fullscreen."));
            Scene dialogScene = new Scene(dialogHbox, 400, 100);
            dialog.setTitle("Select an image!");
            dialog.getIcons().add(image);
            dialog.setResizable(false);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }

    @FXML
    void exitFullscreenBtnAction(ActionEvent event) {
        app_stage = (Stage) exitFullscreenBtn.getScene().getWindow();
        app_stage.close();
    }

    /**
     * **************************** ALBUM TAB
     * **********************************
     */
    ///////////////////////  CREATE NEW VIRTUAL ALBUM  /////////////////////////
    
    @FXML private Button dialogNewAlbumCancelButton;
    
    @FXML
    private void dialogNewAlbumCancelButtonAction(ActionEvent event){
        app_stage = (Stage) dialogNewAlbumCancelButton.getScene().getWindow();
        app_stage.close();
    }
    
    @FXML
    void albumsNewAlbumButtonAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLNewAlbumForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setResizable(false);
        app_stage.setTitle("Create new album");
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(explorerBtn1.getScene().getWindow());
        app_stage.showAndWait();

    }

    @FXML
    void createAlbumPopUpButtonAction(ActionEvent event) {
        String albumName = albumNameTextField.getText();
        String albumDescription = albumDescriptionTextField.getText();

        if (!albumName.equals("")) {
            if (virtualAlbumsController.getVirtualAlbumList().size() != 0) {
                boolean isValidName = virtualAlbumsController.isAlbumNameValid(albumName);
                if (!isValidName) {
                    //Ako ime vec postoji//
                } else {
                    VirtualAlbum album = new VirtualAlbum(albumName, albumDescription);
                    virtualAlbumsController.addVirtualAlbum(album);
                    virtualAlbumsController.addAlbumToAlbumsFlowPane(album);
                    app_stage = (Stage) createAlbumPopUpButton.getScene().getWindow();
                    app_stage.close();
                }
            } else {
                VirtualAlbum album = new VirtualAlbum(albumName, albumDescription);
                virtualAlbumsController.addVirtualAlbum(album);
                virtualAlbumsController.addAlbumToAlbumsFlowPane(album);

                app_stage = (Stage) createAlbumPopUpButton.getScene().getWindow();
                app_stage.close();
            }
        } else {
            //Ako nije uneseno ime//
        }
    }
    ///////////////////////////////////////////////////////////////////////////////

    /////////////////////////// ALBUM BACK BUTTON /////////////////////////////
    @FXML
    void albumsBackButtonAction(ActionEvent event) {
        albumsImport.setDisable(false);
        albumsNewAlbum.setDisable(false);
        albumsDelete.setDisable(true);
        albumsRename.setDisable(true);
        albumsOpen.setDisable(true);
        albumsCopy.setDisable(true);
        albumsMove.setDisable(true);
        albumsFullscreen.setDisable(true);
        albumsBackButton.setDisable(true);
        albumsImageView.setVisible(false);
        
        albumsNavigationLabel.setText("Albums");
        albumOrImageNameLabel.setText("Album name:");
        albumNameLabel.setText("");
        descriptionTempLabel.setText("Description:");
        albumsFlowPane.setVisible(true);
        albumsScrollPane.setVisible(true);
        imagesFlowPane.setVisible(false);
        imagesScrollPane.setVisible(false);
        albumsFlowPane.requestFocus();
    }
    ///////////////////////////////////////////////////////////////////////////

    ////////////////////////// DELETE ALBUM /////////////////////////////////
    private static String albumName = null;
    private static boolean isImage = false;
    private static String albumN = null;
    private static String imageN = null;
    
    @FXML
    void albumsDeleteAction(ActionEvent event) throws IOException {
        albumName = albumNameLabel.getText();
        if (albumOrImageNameLabel.getText().equals("Album name:")) {
            // brisanje albuma //
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAlbumsDeleteDialog.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setResizable(false);
            app_stage.setTitle("Delete album");
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.showAndWait();
            
        } else {
            // brisanje slike iz albuma //
            isImage = true;
            albumN = albumsNavigationLabel.getText();
            imageN = albumNameLabel.getText();
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAlbumsDeleteDialog.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setResizable(false);
            app_stage.setTitle("Delete album");
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.showAndWait();
            
        }

    }
    
    @FXML private Button albumsDeleteDialogNoButton;
    @FXML private Button albumsDeleteDialogYesButton;
    
    @FXML
    void albumsDeleteDialogYesButtonAction(){
        if(!isImage){
            virtualAlbumsController.removeVirtualAlbum(albumName);
        }
        else{
            virtualAlbumsController.removeImageFromAlbum(albumN, imageN);
            isImage = false;
        }
        ((Stage)(albumsDeleteDialogNoButton.getScene().getWindow())).close();
    }
    
    @FXML
    void albumsDeleteDialogNoButtonAction(){
        ((Stage)(albumsDeleteDialogNoButton.getScene().getWindow())).close();
    }
    
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////// OPEN ALBUM OR IMAGE BUTTON  /////////////////////////
    @FXML
    void albumsOpenAction(ActionEvent event) {
        String type = albumsNavigationLabel.getText();
        if ("Albums".equals(type)) {
            ///// Selektovan je album //////
            String albumName = albumNameLabel.getText();
            if (!"".equals(albumName)) {
                virtualAlbumsController.openAlbumOrImage(albumName, null);
            } else {
                //Ako nije selektovan ni jedan album//
            }
        } else {
            ////// Selektovana je slika //////
            String imageName = albumNameLabel.getText();
            if (!"".equals(imageName)) {
                String parentFolder = albumsNavigationLabel.getText();
                virtualAlbumsController.openAlbumOrImage(parentFolder, imageName);
            } else {
                //Ako nije selektovana ni jedna slika//
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////

    ////////////////////////// RENAME ALBUM OR IMAGE  //////////////////////////
    @FXML
    void albumsRenameAction(ActionEvent event) throws IOException {
        type = albumsNavigationLabel.getText();
        selectedImageName = albumNameLabel.getText();
        if ("Albums".equals(type)) {
            // Selektovan je album //
            selectedAlbum = albumNameLabel.getText();
            System.out.println("Selected: " + selectedAlbum);
            if (!"".equals(selectedAlbum)) {
                Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLRenameAlbumForm.fxml"));
                Scene create_folder_scene = new Scene(home_page_parent);
                app_stage = new Stage();
                app_stage.setResizable(false);
                app_stage.setTitle("Rename album");
                app_stage.setScene(create_folder_scene);
                app_stage.initModality(Modality.APPLICATION_MODAL);
                app_stage.initOwner(explorerBtn1.getScene().getWindow());
                app_stage.show();
            }
        } else {
            // Selektovana je slika //
            selectedAlbum = type;
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLRenameAlbumForm.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setResizable(false);
            app_stage.setTitle("Rename album");
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.show();
        }
    }

    @FXML
    void renameAlbumPopUpButtonAction(ActionEvent event) {
        String newName = renameAlbumPopUpTextField.getText();
        if ("Albums".equals(type)) {
            if (!newName.equals("")) {
                virtualAlbumsController.renameAlbumOrImage(selectedAlbum, null, newName);
                app_stage = (Stage) renameAlbumPopUpButton.getScene().getWindow();
                app_stage.close();
            } else {
                //Potrebno ponovo unijeti naziv albuma //
            }
        } else if (!newName.equals("")) {
            //String selectedImageName = albumNameLabel.getText();
            virtualAlbumsController.renameAlbumOrImage(selectedAlbum, selectedImageName, newName);
            app_stage = (Stage) renameAlbumPopUpButton.getScene().getWindow();
            app_stage.close();
        } else {
            //Potrebno ponovo unijeti naziv albuma //
        }
    }
    
    @FXML Button renameAlbumPopUpCancelButton;
    
    @FXML
    private void renameAlbumPopUpCancelButtonAction(ActionEvent event) {
        ((Stage)(renameAlbumPopUpCancelButton.getScene().getWindow())).close();
    }
    ////////////////////////////////////////////////////////////////////////////

    //////////////////////// ALBUMS IMPORT BUTTON /////////////////////////////
    
    @FXML private Button albumsImportPopUpCancelButton;
    
    @FXML
    private void albumsImportPopUpCancelButtonAction(ActionEvent event){
        ((Stage)(albumsImportPopUpCancelButton.getScene().getWindow())).close();
    }
    
    @FXML
    void albumsImportButtonAction(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAlbumsImportPopUpForm.fxml"));
        Scene create_folder_scene = new Scene(home_page_parent);
        app_stage = new Stage();
        app_stage.setResizable(false);
        app_stage.setTitle("Create new folder");
        app_stage.setScene(create_folder_scene);
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.initOwner(explorerBtn1.getScene().getWindow());
        app_stage.showAndWait();

    }

    @FXML
    void albumsImportPopUpBrowseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose images...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png", "*.JPG", "*.PNG"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        choosenFiles = fileChooser.showOpenMultipleDialog(app_stage);
        if (choosenFiles != null) {
            albumsImportPopUpChoosenNumLabel.setText(choosenFiles.size() + " images to import.");
        } else {
            albumsImportPopUpChoosenNumLabel.setText("0 images to import.");
        }
    }

    @FXML
    void albumsImportPopUpAddToAlbumButtonAction(ActionEvent event) {
        String nameOfAlbum = (String) albumsImportPopUpChoiseBox.getValue();
        if (nameOfAlbum != null && !"".equals(nameOfAlbum) && choosenFiles != null) {
            System.out.println("Choosen value: " + nameOfAlbum);
            for (File f : choosenFiles) {
                AlbumImage image = new AlbumImage(f.getName(), f);
                VirtualAlbum album = virtualAlbumsController.getAlbumForString(nameOfAlbum);
                album.addImage(image);
            }

            app_stage = (Stage) albumsImportPopUpAddToAlbumButton.getScene().getWindow();
            app_stage.close();
        }

    }
    ///////////////////////////////////////////////////////////////////////////

    
    //////////////////////////  COPY VIRTUAL ALBUMS  ///////////////////////////
    @FXML private Button albumsCopyAction;
    
    private static VirtualAlbum albumToCopy = null;
    
    @FXML
    private void albumsCopyAction(ActionEvent event) throws IOException{
        String type = albumsNavigationLabel.getText();
        if ("Albums".equals(type)) {
            ///// Selektovan je album //////
            String tempAlbum = albumNameLabel.getText();
            System.out.println("ALBUM: " + tempAlbum);
            albumToCopy = virtualAlbumsController.getAlbumForString(tempAlbum);
            System.out.println("ALBUM OBJECT: " + albumToCopy);
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLAlbumsCopyDialog.fxml"));
            Scene create_folder_scene = new Scene(home_page_parent);
            app_stage = new Stage();
            app_stage.setResizable(false);
            app_stage.setTitle("Copy album");
            app_stage.setScene(create_folder_scene);
            app_stage.initModality(Modality.APPLICATION_MODAL);
            app_stage.initOwner(explorerBtn1.getScene().getWindow());
            app_stage.showAndWait();
        } else {
            ////// Selektovana je slika //////
            
        }
    }
    
    @FXML
    private Button albumsCopyDialogCancelButton;

    @FXML
    private Button albumsCopyDialogCopyButton;

    @FXML
    private TextField albumsCopyDialogTextField;

    @FXML
    void albumsCopyDialogCopyButtonAction(ActionEvent event) {
        String albumName = albumsCopyDialogTextField.getText();
        System.out.println("NEW ALBUM NAME: " + albumName);

        if (virtualAlbumsController.getVirtualAlbumList().size() != 0) {
            boolean isValidName = virtualAlbumsController.isAlbumNameValid(albumName);
            if (!isValidName) {
                //Ako ime vec postoji//
            } else {
                VirtualAlbum album = new VirtualAlbum(albumToCopy);
                album.setName(albumName);
                virtualAlbumsController.addVirtualAlbum(album);
                virtualAlbumsController.addAlbumToAlbumsFlowPane(album);
                app_stage = (Stage) albumsCopyDialogCancelButton.getScene().getWindow();
                app_stage.close();
            }
        } else {
            VirtualAlbum album = new VirtualAlbum(albumToCopy);
            album.setName(albumName);
            virtualAlbumsController.addVirtualAlbum(album);
            virtualAlbumsController.addAlbumToAlbumsFlowPane(album);

            app_stage = (Stage) albumsCopyDialogCancelButton.getScene().getWindow();
            app_stage.close();
        }
    }

    @FXML
    void albumsCopyDialogCancelButtonAction(ActionEvent event) {
        ((Stage)(albumsCopyDialogCancelButton.getScene().getWindow())).close();
    }
    
    @FXML
    private void albumsFullscreenAction(ActionEvent event) {
        String type = albumNameLabel.getText();
        System.out.println(type);
        //selectedPath = fst.getSelectedPath();
        if (type != null && new File(type).isFile()) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(explorerBtn1.getScene().getWindow());
            HBox dialogHbox = new HBox(20);
            dialogHbox.setAlignment(Pos.CENTER);
            Image image = new Image("file:\\" + type.toString());
            ImageView iv = new ImageView();

            dialogHbox.getChildren().add(iv);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            dialog.setX(bounds.getMinX());
            dialog.setY(bounds.getMinY());
            dialog.setWidth(bounds.getWidth());
            dialog.setHeight(bounds.getHeight());

            if (image.getHeight() > bounds.getHeight() || image.getWidth() > bounds.getWidth()) {
                image = new Image(("file:\\" + type.toString()), bounds.getWidth(), bounds.getHeight(), true, true);
            }

            iv.setImage(image);

            if (image.getWidth() / bounds.getWidth() > image.getHeight() / bounds.getHeight()) {
                if (image.getWidth() < bounds.getWidth()) {
                    iv.setFitWidth(image.getWidth());
                } else {
                    iv.setFitWidth(bounds.getWidth());
                }
            } else if (image.getHeight() < bounds.getHeight()) {
                iv.setFitHeight(image.getHeight());
            } else {
                iv.setFitHeight(bounds.getHeight());
            }

            Scene dialogScene = new Scene(dialogHbox, image.getWidth(), image.getHeight());
            dialog.setTitle(type);
            dialog.getIcons().add(new Image("icons/explorerTreeViewIcons/imagePlaceholder.png"));
            dialog.setResizable(false);
            dialog.setScene(dialogScene);
            dialog.show();
            System.out.println("file:\\" + type.toString());
            System.out.println("image.getWidth(): " + image.getWidth());
            System.out.println("image.getHeight(): " + image.getHeight());

        } else {
            //System.out.println("Please select a file to preview.");
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(explorerBtn1.getScene().getWindow());
            HBox dialogHbox = new HBox(20);
            dialogHbox.setAlignment(Pos.CENTER);
            Image image = new Image("icons/warningSign.png");
            ImageView iv = new ImageView();
            iv.setImage(image);
            dialogHbox.getChildren().add(iv);
            dialogHbox.getChildren().add(new Text("Please select an image to open in fullscreen."));
            Scene dialogScene = new Scene(dialogHbox, 400, 100);
            dialog.setTitle("Select an image!");
            dialog.getIcons().add(image);
            dialog.setResizable(false);
            dialog.setScene(dialogScene);
            dialog.show();
        }
        
        /*Disabling buttons after previewing the image */
        albumsImport.setDisable(false);
        albumsNewAlbum.setDisable(false);
        albumsDelete.setDisable(true);
        albumsRename.setDisable(true);
        albumsOpen.setDisable(true);
        albumsCopy.setDisable(true);
        albumsMove.setDisable(true);
        albumsFullscreen.setDisable(true);
        albumsBackButton.setDisable(false);
        albumNameLabel.setText("");
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    /* Explorer image panel hiding controlls */
    @FXML
    private javafx.scene.control.ToggleButton explorerImagePaneToggleButton;
    @FXML
    private javafx.scene.control.SplitPane explorerSplitPane;
    private double explorerDividerPosition;
    @FXML
    private javafx.scene.layout.AnchorPane explorerSplitPaneRightAnchor;
    private double explorerSplitPaneRightAnchorMinWidth;

    @FXML
    private void explorerImagePaneToggleButtonAction() {
        if (Math.abs(1.0 - (explorerSplitPane.getDividerPositions()[0])) > 0.01) {
            explorerSplitPaneRightAnchorMinWidth = explorerSplitPaneRightAnchor.getMinWidth();
            explorerSplitPaneRightAnchor.setMinWidth(0);
            explorerDividerPosition = (explorerSplitPane.getDividerPositions())[0];
            explorerSplitPane.setDividerPositions(1);
        } else {
            explorerSplitPaneRightAnchor.setMinWidth(explorerSplitPaneRightAnchorMinWidth);
            explorerSplitPane.setDividerPositions(explorerDividerPosition);
        }
    }

    /* Albums image panel hiding controlls */
    @FXML
    private javafx.scene.control.ToggleButton albumsImagePaneToggleButton;
    @FXML
    private javafx.scene.control.SplitPane albumsSplitPane;
    private double albumsDividerPosition;
    @FXML
    private javafx.scene.layout.AnchorPane albumsSplitPaneRightAnchor;
    private double albumsSplitPaneRightAnchorMinWidth;

    @FXML
    private void albumsImagePaneToggleButtonAction() {
        if (Math.abs(1.0 - (albumsSplitPane.getDividerPositions()[0])) > 0.01) {
            albumsSplitPaneRightAnchorMinWidth = albumsSplitPaneRightAnchor.getMinWidth();
            albumsSplitPaneRightAnchor.setMinWidth(0);
            albumsDividerPosition = (albumsSplitPane.getDividerPositions())[0];
            albumsSplitPane.setDividerPositions(1);
        } else {
            albumsSplitPaneRightAnchor.setMinWidth(albumsSplitPaneRightAnchorMinWidth);
            albumsSplitPane.setDividerPositions(albumsDividerPosition);
        }
    }

    /* Messages image panel hiding controlls */
    @FXML
    private javafx.scene.control.ToggleButton messagesImagePaneToggleButton;
    @FXML
    private javafx.scene.control.SplitPane messagesSplitPane;
    private double messagesDividerPosition;
    @FXML
    private javafx.scene.layout.AnchorPane messagesSplitPaneRightAnchor;
    private double messagesSplitPaneRightAnchorMinWidth;

    @FXML
    private void messagesImagePaneToggleButtonAction() {
        if (Math.abs(1.0 - (messagesSplitPane.getDividerPositions()[0])) > 0.01) {
            messagesSplitPaneRightAnchorMinWidth = messagesSplitPaneRightAnchor.getMinWidth();
            messagesSplitPaneRightAnchor.setMinWidth(0);
            messagesDividerPosition = (messagesSplitPane.getDividerPositions())[0];
            messagesSplitPane.setDividerPositions(1);
        } else {
            messagesSplitPaneRightAnchor.setMinWidth(messagesSplitPaneRightAnchorMinWidth);
            messagesSplitPane.setDividerPositions(messagesDividerPosition);
        }
    }

    public Button getValidationScreenValidateButton() {
        return validationScreenValidateButton;
    }

    public static void closeValidationScreen() {
        //System.out.println("Close button: " + validationScreenValidateButton);
        //Mosaic.closeStage("validationScreen");
        //((Stage) tempButton.getScene().getWindow()).close();
    }

    public ListView getRequestListView() {
        return screenshotRequestListView;
    }

    public void refreshRequestList() {
        Platform.runLater(() -> {
            ObservableList<ScreenshotMessage> tempList = FXCollections.observableArrayList();
            for (ScreenshotMessage m : screenshotMessageController.getScreenshotMessageList()) {
                if (m.getIsAccepted() == 0 && m.getIsISentThisMessage() == false) {
                    System.out.println("Message: " + m.getSender() + " - " + m.getReceiver() + " - " + m.getSentTimeString());
                    tempList.add(m);
                }
            }
            screenshotRequestListView.setItems(tempList);
            screenshotRequestListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    screenshotListViewSelectedItem = (ScreenshotMessage) screenshotRequestListView.getSelectionModel().getSelectedItem();
                    System.out.println("Selected item: " + screenshotListViewSelectedItem);
                }

            });
        });
    }
    
    @FXML
    private Button albumsImport;
    @FXML
    private Button albumsNewAlbum;

    @FXML
    private Button albumsDelete;
    @FXML private Button albumsRename;
    @FXML private Button albumsOpen;
    @FXML private Button albumsCopy;
    @FXML private Button albumsMove;
    @FXML private Button albumsFullscreen;
    @FXML private Button albumsBackButton;
    
    @FXML private Label albumsDateCreatedLabel;
    @FXML private Label albumsDateLabel;

    /**
     * *************************** INITIALIZE ******************************
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize " + numOfInitialize);
        numOfInitialize++;

        if (isFirstTime) {
            System.out.println("FIRST SET");
            Mosaic.setDocController(this);
            isFirstTime = false;
            fst = new FileSystemTree(explorerTreeView, explorerImgView, explorerPathTextField, explorerImageLabel, explorerBtn1);
            fst.start();
            fst.setExplorerCreateNewFolderButton(explorerBtn1);
            fst.setExplorerDeleteButton(explorerBtn2);
            fst.setExplorerRenameButton(explorerBtn3);
            fst.setExplorerOpenButton(explorerBtn4);
            fst.setExplorerCopyButton(explorerBtn5);
            fst.setExplorerMoveButton(explorerBtn6);
            fst.setExplorerAddImageToAlbumButton(explorerBtn7);
            fst.setExplorerFullscreenButton(explorerBtn8);
            virtualAlbumsController = new VirtualAlbumsController(albumsNavigationLabel, albumNameLabel, albumDescriptionLabel,
                    albumOrImageNameLabel, descriptionTempLabel, albumsFlowPane, imagesFlowPane, albumsScrollPane, imagesScrollPane, 
                    albumsDateCreatedLabel, albumsDateLabel, albumsImageView);
            virtualAlbumsController.setAlbumsImportButton(albumsImport);
            virtualAlbumsController.setAlbumsCreateAlbumButton(albumsNewAlbum);
            virtualAlbumsController.setAlbumsDeleteButton(albumsDelete);
            virtualAlbumsController.setAlbumsRenameButton(albumsRename);
            virtualAlbumsController.setAlbumsOpenButton(albumsOpen);
            virtualAlbumsController.setAlbumsCopyButton(albumsCopy);
            virtualAlbumsController.setAlbumsMoveButton(albumsMove);
            virtualAlbumsController.setAlbumsFullscreenButton(albumsFullscreen);
            virtualAlbumsController.setAlbumsBackButton(albumsBackButton);
            virtualAlbumsController.deserializeAllAlbums();
            virtualAlbumsController.addAllAlbumsToAlbumsFlowPane();
            
            ScreenshotNumberTest test = new ScreenshotNumberTest();
            numOfScreenshotSent = test.getNumOfSent();
            numOfScreenshotReceive = test.getNumOfReceive();

            screenshotMessageController = new ScreenshotMessageController();
            screenshotMessageController.deserializeScreenshotMessages();
            
            messageController = new MessageController();
            messageController.setScreenshotMessageController(screenshotMessageController);
            try {
                InetAddress addr = InetAddress.getByName("127.0.0.1");
                mySocket = new Socket(addr, 9000);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                System.out.println("CREATE LISTENER - myUsername: " + myUsername);
                listener = new MessageListener(this, out, in, onlineUsers, screenshotMessageController, myUsername, mySocket, screenshotListViewSelectedItem);
                System.out.println("listener created");
                listener.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("***********************************************************");
            ObservableList<ScreenshotMessage> tempListAccept = FXCollections.observableArrayList();
            //ObservableList<ScreenshotMessage> tempListDecline = FXCollections.observableArrayList();
            //ObservableList<ScreenshotMessage> tempListWithoutDecision = FXCollections.observableArrayList();
            //if (screenshotMessageController.getScreenshotMessageList() != null ){
            for (ScreenshotMessage m : screenshotMessageController.getScreenshotMessageList()) {
                //if (null != m){
                System.out.println("M: " + m);
                //if (m.getIsAccepted() == 0) {
                    //System.out.println("accepted");
                    //tempListWithoutDecision.add(m);
                /*} else */if (m.getIsAccepted() == 1) {
                    System.out.println("declined");
                    tempListAccept.add(m);
                } /*else if (m.getIsAccepted() == -1) {
                    System.out.println("notsure");
                    tempListDecline.add(m);
                }*/
                //}
            }//}
            ObservableList<ScreenshotMessage> tempList = FXCollections.observableArrayList();
            tempList.addAll(tempListAccept);
            //tempList.addAll(tempListDecline);
            //tempList.addAll(tempListWithoutDecision);
            for (ScreenshotMessage ms : tempList) {
                System.out.println("ms: " + ms);
            }
            messagesListView.setItems(tempList);
            messagesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    screenshotListViewSelectedItem = (ScreenshotMessage) messagesListView.getSelectionModel().getSelectedItem();
                    System.out.println("Selected item: " + screenshotListViewSelectedItem);
                }

            });
            System.out.println("***********************************************************");

            /*Disabling menu buttons*/
            explorerBtn1.setDisable(true);
            explorerBtn2.setDisable(true);
            explorerBtn3.setDisable(true);
            explorerBtn4.setDisable(true);
            explorerBtn5.setDisable(true);
            explorerBtn6.setDisable(true);
            explorerBtn7.setDisable(true);
            explorerBtn8.setDisable(true);
            
            albumsImport.setDisable(false);
            albumsNewAlbum.setDisable(false);
            albumsDelete.setDisable(true);
            albumsRename.setDisable(true);
            albumsOpen.setDisable(true);
            albumsCopy.setDisable(true);
            albumsMove.setDisable(true);
            albumsFullscreen.setDisable(true);
            albumsBackButton.setDisable(true);
            
            /* removing album labels when you enter the album */
            albumNameLabel.setVisible(false);
            albumOrImageNameLabel.setVisible(false);
            albumDescriptionLabel.setVisible(false);
            descriptionTempLabel.setVisible(false);
            albumsDateCreatedLabel.setVisible(false);
            albumsDateLabel.setVisible(false);
            

        }
        if (isFirstTime || isMainFormActive) {
            ObservableList<String> albumNames = virtualAlbumsController.getAllAlbumsName();
            try {
                if (moveImagePopUpChoiceBox != null) {
                    moveImagePopUpChoiceBox.getItems().addAll(albumNames);
                }
                if (albumsImportPopUpChoiseBox != null) {
                    albumsImportPopUpChoiseBox.getItems().addAll(albumNames);
                }
                if (screenshotPopUpImageView != null && iconImage != null) {
                    screenshotPopUpImageView.setImage(iconImage);
                }

                if (screenshotPopUpChoiseBox != null && onlineUsers.size() != 0) {
                    screenshotPopUpChoiseBox.setItems(onlineUsers);
                }
                if (isRequestPopUpRun) {
                    System.out.println("\nINITIALIZE ADD MESSAGES TO LIST");
                    System.out.println("***********************************************************");
                    ObservableList<ScreenshotMessage> tempListForListView = FXCollections.observableArrayList();
                    for (ScreenshotMessage m : screenshotMessageController.getScreenshotMessageList()) {
                        if (m.getIsAccepted() == 0 && m.getIsISentThisMessage() == false) {
                            System.out.println("Message: " + m.getSender() + " - " + m.getReceiver() + " - " + m.getSentTimeString());
                            tempListForListView.add(m);
                        }
                    }
                    screenshotRequestListView.setItems(tempListForListView);
                    screenshotRequestListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            screenshotListViewSelectedItem = (ScreenshotMessage) screenshotRequestListView.getSelectionModel().getSelectedItem();
                            System.out.println("Selected item: " + screenshotListViewSelectedItem);
                        }

                    });
                    System.out.println("***********************************************************");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
