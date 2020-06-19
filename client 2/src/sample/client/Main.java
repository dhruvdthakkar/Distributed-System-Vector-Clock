
/*
 * Name: Dhruv Dhananjaybhai Thakkar
 * Id: 1001815888
 *
 * */

package sample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static sample.client.ClientLogic.*;


/*
    https://www.callicoder.com/javafx-registration-form-gui-tutorial/ get code for UI from this tutorial,
    Same tutorial has this GitHub repo: https://github.com/callicoder/javafx-examples/tree/master/javafx-registration-form-application
*/


// Main client class
// Start client window from here
public class Main extends Application {
    // Store client data in map with status
    public static HashMap<String, Boolean> selectClientForMessage = new HashMap<>();
    //Creating Vector clock
    public static HashMap<String, Integer> vectorClock = new HashMap<>();
    // root window
    public static Stage rootStage;
    public static ObservableList<String> names = FXCollections.observableArrayList();
    // Connect to server for getting port number
    static ClientLogic c = new ClientLogic("127.0.0.1", 1235);
    // User name for this client
    static String thisUser;
    // flag for patch
    public boolean flagForClose = false;
    // flag for patch
    public static boolean flagReady = false;
    // Vector clock
    public static ArrayList<String> vc = new ArrayList<>();
    // where execution start
    public static void main(String[] args) {
        launch(args);
    }

    // Hit the client app.
    @Override
    public void start(Stage primaryStage) throws Exception {
        // initialize the vector clock
        Main.vectorClock.put("A", 0);
        Main.vectorClock.put("B", 0);
        Main.vectorClock.put("C", 0);
        // set the title of window
        primaryStage.setTitle("Client-Server Application");
        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage
        primaryStage.setScene(scene);
        // show the layout
        primaryStage.show();
        // store reference to root layout
        rootStage = primaryStage;
        // close the window and send signal to server
        rootStage.setOnCloseRequest((WindowEvent e) -> {
            System.out.println("Okay bye!");
            // flag for close the app
            if (flagForClose) {
                pwrite.println("closeIt#" + Main.thisUser);
            }
        });
    }

    // create pane UI
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
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        // return gridPane after updating
        return gridPane;
    }

    // Add UI controls
    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Select the username!");
        // set font and add to layout and set style format
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
        // Add Name Label and add to layout
        Label nameLabel = new Label("UserName: ");
        gridPane.add(nameLabel, 0, 1);
        // Add Name Select Field and edit style
        //        Select nameField = new TextField();close
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                "A", "B", "C")
        );
        cb.setPrefHeight(40);
        gridPane.add(cb, 1, 1);
        // Add Submit Button
        Button submitButton = new Button("Check-in");
        // Edit style of button
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        // Add to layout and set style
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));
        // method where event occur for check-In button
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            // handle event
            public void handle(ActionEvent event) {
                // show error if client is empty
                /*if (nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
                    return;
                }*/

                // Create connection
                c.start();

                // Check User name
                c.checkIn((String) cb.getValue());
                try {
                    // If user already active, close it!
                    if (!c.receiveResponse().equals("close")) {
                        // Store user name in static variable
                        Main.thisUser = (String) cb.getValue();
                        // Get UI class of selection
                        UiForClientSelection clientSelection = new UiForClientSelection();
                        // create scene of it
                        Scene sceneOfClientMessage = new Scene(clientSelection.sendGridPane(), 900, 900);
                        // Set to root stage
                        rootStage.setScene(sceneOfClientMessage);
                        // Show it
                        rootStage.show();
                        flagForClose = true;
                        // Receiving the data (New Thread)
                        APart ap = new APart();
                        // Start thread
                        ap.start();
                        // Sending the data (New Thread)
                        APartSend aps = new APartSend();
                        // Start thread
                        aps.start();
                    } else {
                        // Else show error
                        showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Username error", "Enter different username! ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // Add close Button
        Button closeButton = new Button("Close");
        // Edit style of button
        closeButton.setPrefHeight(40);
        closeButton.setDefaultButton(true);
        closeButton.setPrefWidth(100);

        // Add to layout and set style
        gridPane.add(closeButton, 0, 4, 2, 1);
//        GridPane.setHalignment(closeButton, HPos.CENTER);
//        GridPane.setMargin(closeButton, new Insets(20, 0, 20, 0));

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            // handle event
            public void handle(ActionEvent event) {
                rootStage.close();
            }
        });
    }

    // Alert method
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}


class APart extends Thread {
    APart() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("I can hear!");
                // Split the data for signal
                String[] sr = receiveMessage().split(":");
                System.out.println(sr[0]);
                /*if (sr[0].equals("ready")){
                    Main.flagReady = true;
                    continue;
                }*/
                // Fetch the data from message and update.
                int forA = Integer.parseInt(sr[1]);
                int forB = Integer.parseInt(sr[3]);
                int forC = Integer.parseInt(sr[5]);
                // Check the client and update the counter
                // THis is vector clock logic
                if (Main.thisUser.equals("A")) {
                    forA = Main.vectorClock.get("A");
                    forA++;
                    Main.vectorClock.put("A", forA);
                    if (forB > Main.vectorClock.get("B")){
                        Main.vectorClock.put("B", forB);
                    }
                    if (forC > Main.vectorClock.get("C")){
                        Main.vectorClock.put("C", forC);
                    }
                } else if (Main.thisUser.equals("B")) {
                    forB = Main.vectorClock.get("B");
                    forB++;
                    Main.vectorClock.put("B", forB);
                    if (forA > Main.vectorClock.get("A")){
                        Main.vectorClock.put("A", forA);
                    }
                    if (forC > Main.vectorClock.get("C")){
                        Main.vectorClock.put("C", forC);
                    }
                } else {
                    forC = Main.vectorClock.get("C");
                    forC++;
                    Main.vectorClock.put("C", forC);
                    if (forA > Main.vectorClock.get("A")){
                        Main.vectorClock.put("A", forA);
                    }
                    if (forB > Main.vectorClock.get("B")){
                        Main.vectorClock.put("B", forB);
                    }
                }

                System.out.println(Main.vectorClock + "Sent by " + sr[sr.length - 1]);
                // Add the vector clock
                Main.vc.add(Main.vectorClock + " Received from " + sr[sr.length - 1]);
            } catch (IOException e) {
                System.out.println("I am done!");
                e.printStackTrace();
            }
        }
    }
}
// Thread for sending the data
class APartSend extends Thread {
    // Clock logic
    // ArrayList to store the client
    ArrayList<String> clients = new ArrayList<>();
    @Override
    public void run() {
        // Clock logic
        if (Main.thisUser.equals("A")) {
            clients.add("B");
            clients.add("C");
        } else if (Main.thisUser.equals("B")) {
            clients.add("A");
            clients.add("C");
        } else {
            clients.add("B");
            clients.add("A");
        }
        while (true) {
//            if (Main.flagReady){
                try {
                    System.out.println("I am sending data!");
                    // Update the same user's counter while sending
                    int countVector = Main.vectorClock.get(Main.thisUser);
                    System.out.println(Main.thisUser);
                    System.out.println(clients);
                    // increment
                    countVector++;
                    // Update
                    Main.vectorClock.put(Main.thisUser, countVector);
//                sendMessage("13041997c-" + "A" + ";chat");
                    int ss = ThreadLocalRandom.current().nextInt(0, 2);
                    String sss = clients.get(ss);
                    sendMessage("13041997m-" +
                            "A:" + Main.vectorClock.get("A") +
                            ":B:" + Main.vectorClock.get("B") +
                            ":C:" + Main.vectorClock.get("C") +
                            ":" + Main.thisUser +
                            ";chat" +
                            ";" + sss);
                    // Add the client
                    Main.vc.add(Main.vectorClock + "Sent by " + Main.thisUser + " to" + sss);
                    // Sleep the Thread
                    Thread.sleep(ThreadLocalRandom.current().nextInt(2000, 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
        }
    }
}