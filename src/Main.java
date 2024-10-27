/*
Ascendance Kingdom
It's an open-world adventure game where the player is on a journey to become the “Domain King”.
Written by: Aryan Khimani
Last modified: June 20, 2024

 */

import java.awt.*;  // Import the AWT library for graphics and windowing
import java.io.IOException;  // Import IOException for handling IO exceptions

/**
 * The Main class is the entry point of the application.
 * It creates and displays the opening scene of the game.
 */
public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {
        openOpeningScene();
    }

    /**
     * Opens the opening scene of the game.
     *
     * @throws IOException If there is an error loading resources or files.
     * @throws FontFormatException If there is an error with font formatting.
     */
    public static void openOpeningScene() throws IOException, FontFormatException {
        // Create an instance of the OpeningFrame class
        OpeningFrame openingFrame = new OpeningFrame();
        // Set the opening frame to be visible
        openingFrame.setVisible(true);
    }
     
}
