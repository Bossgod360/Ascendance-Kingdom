package Game_Menu.Story_Mode;
import Game_Menu.Story_Mode.MK.Fight;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
/**
 * Panel representing the Quest3 screen for the story mode.
 */
public class Quest3 extends StoryModePanel {
    /**
     * Constructs a Quest3 panel.
     *
     * @throws IOException if there's an error loading resources.
     */
    public Quest3() throws IOException {
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
        scenes.add(new BackgroundPanel("Fighting_Backgrounds/miniboss.gif", "Gojo's Right Hand: \"Hah... you think this changes anything? You're still just a pawn in Gojo's game. Your friend... just a message to you.\""));
        scenes.add(new BackgroundPanel("Fighting_Backgrounds/miniboss.gif", "Aryan: \"Tell me how to save him, now!\" Gojo's Right Hand: \"You'll need more power... power that only the strongest possess. Good luck... if you can survive.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/domain.gif", "With the mini-boss defeated, Aryan learns that breaking the curse requires immense strength, far beyond his current capabilities. "));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/domain.gif", "Aryan: \"Hang on, I promise I'll save you. I'll become strong enough to break this curse, no matter what it takes.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest2/hospital.jpg", "The quest to become the Domain King now carries a deeper, more personal mission. With his friend's life hanging in the balance, Aryan must push beyond his limits!"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest3/gojo_top.png", "Aryan stands face-to-face with Gojo, the tyrant whose iron grip has strangled the city for far too long. The final battle for the title of Domain King!"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest3/gojo_top.png", "Atop a skyscraper overlooking the sprawling cityscape, Gojo stands with an aura of arrogance and power"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest3/gojo_top.png", "Gojo: \"Ah, Aryan. You've come farther than I anticipated. But this city belongs to me, and I'll crush anyone who dares challenge my rule.\" Aryan: \"Your tyranny ends here, Gojo. I fight not just for myself, but for the people you've oppressed.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest3/gojo_top.png", "Gojo: \"You think you can defeat me? Pathetic. I am the true ruler of this city, and you are nothing but a fleeting shadow.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest3/gojo_top.png", "FIGHT TIME!!!! FINAL SHOWDOWN!!!"));
        // Add more scenes specific to Quest1
    }
    /**
     * Loads the next quest or transitions back to the main menu after completing the game over sequence.
     *
     * @throws IOException if there's an error loading resources.
     */
    @Override
    protected void loadNextQuest() throws IOException {
        GameOver GameOver = new GameOver();
        // Implement logic for transitioning to the next quest, if any
        Fight gp = new Fight("Fighting_Backgrounds/rooftop.jpg", 100, 10, 30, GameOver,"yellow");
        this.add(gp);
        gp.requestFocusInWindow();
    }
}
