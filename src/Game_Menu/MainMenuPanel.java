package Game_Menu;

import Game_Menu.Story_Mode.TutorialQuest;  // Import the TutorialQuest class
import Parent.GameFrame;
import Parent.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * The MainMenuPanel class extends GamePanel and serves as the main menu of the game.
 * It displays options for navigating to different game modes and settings.
 */
public class MainMenuPanel extends GamePanel {

    private ImageIcon gifIcon;  // Icon for the background GIF

    /**
     * Constructor for MainMenuPanel.
     * Sets up the background image and initializes components.
     *
     * @throws IOException if there's an error loading resources.
     */
    public MainMenuPanel() throws IOException {
        super();
        setBackgroundImage();  // Set a random background image
        initializeComponents();  // Initialize UI components
    }

    /**
     * Sets a random background image from a predefined list.
     */
    public void setBackgroundImage() {
        String[] gifPaths = {"Menu_Background/background_1.gif", "Menu_Background/background_2.gif"};
        Random rand = new Random();
        String selectedGif = gifPaths[rand.nextInt(gifPaths.length)];
        gifIcon = new ImageIcon(selectedGif);  // Set the selected GIF as the background image
    }

    /**
     * Initializes UI components for the main menu.
     *
     * @throws IOException if there's an error initializing components.
     */
    public void initializeComponents() throws IOException {
        setLayout(new BorderLayout());

        // Create an options panel with a grid layout
        JPanel optionsPanel = new JPanel(new GridLayout(1, 1, 0, 50));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(200, 400, 200, 400));  // Adjust the padding as needed
        optionsPanel.setOpaque(false);  // Make the panel transparent

        // Add buttons to the options panel
        optionsPanel.add(createButton("Story Mode", new TutorialQuest()));  // Change to TutorialQuest

        // Add the options panel to the center of the main menu panel
        add(optionsPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a JButton for navigating to a specific panel.
     *
     * @param text      The text to display on the button.
     * @param nextPanel The panel to display when the button is clicked.
     * @return The created JButton.
     */
    public JButton createButton(String text, JPanel nextPanel) {
        JButton button = new JButton(text);
        button.setFont(getModernFont().deriveFont(48f));  // Set the font for the button
        button.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof GameFrame) {
                ((GameFrame) parentFrame).showNextPanel(nextPanel);  // Show the next panel when the button is clicked
            }
        });
        return button;
    }

    /**
     * Custom painting method to draw the background GIF.
     *
     * @param g The Graphics object for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);  // Draw the background GIF
    }
}
