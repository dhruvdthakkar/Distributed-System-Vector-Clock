
/*
 * Name: Dhruv Dhananjaybhai Thakkar
 * Id: 1001815888
 *
 * */

package sample.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static sample.client.ClientLogic.pwrite;

/*
    https://www.callicoder.com/javafx-registration-form-gui-tutorial/ get code for UI from this tutorial,
    Same tutorial has this GitHub repo: https://github.com/callicoder/javafx-examples/tree/master/javafx-registration-form-application
*/
// Ui for selection of which type of communication client wants
public class UiForClientSelection {
    static String[] messages = null;
    public GridPane sendGridPane() {
        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
//        Scene scene = new Scene(gridPane, 800, 500);
        return gridPane;
    }

    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(20, 20, 20, 20));

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

        return gridPane;
    }

    // Add component
    private void addUIControls(GridPane gridPane) {
        selectClientList(gridPane);

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

    public void selectClientList(GridPane gridPane) {
        // Label to indicate the client
        Label headerLabel = new Label("Connected with server!");
        // set font and add to layout and set style format
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add close Button
        Button closeButton = new Button("Fetch Updated clock");
        // Edit style of button
        closeButton.setPrefHeight(40);
        closeButton.setDefaultButton(true);
        closeButton.setPrefWidth(200);

        // Add to layout and set style
        gridPane.add(closeButton, 0, 4, 2, 1);
        GridPane.setHalignment(closeButton, HPos.LEFT);
//        GridPane.setMargin(closeButton, new Insets(20, 0, 20, 0));

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            // handle event
            public void handle(ActionEvent event) {
                // Add Header
                Label headerLabel = new Label("Connected with server" + Main.vectorClock);

                // set font and add to layout and set style format
                headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                gridPane.add(headerLabel, 0, 0, 2, 1);
                GridPane.setHalignment(headerLabel, HPos.CENTER);
                GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));


                ListView<String> clientList = new ListView<>();
                // Iterate client data and add to list
                for (String s : Main.vc) {
                    clientList.getItems().add(s);
                }
                // Add whole list to layout and set styles
                gridPane.add(clientList, 0, 0, 2, 1);
                GridPane.setHalignment(clientList, HPos.RIGHT);
                GridPane.setMargin(clientList, new Insets(10, 0, 10, 20));
            }
        });
        // Add close Button
        Button closeButton2 = new Button("Close");
        // Edit style of button
        closeButton2.setPrefHeight(40);
        closeButton2.setDefaultButton(true);
        closeButton2.setPrefWidth(100);

        // Add to layout and set style
        gridPane.add(closeButton2, 0, 4, 2, 1);
//        GridPane.setHalignment(closeButton, HPos.CENTER);
//        GridPane.setMargin(closeButton, new Insets(20, 0, 20, 0));

        closeButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            // handle event
            public void handle(ActionEvent event) {
                pwrite.println("closeIt#"+Main.thisUser);
                Main.rootStage.close();
            }
        });
    }
}