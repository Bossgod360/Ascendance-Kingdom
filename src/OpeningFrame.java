import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import Game_Menu.MainMenuPanel;
import Parent.GameFrame;

/**
 * The OpeningFrame class extends GameFrame to create the initial opening screen with a GIF background.
 * It includes methods to initialize components and handle user input to transition to the main menu.
 */
public class OpeningFrame extends GameFrame {

    /**
     * Constructor for the OpeningFrame.
     * Initializes components and sets up event listeners.
     *
     * @throws IOException if there's an error loading resources.
     * @throws FontFormatException if the font files are not in the correct format.
     */
    public OpeningFrame() throws  IOException, FontFormatException {
        super();  // Call the constructor of GameFrame
        initializeComponents();  // Initialize components for the opening frame
        addEventListeners();  // Add event listeners for user interactions
    }

    /**
     * Initializes components for the opening frame.
     * Loads the GIF image and creates the opening panel.
     */
    public void initializeComponents() {
        setGifIcon("opening.gif");  // Load the GIF image
        JPanel openingPanel = createOpeningPanel();  // Create the opening panel
        add(openingPanel);  // Add the opening panel to the frame
    }

    /**
     * Creates the opening panel with a GIF background and text overlay.
     *
     * @return JPanel The opening panel.
     */
    public JPanel createOpeningPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGif(g);  // Draw the GIF background
                drawTextOverlay(g);  // Draw the text overlay
            }

            public void drawGif(Graphics g) {
                g.drawImage(getGifIcon().getImage(), 0, 0, getWidth(), getHeight(), this);  // Draw the GIF image
            }

            public void drawTextOverlay(Graphics g) {
                g.setFont(getModernFont().deriveFont(55f));
                g.setColor(Color.WHITE);
                drawCenteredString(g, "Ascendance Kingdom", 2 * getWidth() - getWidth()/2, getHeight() - (getWidth()/4));

                g.setFont(getModernFont().deriveFont(45f));
                drawCenteredString(g, "Start Press Enter", getWidth()-getWidth()/2-getWidth()/10, getHeight() - getHeight()/11);
            }

            public void drawCenteredString(Graphics g, String text, int width, int y) {
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                int x = (width - metrics.stringWidth(text)) / 2;  // Calculate the horizontal position for centered text
                g.drawString(text, x, y);  // Draw the text
            }
        };
    }

    /**
     * Adds event listeners to handle user interactions.
     * Includes mouse click and Enter key press to transition to the main menu.
     */
    public void addEventListeners() {
        JPanel openingPanel = (JPanel) getContentPane().getComponent(0);  // Get the opening panel

        // Adding a mouse listener to handle click events
        openingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    showNextPanel(new MainMenuPanel());  // Transition to the main menu on mouse click
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Adding a key listener to handle Enter key press
        openingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ENTER"), "startGame");
        openingPanel.getActionMap().put("startGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showNextPanel(new MainMenuPanel());  // Transition to the main menu on Enter key press
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}