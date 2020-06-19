
/*
 * Name: Dhruv Dhananjaybhai Thakkar
 * Id: 1001815888
 *
 * */

package sample.server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// This class contains UI and server for this application
public class Server extends Application implements Runnable {
    // Store the client details (Activation details)
    public static HashMap<String, Boolean> clientsInHall = new HashMap<>();
    // stores the log
    public static ArrayList<String> log = new ArrayList<>();
    // Store the client and it's print writer stream
    static HashMap<String, PrintWriter> clientsSocket = new HashMap<>();
    // server socket and client socket where the request would be accepted
    static ServerSocket ss;
    static Socket s;
    // 1235 is port number where request would be listened
    static int port = 1235;
    // This is functionality to provide different port to create TCP connection to each new client
    static int portManagement = 2000;
    static int countClient = 0;
    // Streams for input and output the data
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    // Thread, just to make globe variable
    Thread t;
    Stage rootStage;

    // To find the status of the client
    public static boolean isClientThere(String clientName) {
        System.out.println(clientsInHall.get(clientName));
        // If client is not there, put it's name and make value true
        if (!clientsInHall.containsKey(clientName)) {
            clientsInHall.put(clientName, true);
            return false;
        } else if (clientsInHall.containsKey(clientName) && !clientsInHall.get(clientName)) {
            // If client is not there, but it's already been registered put it's name and make value true
            clientsInHall.put(clientName, true);
            return false;
        } else {
            return true;
        }
    }

    // hit the server
    public static void main(String[] args) throws IOException {
        System.out.println("It's Started!");
        launch(args);
    }

    // Method to start actual server
    public static void startOperation() throws IOException {
        // Store the log
        log.add("Server has started!");
        // Create object of the server
        ss = new ServerSocket(port);
        // Bunch of object for stream data to and from the client
        PrintWriter pwrite;
        InputStream istream;
        BufferedReader receiveRead;
        OutputStream ostream = null;

        int i = 0;
        // Accept the client request
        while (true) {
            // Assign to socket
            s = ss.accept();
            // increment the port number
            portManagement++;
            try {
                // Bunch of object for stream data to and from the client
                ostream = s.getOutputStream();
                pwrite = new PrintWriter(ostream, true);
                istream = s.getInputStream();
                receiveRead = new BufferedReader(new InputStreamReader(istream));
                // Send the port number for that particular client
                pwrite.println(portManagement);
                // Create new thread for every new client
                clientThread[] newClient = new clientThread[3];
                // print the log
                System.out.println("Server is running! on: " + portManagement);
                // Start the thread for new client
                newClient[i] = new clientThread(portManagement);
                newClient[i].start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //------------------------UI Part---------------------------------//

    // This is where server's internal operation would start. Where it creates new thread.
    @Override
    public void run() {
        try {
            Server.startOperation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Start method to boot window (Server UI)
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Set the title of that window
        primaryStage.setTitle("Client-Server Application");
        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 1200, 650);
        // Set the scene in primary stage
        primaryStage.setScene(scene);
        // Show on screen
        primaryStage.show();
        // Assign value to globe scope
        rootStage = primaryStage;
        // On close request this thing would have done
        primaryStage.setOnCloseRequest((WindowEvent e) -> {
            System.out.println("Okay bye!");
        });
    }

    private GridPane createRegistrationFormPane() {

        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();
        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);
        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        // Set the horizontal gap between columns
        gridPane.setHgap(10);
        // Set the vertical gap between rows
        gridPane.setVgap(10);
        // Add Column Constraints
        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        // Set alignment to right
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        // return updated gridPane
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Connect button
        Button connectButton = new Button("Start the server");
        // Set the style and alignment of the button
        connectButton.setPrefHeight(40);
        connectButton.setDefaultButton(true);
        connectButton.setPrefWidth(150);
        // Add button to gridPane
        gridPane.add(connectButton, 0, 4, 2, 1);
        GridPane.setHalignment(connectButton, HPos.CENTER);
        GridPane.setMargin(connectButton, new Insets(390, 0, 0, 0));
        // When event occurs
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Start the server
                Server s = new Server();
                t = new Thread(s);
                t.start();
                // Disable the button after the connect
                connectButton.setDisable(true);
            }
        });


        // Add Submit Button
        Button submitButton = new Button("Show updated Client");
        // Set the style and alignment of the button
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(150);
        // Add button to gridPane
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.LEFT);
        GridPane.setMargin(submitButton, new Insets(390, 0, 0, 0));
        // When event occurs
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView<String> clientList = new ListView<>();
                // Iterate client data and add to list
                for (Map.Entry<String, Boolean> e :
                        Server.clientsInHall.entrySet()) {
                    String s = e.getKey() + " : " + e.getValue();
                    clientList.getItems().add(s);
                }
                // Add whole list to layout and set styles
                gridPane.add(clientList, 0, 0, 2, 1);
                GridPane.setHalignment(clientList, HPos.RIGHT);
                GridPane.setMargin(clientList, new Insets(10, 0, 10, 20));
            }
        });

        // Add Submit Button
        Button logButton = new Button("Update logs");
        // Set the style and alignment of the button
        logButton.setPrefHeight(40);
        logButton.setDefaultButton(true);
        logButton.setPrefWidth(100);
        // Add button to gridPane
        gridPane.add(logButton, 0, 4, 2, 1);
        GridPane.setHalignment(logButton, HPos.LEFT);
        GridPane.setMargin(logButton, new Insets(390, 0, 0, 300));
        // When Event occurs
        logButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView<String> clientList = new ListView<>();
                for (String e :
                        Server.log) {
                    clientList.getItems().add(e);
                }
                // Add whole list to layout and set styles
                gridPane.add(clientList, 0, 0, 2, 1);
                GridPane.setHalignment(clientList, HPos.RIGHT);
                GridPane.setMargin(clientList, new Insets(10, 0, 10, 20));
            }
        });

        // Add Submit Button
        Button startOps = new Button("Check all 3 client");
        // Set the style and alignment of the button
        startOps.setPrefHeight(40);
        startOps.setDefaultButton(true);
        startOps.setPrefWidth(150);
        // Add button to gridPane
        gridPane.add(startOps, 0, 4, 2, 1);
        GridPane.setHalignment(startOps, HPos.LEFT);
        GridPane.setMargin(startOps, new Insets(390, 0, 0, 700));
        // When Event occurs
        startOps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (countClient != 3) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Please wait!", "There are not all client connected. Please wait!");
                } else {
                    startOps.setDisable(true);
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "The system started!", "Client A, B, and C has been communicating vector clock with each other!");
                }
            }
        });


        // Add close Button
        Button closeButton = new Button("Okay, Close it!");
        // Edit style of button
        closeButton.setPrefHeight(40);
        closeButton.setDefaultButton(true);
        closeButton.setPrefWidth(100);
        // Add to layout and set style
        gridPane.add(closeButton, 0, 4, 2, 1);
        GridPane.setHalignment(closeButton, HPos.RIGHT);
        GridPane.setMargin(closeButton, new Insets(390, 0, 0, 0));

        // method where event occur for check-In button
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            // handle event
            public void handle(ActionEvent event) {
                rootStage.close();
            }
        });
    }

    // Show alert
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    ///////////////////////// UI-Part ////////////////////////////////////
}

// Class for new client thread
class clientThread extends Thread {
    // Port number assignment
    int port;

    clientThread(int port) {
        this.port = port;
    }

    // File create and update the message
    public static void fileCreation(String sender, String userName, String message) {
        // https://www.javatpoint.com/how-to-create-a-file-in-java, get some reference from this link
        FileOutputStream fos = null;
        try {
            // https://beginnersbook.com/2013/05/current-date-time-in-java/, get some reference about date-time from this link
            // Create new file object
            fos = new FileOutputStream(userName + ".txt", true);
            // Date format
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            // Create new Date object
            Date dateObj = new Date();
            String dataTime = df.format(dateObj);
            // Create message format
            message = dataTime + ": " + "from: " + sender + ": " + message + "::done::\n";
            //converts string into bytes
            byte[] b = message.getBytes();
            //writes bytes into file
            fos.write(b);
            //close the file
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // run the server from this method
    public void run() {
        // Server socket reference
        Socket s;
        ServerSocket ssc = null;
        // List that temporary contains client data
        ArrayList<String> clientDataForSendMessage = new ArrayList<>();
        // Object get assign to sockets
        try {
            ssc = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream ostream = null;
        // sending data to client
        try {
            // Accept the client
            s = ssc.accept();
            // Get streams
            ostream = s.getOutputStream();
            PrintWriter pwrite = new PrintWriter(ostream, true);
            // receiving from server ( receiveRead  object)
            InputStream istream = s.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
            // username to globe object
            String userName = "";
            // dataFromClient to globe object
            String dataFromClient = "";
            // Read the client request
            userName = receiveRead.readLine();
            // Set thread name
            this.setName(userName);
            // If client is there
            if (!Server.isClientThere(userName)) {
                // Put name and stream to hash table
                Server.clientsSocket.put(userName, pwrite);
                Server.countClient++;
                // Add log
                Server.log.add("Welcome! " + userName);
                // Send the size of client
                pwrite.println(Server.clientsInHall.size());
                // Send the client's list to client
                for (Map.Entry<String, Boolean> e :
                        Server.clientsInHall.entrySet()) {
                    pwrite.println(e.getKey());
                }
                // count will store offline client
                int count = 0;
                for (Map.Entry<String, Boolean> e :
                        Server.clientsInHall.entrySet()) {
                    if (!e.getValue()) {
                        count++;
                    }
                }
                // It's just to make patching in the code
                int tempCountOne = 1;
                int tempCountTwo = 1;
                // Accept the client request for read file, chat and etc.
                String client = "";
                while (true) {
                    System.out.println("This is Thread: " + this.getName());
                    if (Server.countClient > 2 ) {
//                        if (tempCountTwo == 1){
//                            pwrite.println("ready");
//                            tempCountTwo++;
//                        }
                        System.out.println("This is Thread: " + this.getName());
                        System.out.println(this.getName());
                        System.out.println("Hey I entered my name is: " + this.getName());
                        // read sent data from client
                        dataFromClient = receiveRead.readLine();
                        System.out.println(dataFromClient);
                        // if client send close request, just shutdown for that particular client
                        if (dataFromClient.equals("::closeIt::")) {
                            break;
                        }
                        String[] checkClose = dataFromClient.split("#");
                        if (checkClose[0].equals("closeIt")){
                            Server.clientsInHall.put(checkClose[1], false);
                        }
                        // differentiate the signals
                        String[] dataSignal = dataFromClient.split(";");
                        // store the signal
                        String signal = dataSignal[dataSignal.length - 2];
                        String clientName = dataSignal[dataSignal.length - 1];

                        // If signal sent by client is chat
                        if (signal.equals("chat")) {
                            if (tempCountOne == 1) {
//                            pwrite.println(count);
                                tempCountOne++;
                            }
                            // Store the data and separate the messages
                            String dataClient = dataSignal[0];
                            String data = dataClient.substring(0, 9);
                            String clientOrMessage = dataClient.substring(10);

                            if (data.equals("13041997m")) {
                                System.out.println("Yes! twice");
                                System.out.println("Map" + Server.clientsSocket);
                                System.out.println("Client" + clientName);
                                PrintWriter pr = Server.clientsSocket.get(clientName);
                                System.out.println("data set to: " + clientName + "Printwriter is" + pr);
                                // add log
                                Server.log.add(userName + "is sent " + clientOrMessage + " to the " + clientName + " and it is Online!");
                                System.out.println(userName + "is sent " + clientOrMessage + " to the " + clientName + " and it is Online!");
                                pr.println(clientOrMessage);
                            }
                        }
                        // Set client status
//                        Server.clientsInHall.put(userName, false);
                    }
                }
            } else {
                pwrite.println("close");
//                pwrite.println(Server.clientsInHall.size());
                System.out.println("Sorry this username been already in use");
            }
            // Close all the stream
            pwrite.close();
            ostream.close();
            istream.close();
            s.close();
            ssc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // add log
        Server.log.add(this.getName() + " is going to be closed!");
    }
}
