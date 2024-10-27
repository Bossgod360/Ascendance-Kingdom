package Game_Menu.Story_Mode.MK;

import Game_Menu.Story_Mode.MK.Entity.Entity;
import Game_Menu.Story_Mode.MK.Entity.NPC;
import Game_Menu.Story_Mode.MK.Entity.Player;
import Game_Menu.Story_Mode.StoryModePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Fight extends JPanel implements Runnable {
    private final int FPS = 60;
    private KeyHandler keyH = new KeyHandler();
    private Thread gameThread;
    private Player player;
    private NPC npc;
    private CollisionChecker collisionChecker;

    private int HEALTH_BAR_WIDTH = 350;
    private int HEALTH_BAR_HEIGHT = 35;
    private Color PLAYER_HEALTH_BAR_COLOR = Color.GREEN;
    private Color NPC_HEALTH_BAR_COLOR = Color.YELLOW;
    private Color HEALTH_BAR_BACKGROUND_COLOR = Color.RED;

    private ImageIcon backgroundImage;

    private int playerStr; // Damage NPC can inflict
    private int npcStr; // Damage Player can inflict

    /**
     * Constructs a Fight panel with the specified background image, NPC health and strength,
     * player strength, quest panel, and player skin color. Initializes the game components,
     * sets up collision detection, and starts the game thread.
     *
     * @param backgroundImageFilePath The file path to the background image for the fight scene.
     * @param npcHealth               The initial health of the NPC.
     * @param npcStr                  The damage the NPC can inflict.
     * @param playerStr               The damage the player can inflict.
     * @param quest                   The parent StoryModePanel for scene transitions.
     * @param skinColour              The color of the NPC character.
     * @throws IOException           If there is an error reading the background image file.
     */
    public Fight(String backgroundImageFilePath, int npcHealth, int npcStr, int playerStr, StoryModePanel quest, String skinColour) throws IOException {
        this.collisionChecker = new CollisionChecker(this);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        this.playerStr = playerStr;
        this.npcStr = npcStr;

        backgroundImage = new ImageIcon(backgroundImageFilePath);

        // Initialize player and NPC after setting the panel properties
        player = new Player(this, keyH);
        npc = new NPC(this, player,npcHealth,skinColour, quest); // Initialize NPC here

        // Link player to NPC
        player.npc = npc;

        // Start the game thread
        startGameThread();
    }
    /**
     * Stops the game by setting the game thread and key handler to null.
     */
    public void stopGame() {
        gameThread = null; // Stop the game thread
        keyH = null; // Clear KeyHandler
    }
    /**
     * Checks for collisions between the player and NPC and updates their states accordingly.
     *
     * @throws IOException If there is an error handling NPC actions.
     */
    public void checkCollisions() throws IOException {
        if (player == null || npc == null) {
            return;  // Ensure player and npc are initialized
        }

        // Check collision between player and NPC
        if (collisionChecker.checkCollision(player, npc)) {
            // Check if player is attacking and in a valid sprite frame
            if ((player.getAction().equals("punch") && player.getSpriteNum() == 2) ||
                    (player.getAction().equals("kick") && player.getSpriteNum() == 4)) {

                // Check if NPC is not currently hurt
                if (npc != null && !npc.isHurt()) {
                    // Perform actions on NPC
                    npc.takeDamage(playerStr);
                    npc.setHurt(true);

                    npc.knockback(230, player.getDirection()); // Assuming player.getDirection() returns direction
                }
            }

            // Check if NPC is attacking and in a valid sprite frame
            if ((npc.getAction().equals("punch") && npc.getSpriteNum() == 2) ||
                    (npc.getAction().equals("kick") && npc.getSpriteNum() == 4)) {

                // Check if player is not currently hurt
                if (player != null && !player.isHurt()) {
                    // Perform actions on player
                    player.takeDamage(npcStr);
                    player.setHurt(true);
                    player.knockback(230, npc.getDirection()); // Assuming npc.getDirection() returns direction
                }
            }
        }
    }
    /**
     * Starts the game loop thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    /**
     * The main game loop where game updates and repaints are handled at a constant FPS.
     */
    @Override
    public void run() {
        long drawInterval = 1_000_000_000 / FPS;
        long delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= drawInterval) {
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                repaint();
                delta = 0;
            }

            try {
                Thread.sleep(1); // Ensure some delay to prevent high CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Updates the game state, including player and NPC actions and collision checks.
     *
     * @throws IOException If there is an error updating player or NPC actions.
     */
    public void update() throws IOException {
        if (player != null) {
            player.update();
        }
        if (npc != null) {
            npc.update();
        }
        checkCollisions();
    }
    /**
     * Overrides JPanel's paintComponent method to paint the background, player, NPC, and health bars.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw background image
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        if (player != null) {
            player.draw(g2);
        }
        if (npc != null) {
            npc.draw(g2);
        }

        // Draw health bars
        drawHealthBar(g2, player, 50, 60, PLAYER_HEALTH_BAR_COLOR); // Example position for player health bar
        drawHealthBar(g2, npc, getWidth() - 50 - HEALTH_BAR_WIDTH, 60, NPC_HEALTH_BAR_COLOR); // NPC health bar on the right side
        g2.dispose();
    }
    /**
     * Draws a health bar for the specified entity at the given position with the specified color.
     *
     * @param g2      The Graphics2D object used for drawing.
     * @param entity  The entity (player or NPC) whose health bar is to be drawn.
     * @param barX    The x-coordinate of the health bar.
     * @param barY    The y-coordinate of the health bar.
     * @param color   The color of the health bar.
     */
    private void drawHealthBar(Graphics2D g2, Entity entity, int barX, int barY, Color color) {
        if (entity != null) {
            // Calculate dimensions and position of the health bar
            int barWidth = (int) ((double) entity.getHealth() / entity.getMaxHealth() * HEALTH_BAR_WIDTH);
            int barHeight = HEALTH_BAR_HEIGHT;

            // Draw background of health bar
            g2.setColor(HEALTH_BAR_BACKGROUND_COLOR);
            g2.fillRect(barX, barY, HEALTH_BAR_WIDTH, barHeight);

            // Draw current health level
            g2.setColor(color);
            g2.fillRect(barX, barY, barWidth, barHeight);

            // Draw border of health bar (optional)
            g2.setColor(Color.BLACK);
            g2.drawRect(barX, barY, HEALTH_BAR_WIDTH, barHeight);
        }
    }
}
