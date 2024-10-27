package Parent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * The GamePanel class extends JPanel to provide a base panel for the game.
 * It includes methods to load custom fonts and set a placeholder color.
 */
public class GamePanel extends JPanel {

    private Font pixelatedFont;  // Font for pixelated text
    private Font modernFont;  // Font for modern text


    /**
     * Constructor for the GamePanel.
     * Sets up default properties and loads custom fonts.
     */
    public GamePanel() {
        try {
            loadFonts();  // Load custom fonts
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
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
     * Retrieves the modern font used by this application.
     *
     * @return The modern {@link Font} used for text rendering.
     */
    public Font getModernFont() {
        return modernFont;
    }
}
