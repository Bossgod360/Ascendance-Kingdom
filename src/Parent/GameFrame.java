package Parent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The GameFrame class extends JFrame to provide a base frame for the game.
 * It includes methods to load custom fonts and switch between different panels.
 */
public class GameFrame extends JFrame {

    private ImageIcon gifIcon;  // ImageIcon to hold the GIF image
    private Font pixelatedFont;  // Font for pixelated text
    private Font modernFont;  // Font for modern text

    /**
     * Constructor for the GameFrame.
     * Sets up the frame properties and loads custom fonts.
     *
     * @throws IOException if there's an error loading font files.
     * @throws FontFormatException if the font files are not in the correct format.
     */
    public GameFrame() throws IOException, FontFormatException {
        loadFonts();  // Load custom fonts
        setTitle("Ascendance Kingdom");  // Set the title of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set the default close operation
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize the frame to full screen
        pack();
        setLayout(new BorderLayout());  // Set the layout to BorderLayout
        setFocusable(true);
    }

    /**
     * Loads custom fonts from the specified file paths.
     *
     * @throws IOException if there's an error loading font files.
     * @throws FontFormatException if the font files are not in the correct format.
     */
    private void loadFonts() throws IOException, FontFormatException {
        pixelatedFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Press_Start_2P/PressStart2P-Regular.ttf")).deriveFont(48f);
        modernFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Orbitron/Orbitron-VariableFont_wght.ttf")).deriveFont(32f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(pixelatedFont);
        ge.registerFont(modernFont);
    }

    /**
     * Replaces the current panel with the specified next panel.
     *
     * @param nextPanel The next panel to be displayed.
     */
    public void showNextPanel(JPanel nextPanel) {
        getContentPane().removeAll();  // Remove all components from the current frame
        getContentPane().add(nextPanel);  // Add the next panel
        revalidate();  // Revalidate the frame to reflect the changes
        repaint();  // Repaint the frame to update the visual appearance
    }

    // Getter methods for private variables if needed by subclasses
    /**
     * Sets the GIF icon using the specified file path.
     *
     * @param gifIcon The file path of the GIF icon.
     */
    public void setGifIcon(String gifIcon) {
        this.gifIcon = new ImageIcon(gifIcon);
    }
    /**
     * Returns the GIF icon currently set.
     *
     * @return The ImageIcon object representing the GIF icon.
     */
    public ImageIcon getGifIcon() {
        return gifIcon;
    }

    /**
     * Returns the modern font used in the application.
     *
     * @return The Font object representing the modern font.
     */
    public Font getModernFont() {
        return modernFont;
    }

}