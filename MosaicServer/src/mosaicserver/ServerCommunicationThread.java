package mosaicserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import users.*;
import activationkeys.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Random;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 *
 * @author Asus
 */
public class ServerCommunicationThread extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ActivationKeyController activationKeyController;
    private UsersController usersController;
    private static HashMap<String, File> pictureHashMap = new HashMap<String, File>();
    private FXMLServerDocumentController serverController;

    public ServerCommunicationThread() {
        super();
    }

    public ServerCommunicationThread(Socket socket, ActivationKeyController activationKeyController, UsersController usersController, 
            FXMLServerDocumentController serverController) {
        super();
        this.socket = socket;
        this.activationKeyController = activationKeyController;
        this.usersController = usersController;
        this.serverController = serverController;
        System.out.println("Server thread creating...");
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Server thread created!");
    }

    public void run() {
        try {
            System.out.println("Server running... " + super.getId());
            //ActivationKeyController k = new ActivationKeyController();

            boolean test = true;
            while (test) {
                try {
                    String typeOfMsg = "";
                    typeOfMsg = in.readLine();
                    System.out.println("TYPE OF MESSAGE SERVER THREAD: " + typeOfMsg);
                    ////////// REGISTRATION  ////////
                    if ("registration".equals(typeOfMsg.split("#")[0])) {
                        String requestKey = typeOfMsg.split("#")[1];
                        if (activationKeyController.isKeyInList(requestKey)) {
                            activationKeyController.removeActivationKey(requestKey);
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    serverController.setTextToTrafficTextArea("Korisnik " + socket.getLocalSocketAddress().toString()+ 
                                            " se uspjesno registrovao sa aktivacionim kljucem: " + requestKey + "\n");
                                }
                            });
                            out.println("registration#keyIsOK");
                        } else {
                            out.println("registration#keyIsNotOK");
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    serverController.setTextToTrafficTextArea("Korisnik " + socket.getLocalSocketAddress().toString()+ 
                                            " je unio pogresan aktivacioni kljuc: " + requestKey + "\n");
                                }
                            });
                        }

                    } 
                    ////////////  LOGIN  ////////////
                    else if ("login".equals(typeOfMsg.split("#")[0])) {
                        String requestedUsername = typeOfMsg.split("#")[1];
                        if (usersController.isUsernameAvailable(requestedUsername)) {
                            String username = typeOfMsg.split("#")[1];
                            usersController.addNewOnlineUser(socket, typeOfMsg.split("#")[1]);
                            out.println("login#usernameIsAvailable");
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    serverController.setTextToTrafficTextArea("Korisnik " + username + " se uspjesno prijavio\n");
                                }
                            });
                        } else {
                            out.println("login#usernameIsNotAvailable");
                        }
                    } 
                    ////////////  SCREENSHOT  /////////
                    else if ("screenshot".equals(typeOfMsg.split("#")[0])) {
                        if ("getList".equals(typeOfMsg.split("#")[1])) {
                            System.out.println("\t\t\t GET LIST");
                            System.out.println("************************************************");
                            String sourceUser = typeOfMsg.split("#")[2];
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    serverController.setTextToTrafficTextArea("Korisnik " + sourceUser + " je zatrazio listu online korisnika\n");
                                }
                            });
                            ObservableList<User> users = usersController.getOnlineUsersList();
                            System.out.println("Users list: " + users);
                            out.println("screenshot#");
                            for (User u : users) {
                                System.out.println("\tuser: " + u.getUsername());
                                if(!(sourceUser).equals(u.getUsername())){
                                    out.println(u.getUsername());
                                }
                            }
                            out.println("END_OF_USERNAME_LIST");
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    serverController.setTextToTrafficTextArea("Korisniku " + sourceUser + " je proslijedjena lista online korisnika\n");
                                }
                            });
                            System.out.println("************************************************");
                            if(!(new File("TransferedScreenshots").exists())){
                                System.out.println("\nCREATE TransferedScreenshot FOLDER\n");
                                (new File("TransferedScreenshots")).mkdir();
                            }
                            
                            
                            System.out.println("Waiting for destinationUserRequest...");
                            String destinationUserRequest = in.readLine();
                            if (!"SENDING_OF_SCREENSHOT_DECLINED#".equals(destinationUserRequest)) {
                                String destinationUser = destinationUserRequest.split("#")[0];
                                String millis = destinationUserRequest.split("#")[1];
                                System.out.println("Primljeno: " + destinationUserRequest);
                                // slanje fajla na server //
                                System.out.println("\nWaiting for DUZINA...");
                                String duz = in.readLine();
                                System.out.println("Primljeno: " + duz);
                                /*int duzina = Integer.parseInt(duz);
                                int kontrolnaDuzina = 0, flag = 0;
                                byte[] buffer = new byte[2 * 1024];
                                String fileName = randomGeneratorString() + ".jpg";
                                OutputStream fajl = new FileOutputStream("TransferedScreenshots" + File.separator + fileName);
                                InputStream is = socket.getInputStream();
                                System.out.println("\nSENDING IMAGE TO SERVER...");
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
                                }*/
                                
                                long duzina = Long.parseLong(duz);
                                
                                ServerSocket imageServerSocket = new ServerSocket(6066);
                                Socket imageSocket = imageServerSocket.accept();
                                
                                //int duzina = Integer.parseInt(duz);
                                int kontrolnaDuzina = 0, flag = 0;
                                byte[] buffer = new byte[2 * 1024];
                                String fileName = randomGeneratorString() + ".jpg";
                                OutputStream fajl = new FileOutputStream("TransferedScreenshots" + File.separator + fileName);
                                InputStream is = imageSocket.getInputStream();
                                System.out.println("\nSENDING IMAGE TO SERVER...");
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
                                //fajl.close();
                                System.out.println("Fajl zatvoren!\n");
                                Platform.runLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        serverController.setTextToTrafficTextArea("Korisnik " + sourceUser + " je proslijedio sliku ekrana ka serveru\n");
                                    }
                                });
                                File picFile = new File("TransferedScreenshots" + File.separator + fileName);
                                String picString = sourceUser + "#" + destinationUser + "#" + millis;
                                System.out.println("IMAGE STRING: " + picString);
                                System.out.println("IMAGE FILE: " + picFile);
                                System.out.println("Postavi u hash mapu...");
                                pictureHashMap.put(picString, picFile);
                                System.out.println("Postavljeno!");

                                System.out.println("\nSADRZAJ HASH MAPOM");
                                System.out.println("*****************************************************");
                                for (String s : pictureHashMap.keySet()) {
                                    System.out.println("KEY 1: " + s);
                                    System.out.println("VALUE 1: " + pictureHashMap.get(s));
                                }
                                System.out.println("*****************************************************");

                                System.out.println("\nKREIRANJE USER-a  I OTVARANJE STREAM-a");
                                User destUser = usersController.getUserFromListForString(destinationUser);
                                PrintWriter outDestination = new PrintWriter(new OutputStreamWriter(destUser.getMySocket().getOutputStream()), true);
                                BufferedReader inDestination = new BufferedReader(new InputStreamReader(destUser.getMySocket().getInputStream()));
                                System.out.println("\nSLANJE REQUEST-a DRUGOM KLIJENTU");
                                outDestination.println("screenshotRequest#" + sourceUser + "#" + millis);
                                System.out.println("SLANJE REQUEST-a ZAVRSENO: " + "screenshotRequest#" + sourceUser + "#" + millis);
                            //outDestination.close();
                                //inDestination.close();
                            }
                            System.out.println("Waiting end");
                        }
                    } 
                    else if("screenshotResponseAccept".equals(typeOfMsg.split("#")[0])){
                        System.out.println("\nSCREENSHOT ACCEPTED");
                        String sender = typeOfMsg.split("#")[1];
                        String receiver = typeOfMsg.split("#")[2];
                        String millis = typeOfMsg.split("#")[3];
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                serverController.setTextToTrafficTextArea("Korisnik " + receiver + " je prihvatio sliku koju mu je poslao korisnik " + sender + "\n");
                            }
                        });
                        String hashString = sender + "#" + receiver + "#" + millis;
                        System.out.println("HASH STRING: " + hashString);
                        File picFile = pictureHashMap.get(hashString);
                        System.out.println("\nSADRZAJ HASH MAPOM");
                        System.out.println("*****************************************************");
                        for(String s: pictureHashMap.keySet()){
                            System.out.println("KEY: " + s);
                            System.out.println("VALUE: " + pictureHashMap.get(s));
                        }
                        System.out.println("*****************************************************");
                        System.out.println("\nPIC FILE: " + picFile);
                        System.out.println("SCREENSHOT FILE: " + picFile.getPath());
                        out.println("receiveScreenshot#" + sender + "#" + receiver + "#" + millis);
                        System.out.println("SENT REQUEST: " + "receiveScreenshot#" + sender + "#" + receiver + "#" + millis);
                        long duzina = picFile.length();
                        System.out.println("\nSENDING DUZINA TO SECOND CLIENT");
                        out.println(duzina);
                        System.out.println("SENT: " + duzina);
                        /*byte[] buffer = new byte[50 * 1024];
                        InputStream fajl = new FileInputStream(picFile);
                        int length = 0;
                        OutputStream os = socket.getOutputStream();
                        System.out.println("\nSENDING IMAGE TO SECOND CLIENT...");
                        System.out.println("*****************************************************");
                        while ((length = fajl.read(buffer)) > 0) {
                            System.out.println("Length: " + length);
                            System.out.println("duzina: " + duzina);
                            os.write(buffer, 0, length);
                            System.out.println("Preostalo jos " + (duzina -=  length));
                        }*/
                        
                        System.out.println("Opening new socket: ");
                        Socket imageSocket = new Socket("localhost", 6066);

                        byte[] buffer = new byte[2 * 1024];
                        InputStream fajl = new FileInputStream(picFile);
                        int length = 0;
                        OutputStream os = imageSocket.getOutputStream();
                        System.out.println("SENDING IMAGE TO SECOND CLIENT...");
                        System.out.println("**************************************************");
                        while ((length = fajl.read(buffer)) > 0) {
                            System.out.println("Length: " + length);
                            System.out.println("duzina: " + duzina);
                            os.write(buffer, 0, length);
                            System.out.println("Preostalo jos " + (duzina -= length));
                        }
                        System.out.println("Closing streams and socket.");
                        os.close();
                        fajl.close();
                        imageSocket.close();
                        System.out.println("Closing streams and socket done.");
                        
                        System.out.println("Slanje drugom klijentu zavrseno...");
                        //fajl.close();
                        System.out.println("*****************************************************");
                        
                        System.out.println("\nKREIRANJE SOURCE USER-a  I OTVARANJE STREAM-a");
                        User sourceUser = usersController.getUserFromListForString(sender);
                        PrintWriter outDestination = new PrintWriter(new OutputStreamWriter(sourceUser.getMySocket().getOutputStream()), true);
                        BufferedReader inDestination = new BufferedReader(new InputStreamReader(sourceUser.getMySocket().getInputStream()));
                        System.out.println("\nSLANJE REQUEST-a DRUGOM KLIJENTU");
                        outDestination.println("screenshotAccepted#" + sender + "#" + receiver + "#" + millis);
                        System.out.println("SLANJE REQUEST-a ZAVRSENO: " + "screenshotAccepted#" + sender + "#" + receiver + "#" + millis);
                        //outDestination.close();
                        //inDestination.close();
                    }
                    else if("screenshotResponseDecline".equals(typeOfMsg.split("#")[0])){
                        System.out.println("\nSCREENSHOT DECLINE");
                        String sender = typeOfMsg.split("#")[1];
                        String receiver = typeOfMsg.split("#")[2];
                        String millis = typeOfMsg.split("#")[3];
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                serverController.setTextToTrafficTextArea("Korisnik " + receiver + " je odbio sliku koju mu je poslao korisnik " + sender + "\n");
                            }
                        });
                        String hashString = sender + "#" + receiver + "#" + millis;
                        System.out.println("\nKREIRANJE SOURCE USER-a  I OTVARANJE STREAM-a");
                        User sourceUser = usersController.getUserFromListForString(sender);
                        PrintWriter outDestination = new PrintWriter(new OutputStreamWriter(sourceUser.getMySocket().getOutputStream()), true);
                        BufferedReader inDestination = new BufferedReader(new InputStreamReader(sourceUser.getMySocket().getInputStream()));
                        System.out.println("\nSLANJE REQUEST-a DRUGOM KLIJENTU");
                        outDestination.println("screenshotDeclined#" + sender + "#" + receiver + "#" + millis);
                        System.out.println("SLANJE REQUEST-a ZAVRSENO: " + "screenshotAccepted#" + sender + "#" + receiver + "#" + millis);
                    }
                    ///////////  QUIT  //////////
                    else if ("QUIT".equals(typeOfMsg.split("#")[0])) {
                        String username = typeOfMsg.split("#")[1];
                        if (!"nop".equals(username)) {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    serverController.setTextToTrafficTextArea("Korisnik " + username + " se odjavio\n");
                                }
                            });
                            usersController.removeUser(username);
                        }
                        out.println("QUIT#");
                        test = false;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("CLOSING CONNECTION");
            in.close();
            out.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String randomGeneratorString(){
        Random rand = new Random();
        String retStr = "message";
        for(int i=0; i<5; i++){
            retStr += rand.nextInt(9);
        }
        return retStr;
    }
}
