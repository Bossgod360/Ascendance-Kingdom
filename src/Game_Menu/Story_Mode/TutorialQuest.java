package Game_Menu.Story_Mode;

import Game_Menu.Story_Mode.MK.Fight;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
/**
 * Panel representing the Tutorial Quest screen for the story mode.
 */
public class TutorialQuest extends StoryModePanel {
    /**
     * Constructs a Tutorial Quest panel.
     *
     * @throws IOException if there's an error loading resources.
     */
    public TutorialQuest() throws IOException {
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
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/city_night.jpg", "In a world where power reigns supreme, the title of 'Domain King' is coveted by many but held by only one. In this city, strength and ruthlessness determine who rules, and none have been more ruthless than Gojo."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/gojo.png", "Gojo, a prodigy with cosmic-level abilities, seized control at a young age, mercilessly defeating all who stood in his way. His iron fist and unparalleled strength have made him the undisputed ruler of the city."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/gojo_bodies.png", "With his unparalleled strength and ruthless nature, Gojo took control of the city, ruling it with an iron fist. None dared to challenge his reign."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/cozy_apartment.jpg", "But amidst this darkness, there exists a spark of hope. Unbeknownst to him, a young man named Aryan possesses the potential to become even stronger than Gojo. For now, Aryan lives a carefree, normal life, unaware of the immense power within him."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/cozy_apartment.jpg", "Life is good. I’ve got great friends, a decent job, and no worries. What more could I ask for?"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/aurora_borealis.jpeg", "Fate often has a way of revealing itself in the most unexpected moments."));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/alleyway_stabbed.gif", "Wh-why...?"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/alleyway_stabbed.gif", "In that moment, the hidden power within him awakens. He began boiling with anger… pure rage. This can’t be the end, THIS CAN’T BE!!!!!"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/domain.gif", "Recovered in the alleyway. This... this power… what did I just feel?"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/gojo_smirk.png", "Gojo smirks: \"Things were getting boring.\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/alleyway_stabbed.gif", "*Back where our hero is* Thug: \"Hey you! Looks like we got ourselves a fresh face. Think you can just wander through here without paying the toll?\""));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/tutorial_screen.jpg", "Tutorial Quest Time!!! Movement is left is A right is D, Punch is J, Kick is K!"));
        scenes.add(new BackgroundPanel("Storyline_Pictures/Tutorial/tutorial_screen.jpg", "Use your attacks to defeat the thug. Watch your health bar!"));
    }

    /**
     * Loads the next quest or transitions back to the main menu after completing the game over sequence.
     *
     * @throws IOException if there's an error loading resources.
     */
    @Override
    protected void loadNextQuest() throws IOException {
        // Initialize Quest1 only when needed
        StoryModePanel quest1 = new Quest1();
        Fight gp = new Fight("Fighting_Backgrounds/Alley-Way.png", 100, 10, 30, quest1,"red");
        this.add(gp);
        gp.requestFocusInWindow();
    }
}
