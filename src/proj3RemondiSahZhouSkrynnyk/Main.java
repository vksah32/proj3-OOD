/*
 * File: Main.java
 * Names: Alex, Mike, Vivek
 * Class: CS361
 * Project 2
 * Date: September 18, 2016
 */

package proj3RemondiSahZhouSkrynnyk;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

/**
 * This class creates a small application that asks the user for a starting note and
 * uses a midi player to play that note's scale
 *
 * @author Alex Rinker
 * @author Vivek Sah
 * @author Mike Remondi
 */
public class Main extends Application {
    final private int BEATS_PER_MINUTE = 100;
    private MidiPlayer midiPlayer = new MidiPlayer(1, BEATS_PER_MINUTE);
    final private int VOLUME = 50;
    final private int CHANNEL = 0;

    /**
     * Creates a dialog box and returns the input string provided by the user.
     *
     * @param title         the String value for the title of the dialog box
     * @param headerText    the String value for the header prompt
     * @param defaultString the String value for the default text input field
     * @return              user input String
     */
    private Optional<String> createDialogBox(
            String title, String headerText, String defaultString
    ) {
        TextInputDialog dialog = new TextInputDialog(defaultString);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);

        Optional<String> result = dialog.showAndWait();
        return result;
    }

    /**
     * Plays midi starting from the given value if it is between 0 and 115 otherwise it crashes
     *
     * @param startNote int value for starting note
     */
    private void playMidi(int startNote){
        stopMidi();
        for (int i = 0; i < 8; i++) {
            midiPlayer.addNote(startNote, VOLUME, i, 1, CHANNEL, 0);
            midiPlayer.addNote(startNote, VOLUME, 15 - i, 1, CHANNEL, 0);
            if (i == 2 || i == 6) {
                startNote += 1;
            } else {
                startNote += 2;
            }
        }
        midiPlayer.play();
    }

    /**
     * Stops the midi player and clears its sequence
     */
    private void stopMidi(){
        this.midiPlayer.stop();
        this.midiPlayer.clear();
    }

    @FXML
    /**
     * Safely exits the program without throwing an error
     *
     * @param event the event to trigger the exit.
     */
    protected void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    /**
     * Stops the midi player
     * For use in our stop button
     *
     * @param event the event which causes the midiplayer to stop
     */
    protected void handleStopButtonAction(ActionEvent event) {
        stopMidi(); // maybe just put stopMidi body in here...
    }

    @FXML
    /**
     * Creates a dialog box prompting the user for the starting note
     * for use in the midi player.
     * Then calls the playMidi method using that note (Integer)
     *
     * @param event the event which should trigger the dialog box and midiplayer combo functionality.
     */
    protected void handlePlayButtonAction(ActionEvent event) {
        Optional<String> startNote = createDialogBox(
                "Starting Note", "Give Me A Starting Note", "60");
        if (startNote.isPresent()){
            playMidi(Integer.valueOf(startNote.get()));
        }

    }

    /**
     * Initializes the FXML using the built in loader
     * Initializes the root scene of our GUI with the FXML elements
     * Adds the titles and elements to our scene
     * Returns an error message and closes if there is any issue setting up
     *
     * @param primaryStage Stage for main application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root, 300, 275);

            primaryStage.setTitle("FXML Welcome");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {System.exit(0);});
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
