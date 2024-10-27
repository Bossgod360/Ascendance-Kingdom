package Game_Menu.Story_Mode;
import Game_Menu.Story_Mode.MK.Fight;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
/**
 * Panel representing the Quest1 screen for the story mode.
 */
public class Quest1 extends StoryModePanel {
    /**
     * Constructs a Quest1 panel.
     *
     * @throws IOException if there's an error loading resources.
     */
    public Quest1() throws IOException {
        super();
    }
    /**
     * Starts the story mode by displaying the first scene and adding a mouse listener
     * to progress to the next scene on click.
     */
    @Override
    public void startStoryMode() {
        removeAll();
        add(scenes.peekFirst(), BorderLayout.CENTER);
        revalidate();
        repaint();

        // Add a mouse listener to progress to the next scene on click
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    progressToNextScene();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        addMouseListener(mouseListener);
    }
    /**
     * Loads the background panels and their corresponding text for the game over sequence.
     *
     * @throws IOException if there's an error loading resources.
     */
    @Override
    public void Background1() throws IOException {
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/alleyway_stabbed.gif", "Thug: \"Gah! You're tougher than you look! Fine, take this as a warning... Gojo's streets ain't safe for the likes of you!\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest1/powerup.png", "As Aryan catches his breath, a strange sensation begins to stir within. The encounter has awakened a latent power, a glimpse of which Aryan cannot yet comprehend."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest1/plasma_ball.png", "Aryan: \"What... was that? This feeling... it's like something inside me just... woke up.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/aurora_borealis.jpeg", "And so, in the heart of the city where power reigns supreme, a journey of destiny begins for Aryan. Unbeknownst to them, this encounter marks the first step towards unlocking his true potential and challenging the tyrannical rule of Gojo, the city's ruthless Domain King."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/city_night.jpg", "With the taste of victory still fresh, Aryan feels the stirring of newfound power within. Determined to harness this potential, they set out under the city’s neon glow, seeking further challenges to overcome."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/city_night.jpg", "Aryan: \"I can't ignore this feeling. There's more to discover about these abilities... and about myself.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/city_night.jpg", "Amidst the bustling streets and shadowed alleys, Aryan encounters more adversaries and now is going to fight the local gangs leader! Get Ready!!!"));
        // Add more scenes specific to Quest1
    }

    /**
     * Loads the next quest or transitions back to the main menu after completing the game over sequence.
     *
     * @throws IOException if there's an error loading resources.
     */
    @Override
    protected void loadNextQuest() throws IOException {
        Quest2 quest2 = new Quest2();
        // Implement logic for transitioning to the next quest, if any
        Fight gp = new Fight("Fighting_Backgrounds/dojo.jpg", 100, 20, 10,quest2,"blue");
        this.add(gp);
        gp.requestFocusInWindow();
    }
}
