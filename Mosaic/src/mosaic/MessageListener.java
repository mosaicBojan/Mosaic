/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Random;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import messages.MessageController;
import screenshots.ScreenshotMessage;
import screenshots.ScreenshotMessageController;

/**
 *
 * @author Asus
 */
public class MessageListener extends Thread{
    private PrintWriter out;
    private BufferedReader in;
    private Stage stage;
    private ObservableList<String> onlineUsers;
    private FXMLDocumentController docController;
    private ScreenshotMessageController screenshotMessageController;
    private String myUsername;
    private Socket mySocket;
    private ScreenshotMessage screenshotListViewSelectedItem;
    private ListView list;
    private Label validationScreenWarningLabel;
    private Label loginScreenWarningLabel;
    private MessageController messageController;
    
    public MessageListener(FXMLDocumentController docController, PrintWriter out, BufferedReader in, ObservableList<String> onlineUsers,
            ScreenshotMessageController screenshotMessageController, String myUsername, Socket mySocket, ScreenshotMessage screenshotListViewSelectedItem){
        super();
        this.out = out;
        this.in = in;
        this.onlineUsers = onlineUsers;
        this.docController = docController;
        this.screenshotMessageController = screenshotMessageController;
        this.myUsername = myUsername;
        this.mySocket = mySocket;
        this.screenshotListViewSelectedItem = screenshotListViewSelectedItem;
    }

    public void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    
    
    public void setList(ListView list) {
        this.list = list;
    }
    
    public void setValidationScreenWarningLabel(Label validationScreenWarningLabel){
        this.validationScreenWarningLabel = validationScreenWarningLabel;
    }

    public void setLoginScreenWarningLabel(Label loginScreenWarningLabel) {
        this.loginScreenWarningLabel = loginScreenWarningLabel;
    }
    
    
    
    public void run(){
        boolean test = true;
        while(test){
            
            try {
                String typeOfMsg = "";
                typeOfMsg = in.readLine();
                System.out.println("TYPE OF MESSAGE LISTENER: " + typeOfMsg);
                if("registration".equals(typeOfMsg.split("#")[0])){
                    if("keyIsOK".equals(typeOfMsg.split("#")[1])){
                        //Uspjesna registracija
                        System.out.println("Uspjena registracija");
                        ActivationTest at = new ActivationTest();
                        at.setIsAvtivated(true);
                        at.serializeActivationTest();
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                stage.hide();
                            }
                        });
                    }
                    else{
                        //Neuspjesna registracija
                        System.out.println("Neuspjesna registracija");
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                validationScreenWarningLabel.setText("Invalid activation key.");
                            }
                        });
                    }
                }
                else if("login".equals(typeOfMsg.split("#")[0])){
                    if("usernameIsAvailable".equals(typeOfMsg.split("#")[1])){
                        System.out.println("Uspjena prijava");
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                FXMLDocumentController.setIsLoginEnd();
                                stage.hide();
                            }
                        });
                    }
                    else{
                        System.out.println("Neuspjesna prijava");
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                loginScreenWarningLabel.setText("Username already taken.");
                            }
                        });
                    }
                }
                else if("screenshot".equals(typeOfMsg.split("#")[0])){
                    System.out.println("Message Listener screenshot");
                    
                    if(onlineUsers.size() != 0){
                        ObservableList<String> temp = FXCollections.observableArrayList();
                        for(String s: onlineUsers){
                            temp.add(s);
                        }
                        onlineUsers.removeAll(temp);
                    }
                    
                    String destinationUsername = "";
                    boolean endTest = true;
                    while (endTest) {
                        destinationUsername = in.readLine();
                        if ("END_OF_USERNAME_LIST".equals(destinationUsername)) {
                            endTest = false;
                        } else {
                            onlineUsers.add(destinationUsername);
                        }
                    }
                }
                else if("screenshotRequest".equals(typeOfMsg.split("#")[0])){
                    System.out.println("\nREQUEST FORM SERVER RECEIVED");
                    System.out.println("\nCREATE SCREENSHOTMESSAGE WITH ATRIBUTE:");
                    System.out.println("*******************************************************");
                    String sender = typeOfMsg.split("#")[1];
                    String date = typeOfMsg.split("#")[2];
                    ScreenshotMessage receiveMsg = new ScreenshotMessage();
                    receiveMsg.setIsISentThisMessage(false);
                    receiveMsg.setSender(sender);
                    receiveMsg.setSentTimeString(date);
                    receiveMsg.setReceiver(myUsername);
                    System.out.println("Sender*: " + receiveMsg.getSender());
                    System.out.println("Receiver*: " + receiveMsg.getReceiver());
                    System.out.println("Is I sent*: " + receiveMsg.getIsISentThisMessage());
                    System.out.println("Sent time string*: " + receiveMsg.getSentTimeString());
                    System.out.println("Is accepted: " + receiveMsg.getIsAccepted());
                    System.out.println("********************************************************");
                    screenshotMessageController.addScreenshotMessage(receiveMsg);
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            docController.setEnableButton();
                            messageController.setMessagesListView(list);
                            messageController.addMessagesToListView();
                        }
                    });
                    
                    System.out.println("END REQUEST IN LISTENER\n\n");
                }
                else if("receiveScreenshot".equals(typeOfMsg.split("#")[0])){
                    System.out.println("RECEIVE SCREENSHOT RECEIVER");
                    System.out.println("*************************************************");
                    String sender = typeOfMsg.split("#")[1];
                    String receiver = typeOfMsg.split("#")[2];
                    String millis = typeOfMsg.split("#")[3];
                    long duzina = Long.parseLong(in.readLine());
                    System.out.println("DUZINA FROM SERVER: " + duzina);
                    //int kontrolnaDuzina = 0, flag = 0;
                    //byte[] buffer = new byte[50 * 1024];
                    String fileName = randomGeneratorString() + ".jpg";
                    File folder = new File("ReceivedScreenshotMessages");
                    if(!folder.exists()){
                        System.out.println("CREATE FILE");
                        folder.mkdir();
                    }
                    
                    /*OutputStream fajl = new FileOutputStream("ReceivedScreenshotMessages" + File.separator + fileName);
                    InputStream is = mySocket.getInputStream();
                    System.out.println("RECEIVING SCREENSHOT FROM SERVER...");
                    System.out.println("*******************************************************");
                    while ((kontrolnaDuzina = is.read(buffer)) > 0) {
                        fajl.write(buffer, 0, kontrolnaDuzina);
                        flag += kontrolnaDuzina;
                        System.out.println("Kontrolna duzina: " + kontrolnaDuzina);
                        System.out.println("Flag: " + flag);
                        System.out.println("Duzina: " + duzina);
                        if (duzina == flag) {
                            break;
                        }
                    }*/
                    
                    ServerSocket imageServerSocket = new ServerSocket(6066);
                    Socket imageSocket = imageServerSocket.accept();

                    //int duzina = Integer.parseInt(duz);
                    int kontrolnaDuzina = 0, flag = 0;
                    byte[] buffer = new byte[2 * 1024];
                    OutputStream fajl = new FileOutputStream("ReceivedScreenshotMessages" + File.separator + fileName);
                    InputStream is = imageSocket.getInputStream();
                    System.out.println("\nRECEIVING SCREENSHOT FROM SERVER...");
                    System.out.println("*****************************************************");
                    while ((kontrolnaDuzina = is.read(buffer)) > 0) {
                        fajl.write(buffer, 0, kontrolnaDuzina);
                        flag += kontrolnaDuzina;
                        System.out.println("Kontrolna duzina: " + kontrolnaDuzina);
                        System.out.println("Flag: " + flag);
                        System.out.println("Duzina: " + duzina);
                        if (duzina == flag) {
                            break;
                        }
                    }

                    System.out.println("Preuzimanje zavrseno...");
                    System.out.println("*****************************************************");

                    System.out.println("Closing sockets and streams.");
                    fajl.close();
                    is.close();
                    imageSocket.close();
                    imageServerSocket.close();
                    System.out.println("Closing sockets and streams done.");
                    
                    System.out.println("Preuzimanje od servera zavrseno...");
                    //fajl.close();
                    System.out.println("*********************************************************");
                    ObservableList<ScreenshotMessage> msgs = screenshotMessageController.getScreenshotMessageList();
                    ScreenshotMessage msg = null;
                    for (ScreenshotMessage m : msgs) {
                        if (m.getSender().equals(sender) && m.getReceiver().equals(receiver) && m.getSentTimeString().equals(millis)) {
                            msg = m;
                            break;
                        }
                    }
                    msg.setPath(new File("ReceivedScreenshotMessages" + File.separator + fileName));
                    msg.setIsAccepted(1);
                    System.out.println("\nSCREENSHOT MESSAGE ATRIBUTES:");
                    System.out.println("********************************************************");
                    System.out.println("Sender*: " + msg.getSender());
                    System.out.println("Receiver*: " + msg.getReceiver());
                    System.out.println("Is I sent*: " + msg.getIsISentThisMessage());
                    System.out.println("Sent time string*: " + msg.getSentTimeString());
                    System.out.println("Is accepted: " + msg.getIsAccepted());
                    System.out.println("********************************************************\n");
                    System.out.println("SCREENSHOT ACCEPTED END!");
                }
                else if("screenshotDeclined".equals(typeOfMsg.split("#")[0])){
                    System.out.println("DECLINE SCREENSHOT LISTENER");
                    System.out.println("*************************************************");
                    String sender = typeOfMsg.split("#")[1];
                    String receiver = typeOfMsg.split("#")[2];
                    String millis = typeOfMsg.split("#")[3];
                    
                    ObservableList<ScreenshotMessage> msgs = screenshotMessageController.getScreenshotMessageList();
                    ScreenshotMessage msg = null;
                    for (ScreenshotMessage m : msgs) {
                        if (m.getSender().equals(sender) && m.getReceiver().equals(receiver) && m.getSentTimeString().equals(millis)) {
                            msg = m;
                            break;
                        }
                    }
                    msg.setIsAccepted(-1);
                    messageController.addMessagesToListView();
                    System.out.println("*************************************************");
                }
                else if("screenshotAccepted".equals(typeOfMsg.split("#")[0])){
                    System.out.println("\nSCREENSHOT ACCEPTED LISTENER");
                    String sender = typeOfMsg.split("#")[1];
                    String receiver = typeOfMsg.split("#")[2];
                    String millis = typeOfMsg.split("#")[3];
                    ObservableList<ScreenshotMessage> msgs = screenshotMessageController.getScreenshotMessageList();
                    ScreenshotMessage msg = null;
                    for (ScreenshotMessage m : msgs) {
                        if (m.getSender().equals(sender) && m.getReceiver().equals(receiver) && m.getSentTimeString().equals(millis)) {
                            msg = m;
                            break;
                        }
                    }
                    msg.setIsAccepted(1);
                    
                    System.out.println("\nSCREENSHOT MESSAGE ATRIBUTES:");
                    System.out.println("********************************************************");
                    System.out.println("Sender*: " + msg.getSender());
                    System.out.println("Receiver*: " + msg.getReceiver());
                    System.out.println("Is I sent*: " + msg.getIsISentThisMessage());
                    System.out.println("Sent time string*: " + msg.getSentTimeString());
                    System.out.println("Is accepted: " + msg.getIsAccepted());
                    System.out.println("********************************************************\n");
                    System.out.println("SCREENSHOT ACCEPTED END!");
                    messageController.addMessagesToListView();
                    
                }
                else if("QUIT".equals(typeOfMsg.split("#")[0])){
                    try{
                        out.close();
                        in.close();
                        test = false;
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public String randomGeneratorString(){
        Random rand = new Random();
        String retStr = "message";
        for(int i=0; i<5; i++){
            retStr += rand.nextInt(9);
        }
        return retStr;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }
    
    
}
