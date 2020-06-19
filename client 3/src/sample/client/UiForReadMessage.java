
/*
 * Name: Dhruv Dhananjaybhai Thakkar
 * Id: 1001815888
 *
 * */

package sample.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Map;


/*
    https://www.callicoder.com/javafx-registration-form-gui-tutorial/ get code for UI from this tutorial,
    Same tutorial has this GitHub repo: https://github.com/callicoder/javafx-examples/tree/master/javafx-registration-form-application
*/


// Main client class
// Start client window from here
public class UiForReadMessage extends Application {
    public static Stage rootStage;

    // where execution start
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // set the title of window
        primaryStage.setTitle("Read Message");
        // Create the registration form grid pane
        // Add UI controls to the registration form grid pane
//        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        // show the layout
        primaryStage.show();
        // store reference to root layout
        rootStage = primaryStage;
    }

    public GridPane createRegistrationFormPane() {
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

        // return gridpane after updating
        return gridPane;
    }

    public void addUIControls(GridPane gridPane, String[] messages) {
        // Add Header
        Label headerLabel = new Label("Your Messages!");

        // set font and add to layout and set style format
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        ListView<String> clientList = new ListView<>();
        // Iterate client data and add to list
        for (int i = 0; i < messages.length; i++){
            clientList.getItems().add(messages[i]);
        }

        // Add whole list to layout and set styles
        gridPane.add(clientList, 0, 0, 2, 1);
        GridPane.setHalignment(clientList, HPos.RIGHT);
        GridPane.setMargin(clientList, new Insets(10, 0, 10, 20));

        // Add close Button
        Button closeButton = new Button("Okay, Close it!");
        // Edit style of button
        closeButton.setPrefHeight(40);
        closeButton.setDefaultButton(true);
        closeButton.setPrefWidth(100);

        // Add to layout and set style
        gridPane.add(closeButton, 0, 4, 2, 1);
        GridPane.setHalignment(closeButton, HPos.CENTER);
        GridPane.setMargin(closeButton, new Insets(20, 0, 20, 0));

        // method where event occur for check-In button
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            // handle event
            public void handle(ActionEvent event) {
                rootStage.close();
            }
        });
    }
}