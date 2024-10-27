package Game_Menu.Story_Mode.MK.Entity;
import Game_Menu.Story_Mode.MK.Fight;
import Game_Menu.Story_Mode.StoryModePanel;
import Game_Menu.Story_Mode.Quest1;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NPC extends Entity {
    Fight gp;
    Player player;
    Random rand = new Random();
    private StoryModePanel quest;
    private Timer deathTimer;

    // Animation frames
    private BufferedImage left1, left2, left3, left4, right1, right2, right3, right4;
    private BufferedImage punch1, punch2, punch3, punch4, kick1, kick2, kick3, kick4;
    private BufferedImage stance1, stance2, stance3, stance4, hurt;

    // States
    private boolean attacking = false;
    private String action = "idle"; // idle, punch, kick
    private int stallCounter = 0;
    private boolean moving = false;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = screenSize.width;
    private int screenHeight = screenSize.height;
    private boolean isHurt = false;
    private int hurtCounter = 0;
    private int knockbackCounter = 0;
    private int knockbackDirection = 0; // -1 for left, 1 for right
    private int knockbackRemaining = 0;
    private int knockbackDuration = 25; // Duration of the hurt state and knockback
    private int knockbackDistance = 30;
    BufferedImage playerSpriteImage = ImageIO.read(new File("Gojo_Avatar/Stance1.png"));
    int width = playerSpriteImage.getWidth();
    int height = playerSpriteImage.getHeight();
    String skinColour;
    /**
     * Constructs an NPC (Non-Player Character) object for the game fight scene.
     *
     * @param gp The Fight panel where the NPC will be displayed and interacted with.
     * @param player The Player object that interacts with this NPC.
     * @param health The initial health of the NPC.
     * @param skinColour The color of the NPC.
     * @param quest The StoryModePanel associated with the current quest or story mode.
     * @throws IOException If there is an error loading NPC images.
     */
    public NPC(Fight gp, Player player, int health, String skinColour, StoryModePanel quest) throws IOException {
        this.gp = gp;
        this.player = player;
        this.health = health;
        maxHealth = 100;
        solidArea = new Rectangle(x, y, width, height);
        this.quest = quest;
        this.skinColour = skinColour;
        setDefaultValues();
        loadImages();
    }

    /**
     * Sets default values for the NPC's position, speed, direction, and sprite state.
     */
    public void setDefaultValues() {
        x = screenWidth - screenWidth / 9; // 10% from the left edge
        y = (int) (screenHeight * 0.55); // 55% from the top edge
        speed = screenWidth / 300;
        direction = "idle";
        spriteCounter = 0;
        spriteNum = 1;
    }

    /**
     * Loads all required images for the NPC's animation frames.
     */
    private void loadImages() {
        try {
            // Load images
            stance1 = ImageIO.read(new File("Gojo_Avatar/Stance1.png"));
            stance2 = ImageIO.read(new File("Gojo_Avatar/Stance2.png"));
            stance3 = ImageIO.read(new File("Gojo_Avatar/Stance3.png"));
            stance4 = stance1; // Reuse stance1 for the fourth frame
            left1 = ImageIO.read(new File("Gojo_Avatar/walk1.png"));
            left2 = ImageIO.read(new File("Gojo_Avatar/walk2.png"));
            left3 = ImageIO.read(new File("Gojo_Avatar/walk3.png"));
            left4 = ImageIO.read(new File("Gojo_Avatar/walk4.png"));
            right1 = left1; // Reuse left images for right
            right2 = left2;
            right3 = left3;
            right4 = left4;
            punch1 = ImageIO.read(new File("Gojo_Avatar/smash1.png"));
            punch2 = ImageIO.read(new File("Gojo_Avatar/smash2.png"));
            punch3 = punch1; // Reuse punch1 for the third frame
            punch4 = punch2; // Reuse punch2 for the fourth frame
            kick1 = ImageIO.read(new File("Gojo_Avatar/front_kick1.png"));
            kick2 = ImageIO.read(new File("Gojo_Avatar/front_kick2.png"));
            kick3 = ImageIO.read(new File("Gojo_Avatar/front_kick3.png"));
            kick4 = ImageIO.read(new File("Gojo_Avatar/front_kick4.png"));
            hurt = ImageIO.read(new File("Gojo_Avatar/hurt.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load player images", e);
        }
    }
    /**
     * Updates the NPC's state, including position, attacking status, and animation frames.
     */
    public void update() {
        updatePlayerDistance();
        handleHurtState();
        handleMovement();
        handleAttackingState();
        updateSpriteAnimation();
        updateKnockback();
    }

    /**
     * Updates the distance between the NPC and the player.
     */
    private void updatePlayerDistance() {
        int playerX = player.x;
        int dx = playerX - x;
        double distance = Math.abs(dx);
    }

    /**
     * Handles the hurt state of the NPC, including the hurt counter.
     */
    private void handleHurtState() {
        if (isHurt) {
            hurtCounter++;
            if (hurtCounter > knockbackDuration) {
                isHurt = false;
                hurtCounter = 0;
            }
        }
    }

    /**
     * Handles the movement logic of the NPC, including stalling and moving towards the player.
     */
    private void handleMovement() {
        if (!moving) {
            stallCounter++;
            if (stallCounter > rand.nextInt(100) + 50) { // Random delay before moving
                moving = true;
                stallCounter = 0;
            }
        } else if (!isHurt) {
            double distance = Math.abs(player.x - x);
            if (distance > 100) {
                moveTowardsPlayer();
            } else {
                startAttacking();
            }
        }
    }

    /**
     * Moves the NPC towards the player's position.
     */
    private void moveTowardsPlayer() {
        int dx = player.x - x;
        direction = dx > 0 ? "right" : "left";
        x += dx > 0 ? speed : -speed;
        attacking = false;
    }

    /**
     * Starts the attacking state of the NPC.
     */
    private void startAttacking() {
        attacking = true;
        spriteCounter = 0;
        spriteNum = 1;
        action = rand.nextBoolean() ? "punch" : "kick"; // Alternate between punch and kick
        moving = false; // Stop moving when attacking
    }

    /**
     * Handles the attacking state and sprite animation of the NPC.
     */
    private void handleAttackingState() {
        if (!attacking) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum % 4) + 1; // Loop through 1, 2, 3, 4
                spriteCounter = 0;
            }
        } else {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum++;
                spriteCounter = 0;
                if (spriteNum > 4) {
                    spriteNum = 1;
                    attacking = false;
                }
            }
        }
    }

    /**
     * Updates the sprite animation based on the current state.
     */
    private void updateSpriteAnimation() {
        double distance = Math.abs(player.x - x);
        if (!attacking && distance > 100) {
            action = "idle";
        }
    }

    /**
     * Draws the NPC on the screen with appropriate image, position, and size.
     * @param g2 the Graphics2D context to draw the NPC.
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (isHurt) {
            image = hurt;
        } else {
            if (attacking) {
                switch (action) {
                    case "punch":
                        if (spriteNum == 1) image = punch1;
                        else if (spriteNum == 2) image = punch2;
                        else if (spriteNum == 3) image = punch3;
                        else if (spriteNum == 4) image = punch4;
                        break;
                    case "kick":
                        if (spriteNum == 1) image = kick1;
                        else if (spriteNum == 2) image = kick2;
                        else if (spriteNum == 3) image = kick3;
                        else if (spriteNum == 4) image = kick4;
                        break;
                }
            } else {
                switch (direction) {
                    case "left":
                        if (spriteNum == 1) image = left1;
                        else if (spriteNum == 2) image = left2;
                        else if (spriteNum == 3) image = left3;
                        else if (spriteNum == 4) image = left4;
                        break;
                    case "right":
                        if (spriteNum == 1) image = right1;
                        else if (spriteNum == 2) image = right2;
                        else if (spriteNum == 3) image = right3;
                        else if (spriteNum == 4) image = right4;
                        break;
                    case "idle":
                    default:
                        if (spriteNum == 1) image = stance1;
                        else if (spriteNum == 2) image = stance2;
                        else if (spriteNum == 3) image = stance3;
                        else if (spriteNum == 4) image = stance4;
                        break;
                }
            }
        }

        int npcWidth = image.getWidth();
        int npcHeight = image.getHeight();
        double aspectRatio = (double) npcWidth / npcHeight;
        npcWidth = (int) ((screenWidth * aspectRatio) * 0.18);
        npcHeight = (int) ((npcWidth / aspectRatio));
        if (player != null && player.x < x) {
            image = flipImageHorizontally(image);
        }
// Apply skin color tint based on skinColour variable
        if ("red".equals(skinColour)) {
            image = applyRedTint(image);
        } else if ("blue".equals(skinColour)) {
            image = applyBlueTint(image);
        } else if ("yellow".equals(skinColour)) {
            image = applyYellowTint(image);
        }
        // Draw the tinted image
        g2.drawImage(image, x, y, npcWidth, npcHeight, null);

        setSolidAreaDimensions(npcWidth + 10, npcHeight + 10);
        updateSolidArea(x, y, npcWidth + 10, npcHeight + 10);
    }
    /**
     * Applies a red tint to the given image.
     * @param image the original image.
     * @return the red-tinted image.
     */
    private BufferedImage applyRedTint(BufferedImage image) {
        BufferedImage tintedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D tintedGraphics = tintedImage.createGraphics();
        tintedGraphics.drawImage(image, 0, 0, null);

        // Apply red tint by manipulating color channels
        for (int y = 0; y < tintedImage.getHeight(); y++) {
            for (int x = 0; x < tintedImage.getWidth(); x++) {
                int rgb = tintedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Increase the red channel to add a red tint
                red = Math.min(red + 50, 255); // Adjust the intensity of the red tint here

                // Create the tinted color
                int tintedRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                tintedImage.setRGB(x, y, tintedRGB);
            }
        }

        return tintedImage;
    }
    /**
     * Applies a blue tint to the given image.
     * @param image the original image.
     * @return the blue-tinted image.
     */
    private BufferedImage applyBlueTint(BufferedImage image) {
        BufferedImage tintedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D tintedGraphics = tintedImage.createGraphics();
        tintedGraphics.drawImage(image, 0, 0, null);

        // Apply blue tint by manipulating color channels
        for (int y = 0; y < tintedImage.getHeight(); y++) {
            for (int x = 0; x < tintedImage.getWidth(); x++) {
                int rgb = tintedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Increase the blue channel to add a blue tint
                blue = Math.min(blue + 50, 255); // Adjust the intensity of the blue tint here

                // Create the tinted color
                int tintedRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                tintedImage.setRGB(x, y, tintedRGB);
            }
        }

        return tintedImage;
    }
    /**
     * Applies a yellow tint to the given image.
     * @param image the original image.
     * @return the yellow-tinted image.
     */
    private BufferedImage applyYellowTint(BufferedImage image) {
        BufferedImage tintedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D tintedGraphics = tintedImage.createGraphics();
        tintedGraphics.drawImage(image, 0, 0, null);

        // Apply yellow tint by manipulating color channels
        for (int y = 0; y < tintedImage.getHeight(); y++) {
            for (int x = 0; x < tintedImage.getWidth(); x++) {
                int rgb = tintedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Increase the red and green channels to add a yellow tint
                red = Math.min(red + 50, 255); // Adjust the intensity of the red tint here
                green = Math.min(green + 50, 255); // Adjust the intensity of the green tint here

                // Create the tinted color
                int tintedRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                tintedImage.setRGB(x, y, tintedRGB);
            }
        }

        return tintedImage;
    }
    /**
     * Flips the given image horizontally.
     * @param image the original image.
     * @return the horizontally flipped image.
     */
    private BufferedImage flipImageHorizontally(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();
        return flippedImage;
    }

    /**
     * Sets the NPC to a hurt state, resetting the hurt counter.
     * @param hurt the hurt state to set.
     */
    public void setHurt(boolean hurt) {
        isHurt = hurt;
        hurtCounter = 0; // Reset the hurt counter when hurt state is set
    }

    /**
     * Applies knockback to the NPC in the specified direction for a given distance.
     * @param distance the distance of the knockback.
     * @param direction the direction of the knockback ("left" or "right").
     */
    public void knockback(int distance, String direction) {
        knockbackDistance = distance;
        knockbackCounter = knockbackDuration; // Set knockback duration
        knockbackDirection = direction.equals("left") ? -1 : 1; // Determine direction (-1 for left, 1 for right)
        knockbackRemaining = knockbackDistance; // Start with full knockback distance
    }
    /**
     * Updates the NPC's position and state during knockback.
     */
    public void updateKnockback() {
        if (knockbackCounter > 0) {
            // Calculate distance to move this update
            int moveDistance = Math.min(knockbackRemaining, knockbackDistance / knockbackDuration);

            // Check boundaries before moving
            if (knockbackDirection == -1) { // Knockback to the left
                if (x - moveDistance >= 0) {
                    x -= moveDistance * knockbackDirection;
                } else {
                    x = 0; // Set x to the left edge of the screen
                }
            } else { // Knockback to the right
                if (x + moveDistance + width <= screenWidth) {
                    x += moveDistance * knockbackDirection;
                } else {
                    x = screenWidth - width; // Set x to the right edge of the screen
                }
            }

            knockbackRemaining -= moveDistance;
            knockbackCounter--;

            if (knockbackCounter == 0) {
                knockbackDistance = 0; // Reset knockback distance after knockback ends
            }
        }
    }
    /**
     * Reduces the NPC's health by the specified damage amount and handles death or hurt state.
     * @param damage the amount of damage taken.
     * @throws IOException if an I/O error occurs during quest transition.
     */
    public void takeDamage(int damage) throws IOException {
        // Reduce health by the amount of damage
        health -= damage;
        if (health <= 0) {
            // NPC is dead
            health = 0; // Ensure health is zero
            stopAndTransitionToQuest();
        } else {
            setHurt(true); // Set the hurt state when taking damage
        }
    }

    /**
     * Stops the current game and transitions to the quest panel.
     * @throws IOException if an I/O error occurs during quest transition.
     */
    public void stopAndTransitionToQuest() throws IOException {
        gp.stopGame(); // Stop the game logic

        Container parent = gp.getParent();
        if (parent != null) {
            parent.removeAll(); // Remove all components from the parent container
            parent.revalidate();
            parent.repaint();
        }

        parent.add(quest); // Add the quest panel
        quest.startStoryMode(); // Start the quest
        parent.revalidate();
        parent.repaint();
    }

    /**
     * Gets the current action of the NPC.
     * @return the current action.
     */
    public String getAction() {
        return action; // Assuming 'action' is a member variable in Player class
    }

    /**
     * Gets the current sprite number of the NPC.
     * @return the current sprite number.
     */
    public int getSpriteNum() {
        return spriteNum; // Assuming 'spriteNum' is a member variable in Player class
    }
    /**
     * Gets the current direction of the NPC.
     * @return the current direction.
     */
    public String getDirection() {
        return direction; // Assuming 'direction' is a member variable in Player class
    }
    /**
     * Checks if the NPC is in a hurt state.
     * @return true if the NPC is hurt, false otherwise.
     */
    public boolean isHurt() {
        return isHurt;
    }
}
