package Game_Menu.Story_Mode;

import Game_Menu.MainMenuPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Panel representing the game over screen for the story mode.
 */
public class GameOver extends StoryModePanel {

    /**
     * Constructs a GameOver panel.
     *
     * @throws IOException if there's an error loading resources.
     */
    public GameOver() throws IOException {
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
        scenes.add(new BackgroundPanel("Storyline_Pictures/gojo_death.gif",
                "Gojo (struggling): \"This... this isn't over! You can't stop me...\" Aryan: \"It's over, Gojo. Your time is up.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest2/hospital.jpg",
                "Cut to the hospital where Aryan's friend lies unconscious, light streams through the hospital window, bathing Aryan's friend in a healing glow. The curse seal fades, and their friend's eyes flutter open, miraculously restored to life."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/aurora_borealis.jpeg",
                "With Gojo defeated and their friend saved, Aryan stands at the precipice of a new era in Ascendance Kingdom. Game Over :)"));
        // Add more scenes specific to Quest1 or other quests as needed
    }

    /**
     * Loads the next quest or transitions back to the main menu after completing the game over sequence.
     *
     * @throws IOException if there's an error loading resources.
     */
    @Override
    protected void loadNextQuest() throws IOException {
        MainMenuPanel gp = new MainMenuPanel();
        // Implement logic for transitioning to the next quest, if any

        this.add(gp);
        gp.requestFocusInWindow();
    }
}
