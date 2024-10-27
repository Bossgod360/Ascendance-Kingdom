package Game_Menu.Story_Mode;

import Parent.GamePanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The StoryModePanel class represents the story mode of the game.
 * It displays a series of scenes that tell the game's story.
 */
public abstract class StoryModePanel extends GamePanel {
    protected Deque<BackgroundPanel> scenes;  // Deque of scenes in the story mode
    protected int currentSceneIndex;  // Index of the current scene being displayed
    protected MouseAdapter mouseListener;  // Mouse listener for advancing scenes

    /**
     * Constructor for StoryModePanel.
     * Initializes the scenes and displays the first panel.
     *
     * @throws IOException if there's an error loading resources.
     */
    public StoryModePanel() throws IOException {
        scenes = new ArrayDeque<>();
        currentSceneIndex = 0;
        Background1();  // Load the initial scenes
        // Set the initial scene
        setLayout(new BorderLayout());
        startStoryMode();
    }

    /**
     * Start the story mode by showing the first scene.
     */
    public abstract void startStoryMode();

    /**
     * Loads the initial scenes for the story mode.
     *
     * @throws IOException if there's an error loading resources.
     */
    public abstract void Background1() throws IOException;

    /**
     * Progresses to the next scene in the story mode.
     */
    public void progressToNextScene() throws IOException {
        if (!scenes.isEmpty()) {
            BackgroundPanel nextScene = scenes.pollFirst();
            removeAll();
            add(nextScene, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else {
            removeAll();
            // Specific implementation in subclasses for transitioning to next part
            loadNextQuest();
            revalidate();
            repaint();
        }
    }

    /**
     * Loads the next quest or phase.
     * Specific implementation should be provided in subclasses.
     */
    protected abstract void loadNextQuest() throws IOException;
}
