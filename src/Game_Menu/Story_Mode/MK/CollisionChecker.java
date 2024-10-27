package Game_Menu.Story_Mode.MK;

import Game_Menu.Story_Mode.MK.Entity.Entity;

import java.awt.*;

/**
 * The CollisionChecker class is responsible for detecting collisions between entities
 * within the Fight panel.
 */
public class CollisionChecker {
    private Fight gp; // Reference to the Fight panel where collisions are checked

    /**
     * Constructs a CollisionChecker with a reference to the Fight panel.
     *
     * @param gp The Fight panel where collisions will be checked.
     */
    public CollisionChecker(Fight gp) {
        this.gp = gp;
    }

    /**
     * Checks if two entities collide based on their solid areas.
     *
     * @param entity1 The first entity to check for collision.
     * @param entity2 The second entity to check for collision.
     * @return true if entity1's solid area intersects with entity2's solid area, otherwise false.
     */
    public boolean checkCollision(Entity entity1, Entity entity2) {
        // Get the collision areas of the entities
        Rectangle entity1CollisionArea = new Rectangle(entity1.x, entity1.y, entity1.solidArea.width, entity1.solidArea.height);
        Rectangle entity2CollisionArea = new Rectangle(entity2.x, entity2.y, entity2.solidArea.width, entity2.solidArea.height);

        // Check if the collision areas intersect
        return entity1CollisionArea.intersects(entity2CollisionArea);
    }
}
