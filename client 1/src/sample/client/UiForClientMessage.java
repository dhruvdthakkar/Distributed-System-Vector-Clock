
/*
 * Name: Dhruv Dhananjaybhai Thakkar
 * Id: 1001815888
 *
 * */

package sample.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Map;

/*
    https://www.callicoder.com/javafx-registration-form-gui-tutorial/ get code for UI from this tutorial,
    Same tutorial has this GitHub repo: https://github.com/callicoder/javafx-examples/tree/master/javafx-registration-form-application
*/
public class UiForClientMessage {

    public GridPane sendGridPane() {
        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
//        Scene scene = new Scene(gridPane, 800, 500);
        return gridPane;
    }


    // create layout
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

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Enter your Message!");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        // Add label to layout and set alignments
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add Name Text Field
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(80);
        gridPane.add(textArea, 1, 1);

        // Add Submit Button
        Button submitButton = new Button("Send");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        // Add button to layout and set alignments
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));


        // handle the event
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // If the textBox is empty
                if (textArea.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your Message");
                }
                // Else it sends message to server in agreed format
                else {
                    for (Map.Entry<String, Boolean> e :
                            Main.selectClientForMessage.entrySet()) {
                        if (e.getValue()) {
                            // Send client name
                            Main.c.sendMessage("13041997c-" + e.getKey() + ":chat");
                        }
                    }
                    // Send Message
                    Main.c.sendMessage("13041997m-" + textArea.getText() + ":chat");
                    // Show alert on successfully sent data
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Data send successfully!", "Data send successfully!");
                    // close window
                    UiForClientList.rootStage.close();
                }
                try {
                    // If client is not there get size of it.
                    int offLine = Integer.parseInt(ClientLogic.clientNotHere());
                    String s = "";
                    // Make format
                    for (int i = 0; i < offLine; i++) {
                        s = s + ", " + ClientLogic.clientNotHere();
                    }
                    s = "Offline users: " + s;
                    // Show client is offline alert
                    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Offline Client", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // show alert
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}