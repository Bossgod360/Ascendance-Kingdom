package Game_Menu.Story_Mode;

import Game_Menu.Story_Mode.MK.Fight;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
/**
 * Panel representing the Quest2 screen for the story mode.
 */
public class Quest2 extends StoryModePanel {
    /**
     * Constructs a Quest2 panel.
     *
     * @throws IOException if there's an error loading resources.
     */
    public Quest2() throws IOException {
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
        scenes.add(new BackgroundPanel("Fighting_Backgrounds/dojo.jpg", "Enemy: \"You're tougher than you look. But remember, this city belongs to Gojo. You're just another fly buzzing around his domain.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest1/plasma_ball.png", "With each victory, Aryan feels his latent potential growing stronger. The battles not only sharpen combat skills but also deepen Aryan's understanding of the power simmering within."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest1/powerup.png", "Aryan: \"I can feel myself getting stronger. These encounters are unlocking something inside me... something powerful.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/domain.gif", "As Aryan continues to triumph over challengers and grow in strength, whispers of his exploits spread through the city. So Gojo sends someone to kill him off"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/cozy_apartment.jpg", "*Back at his home* Friend: \"You know, I’ve always got your back, no matter what. Just... be careful out there.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest2/fire_apartment.png", "As you left to go fight off more villains, you hear a loud screaming and when you return you find your friend gravely injured, a sinister curse seal etched into his skin."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest2/hospital.jpg", "Friend (gasping): \"They... thought I was you... I'm sorry... I couldn't stop them.\" \n Aryan: \"No, this can't be happening! Who did this to you?\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest2/hospital.jpg", "Determined to uncover the truth, Aryan follows the trail left by the assassins. The journey leads to a confrontation with Gojo's second in command!"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Quest2/hospital.jpg", "You Ready!!! Fight!"));
        // Add more scenes specific to Quest1
    }
    /**
     * Loads the next quest or transitions back to the main menu after completing the game over sequence.
     *
     * @throws IOException if there's an error loading resources.
     */
    @Override
    protected void loadNextQuest() throws IOException {
        Quest3 Quest3 = new Quest3();
        // Implement logic for transitioning to the next quest, if any
        Fight gp = new Fight("Fighting_Backgrounds/miniboss.gif", 100, 15, 10, Quest3,"red");
        this.add(gp);
        gp.requestFocusInWindow();
    }
}
