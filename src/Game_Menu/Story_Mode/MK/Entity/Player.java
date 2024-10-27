package Game_Menu.Story_Mode.MK.Entity;



import Game_Menu.Story_Mode.MK.Fight;
import Game_Menu.Story_Mode.MK.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
    Fight gp;
    KeyHandler keyH;
    public NPC npc;

    // Animation frames
    private BufferedImage left1, left2, left3, left4, right1, right2, right3, right4;
    private BufferedImage punch1, punch2, punch3, punch4, kick1, kick2, kick3, kick4;
    private BufferedImage stance1, stance2, stance3, stance4, hurt;

    // States
    private boolean attacking = false;
    private String action = "idle"; // idle, punch, kick, attack
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = screenSize.width;
    private int screenHeight = screenSize.height;
    private int knockbackDistance = 30; // Distance to knock back the NPC
    BufferedImage playerSpriteImage = ImageIO.read(new File("Gojo_Avatar/Stance1.png"));
    int width = playerSpriteImage.getWidth();
    int height = playerSpriteImage.getHeight();
    private int knockbackDirection = 0; // -1 for left, 1 for right
    private int knockbackRemaining = 0;

    private boolean isHurt = false;
    private int hurtCounter = 0;
    private int knockbackCounter = 0;
    private int knockbackDuration = 30; // Duration of the hurt state and knockback


    /**
     * Constructs a Player object within the specified Fight panel, initialized with default values.
     *
     * @param gp   The Fight panel where the player exists.
     * @param keyH The KeyHandler for managing player's input controls.
     * @throws IOException If there is an error loading player-related resources.
     */
    public Player(Fight gp, KeyHandler keyH) throws IOException {
        this.gp = gp;
        this.keyH = keyH;
        health = 100; // Example initial health
        maxHealth = 100; // Example max health
        solidArea = new Rectangle(0, 0, width, height);
        setDefaultValues();
        getPlayerImage();
    }
    /**
     * Sets default values for the player, including initial position, speed, and sprite animation.
     */
    public void setDefaultValues() {
        x = screenWidth / 10; // 10% from the left edge
        y = (int) (screenHeight * 0.55); // 75% from the top edge
        speed = screenWidth / 300;

        direction = "idle";
        spriteCounter = 0;
        spriteNum = 1;
    }
    /**
     * Loads player images for various actions (stance, walking, punching, kicking, hurt).
     *
     */
    public void getPlayerImage() {
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
     * Updates the player's state based on keyboard input and game logic.
     * Handles player movement, sprite animation, attacking, hurt state, and knockback.
     * @throws IOException if an IO operation fails during the method execution.
     */
    public void update() {
        boolean keyPressed = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        // Direction handling
        if (keyPressed) {
            if (keyH.leftPressed) {
                direction = "left";
                attacking = false;
                if (x - speed >= 0) { // Check if moving left keeps player within screen bounds
                    x -= speed;
                }
            } else if (keyH.rightPressed) {
                direction = "right";
                attacking = false;
                if (x + speed + width <= screenWidth) { // Check if moving right keeps player within screen bounds
                    x += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum % 4) + 1; // Loop through 1, 2, 3, 4
                spriteCounter = 0;
            }
        } else {
            direction = "idle";
            handleIdle();
        }

        if (isHurt) {
            hurtCounter++;
            if (hurtCounter > knockbackDuration) {
                isHurt = false;
                hurtCounter = 0;
            }
        }

        // Handle attacks
        if (keyH.pressedJ) {
            action = "punch";
            attacking = true;
            spriteCounter = 0;
            spriteNum = 1;
        } else if (keyH.pressedK) {
            action = "kick";
            attacking = true;
            spriteCounter = 0;
            spriteNum = 1;
        }

        updateKnockback(); // Update knockback movement

        // Attacking logic
        if (attacking) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum++;
                spriteCounter = 0;
                if (action.equals("punch") && spriteNum > 4) {
                    spriteNum = 1;
                    attacking = false;
                    action = "idle";
                } else if (action.equals("kick") && spriteNum > 4) {
                    spriteNum = 1;
                    attacking = false;
                    action = "idle";
                }
            }
        }

        if (!keyPressed && !attacking) {
            action = "idle";
        }
    }

    /**
     * Handles the idle state of the player when no movement or attacks are happening.
     * Manages sprite animation for the idle state.
     */
    private void handleIdle() {
        if (!attacking) {
            spriteCounter++;
            if (spriteCounter > 15) {
                spriteNum = (spriteNum % 4) + 1; // Loop through 1, 2, 3, 4
                spriteCounter = 0;
            }
        }
    }
    /**
     * Draws the player character on the screen based on its current state (stance, movement, attacking, hurt).
     * Flips the image horizontally if the NPC is on the left side of the player.
     * @param g2 the Graphics2D object used for drawing.
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
                        image = flipImageHorizontally(image);
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
        int playerWidth = image.getWidth();
        int playerHeight = image.getHeight();
        double aspectRatio = (double) playerWidth /playerHeight;
        playerWidth = (int) ((screenWidth * aspectRatio)*0.18);
        playerHeight = (int) ((playerWidth / aspectRatio));

        if (npc != null && npc.x < x) {
            image = flipImageHorizontally(image);
        }
        setSolidAreaDimensions(playerWidth + 10, playerHeight + 10);
        g2.drawImage(image, x, y, playerWidth, playerHeight, null);
        updateSolidArea(x, y, playerWidth + 10, playerHeight +10);
    }
    /**
     * Flips the given image horizontally.
     * @param image the BufferedImage to flip.
     * @return the horizontally flipped BufferedImage.
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
     * Initiates a knockback effect on the player.
     * @param distance the distance to knock back.
     * @param direction the direction of knockback ("left" or "right").
     */
    public void knockback(int distance, String direction) {
        knockbackDistance = distance;
        knockbackCounter = knockbackDuration; // Set knockback duration
        knockbackDirection = direction.equals("left") ? -1 : 1; // Determine direction (-1 for left, 1 for right)
        knockbackRemaining = knockbackDistance; // Start with full knockback distance
    }

    /**
     * Updates the knockback effect, moving the player back until the knockback distance is covered.
     * Resets the knockback effect after it completes.
     */
    public void updateKnockback() {
        if (knockbackCounter > 0) {
            // Calculate distance to move this update
            int moveDistance = Math.min(knockbackRemaining, knockbackDistance / knockbackDuration);

            // Check boundaries
            if (x + moveDistance < 0) {
                x = 0;
            } else if (x + moveDistance + width> screenWidth) {
                x = screenWidth - width;
            } else {
                x += moveDistance * knockbackDirection;
            }

            knockbackRemaining -= moveDistance;
            knockbackCounter--;

            if (knockbackCounter == 0) {
                knockbackDistance = 0; // Reset knockback distance after knockback ends
            }
        }
    }
    /**
     * Reduces the player's health by a specified amount and triggers the hurt state.
     * @param damage the amount of damage to take.
     */
    public void takeDamage(int damage) {
        // Reduce health by the amount of damage
        health -= damage;
        if (health <= 0) {
            //System.out.println("I'm dead");
        }
        setHurt(true); // Set the hurt state when taking damage
    }

    /**
     * Sets the hurt state of the player and resets the hurt counter.
     * @param hurt true to set the player as hurt; false otherwise.
     */
    public void setHurt(boolean hurt) {
        isHurt = hurt;
        hurtCounter = 0; // Reset the hurt counter when hurt state is set
    }

    /**
     * Retrieves the current action of the player (e.g., "idle", "punch", "kick").
     * @return a String representing the current action.
     */
    public String getAction() {
        return action; // Assuming 'action' is a member variable in Player class
    }

    /**
     * Retrieves the current sprite number of the player.
     * @return an integer representing the current sprite number.
     */
    public int getSpriteNum() {
        return spriteNum; // Assuming 'spriteNum' is a member variable in Player class
    }

    /**
     * Retrieves the current direction of the player (e.g., "left", "right", "idle").
     * @return a String representing the current direction.
     */
    public String getDirection() {
        return direction; // Assuming 'direction' is a member variable in Player class
    }
    /**
     * Checks if the player is currently in a hurt state.
     * @return true if the player is hurt; false otherwise.
     */
    public boolean isHurt() {
        return isHurt;
    }


}