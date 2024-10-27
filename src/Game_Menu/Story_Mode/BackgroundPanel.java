package Game_Menu.Story_Mode;

import Parent.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JPanel that displays a background image and overlay text at the bottom.
 */
public class BackgroundPanel extends GamePanel {

    private ImageIcon backgroundImage;
    private String foregroundText;
    private int fontSize = 30;
    private int textAreaHeightRatio = 5;  // Height ratio for the text area (1/5th of panel height)
    private int textPadding = 10;  // Padding around the text within the text area

    /**
     * Constructs a BackgroundPanel with a specified background image and foreground text.
     *
     * @param backgroundImageFileName the file name of the background image.
     * @param foregroundText          the text to display at the bottom of the panel.
     */
    public BackgroundPanel(String backgroundImageFileName, String foregroundText) {
        this.backgroundImage = new ImageIcon(backgroundImageFileName);
        this.foregroundText = foregroundText;
    }

    /**
     * Overrides the paintComponent method to draw the background image and overlay text.
     *
     * @param g the Graphics object used for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Draw the foreground text inside a black rectangle at the bottom of the screen
        if (foregroundText != null && !foregroundText.isEmpty()) {
            g.setColor(Color.BLACK);
            int textAreaHeight = getHeight() / textAreaHeightRatio;
            int textAreaY = getHeight() - textAreaHeight;
            g.fillRect(0, textAreaY, getWidth(), textAreaHeight);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            FontMetrics metrics = g.getFontMetrics();

            // Wrap text into maximum of two lines
            String[] lines = wrapText(foregroundText, metrics, getWidth() - 2 * textPadding);
            int lineHeight = metrics.getHeight() * 2; // Double line height for two lines
            int yStart = textAreaY + textPadding + metrics.getAscent() + 15; // Initial y position

            // Draw each line with left alignment
            for (int i = 0; i < lines.length; i++) {
                int x = textPadding;
                int y = yStart + i * lineHeight;
                g.drawString(lines[i], x, y);
            }
        }
    }

    /**
     * Wraps the given text into two lines based on the maximum width and font metrics.
     *
     * @param text     the text to wrap.
     * @param metrics  the FontMetrics object for calculating text dimensions.
     * @param maxWidth the maximum width for each line.
     * @return an array of two strings representing the wrapped text lines.
     */
    private String[] wrapText(String text, FontMetrics metrics, int maxWidth) {
        String[] words = text.split(" ");
        String line1 = "";
        String line2 = "";
        String currentLine = "";

        for (String word : words) {
            if (metrics.stringWidth(currentLine + word) < maxWidth) {
                currentLine += word + " ";
            } else {
                if (line1.isEmpty()) {
                    line1 = currentLine.trim();
                    currentLine = word + " ";
                } else {
                    line2 = currentLine.trim();
                    break;
                }
            }
        }
        if (line1.isEmpty()) {
            line1 = currentLine.trim();
        } else if (line2.isEmpty()) {
            line2 = currentLine.trim();
        }

        return new String[]{line1, line2};
    }
}
