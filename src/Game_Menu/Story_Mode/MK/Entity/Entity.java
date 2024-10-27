package Game_Menu.Story_Mode.MK.Entity;

import java.awt.*;

/**
 * The Entity class represents a basic game entity with properties such as
 * position, speed, direction, and health.
 */
public class Entity {
    public int x, y; // Entity's position coordinates
    public int speed; // Entity's movement speed
    public String direction; // Direction the entity is facing
    public Rectangle solidArea; // Solid area for collision detection
    public int spriteCounter = 0; // Counter for sprite animation
    public int spriteNum = 1; // Current sprite number for animation
    public int solidAreaDefaultX, solidAreaDefaultY; // Default position of the solid area
    public int health; // Current health of the entity
    public int maxHealth; // Maximum health of the entity

    /**
     * Constructor for the Entity class.
     * Initializes the solid area with default size and position.
     */
    public Entity(){
        solidArea = new Rectangle(0, 0, 50, 50); // Default solid area size
        solidAreaDefaultX = solidArea.x; // Default x position of the solid area
        solidAreaDefaultY = solidArea.y; // Default y position of the solid area
    }

    /**
     * Sets the dimensions of the solid area.
     *
     * @param width  The width of the solid area.
     * @param height The height of the solid area.
     */
    public void setSolidAreaDimensions(int width, int height) {
        solidArea.width = width;
        solidArea.height = height;
    }

    /**
     * Updates the solid area's position and size based on the entity's position
     * and given offsets and dimensions.
     *
     * @param offsetX The x-offset for the solid area's position.
     * @param offsetY The y-offset for the solid area's position.
     * @param width   The new width of the solid area.
     * @param height  The new height of the solid area.
     */
    public void updateSolidArea(int offsetX, int offsetY, int width, int height) {
        solidArea.x = x + offsetX; // Update x position of the solid area
        solidArea.y = y + offsetY; // Update y position of the solid area
        solidArea.width = width; // Update width of the solid area
        solidArea.height = height; // Update height of the solid area
    }

    /**
     * Gets the current health of the entity.
     *
     * @return The current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the maximum health of the entity.
     *
     * @return The maximum health.
     */
    public int getMaxHealth() {
        return maxHealth;
    }
}
