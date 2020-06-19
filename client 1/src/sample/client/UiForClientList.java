
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
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/*
    https://www.callicoder.com/javafx-registration-form-gui-tutorial/ get code for UI from this tutorial,
    Same tutorial has this GitHub repo: https://github.com/callicoder/javafx-examples/tree/master/javafx-registration-form-application
*/
public class UiForClientList extends Application {

    // root stage for show scene
    public static Stage rootStage;
    // get client message UI

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        // return gridPane after updating
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Enter the username!");
        // set font and add to layout and set style format
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
        // Add Name Label and add to layout
        Label nameLabel = new Label("UserName: ");
        gridPane.add(nameLabel, 0, 1);
        // Add Name Text Field and edit style
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1, 1);
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
                if (nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
                    return;
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