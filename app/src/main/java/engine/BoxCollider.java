package engine;

import java.util.List;

/**
 * The BoxCollider class represents a component that defines a rectangular
 * bounding box for collision detection.
 * It extends the Component class and provides methods for checking collisions
 * with other BoxColliders.
 * Note: for this to work, the GameObject must have a Transform component.
 *
 *
 */
public class BoxCollider extends Component {

    /**
     * Checks if this BoxCollider collides with another BoxCollider.
     *
     * @param other the other BoxCollider to check collision with
     * @return true if the colliders intersect, false otherwise
     */
    public boolean collidesWith(BoxCollider other) {

        return parentObject.getTransform().intersects(other.parentObject.getTransform());
    }

    /**
     * Updates the BoxCollider component.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(double deltaTime) {
    }

    /**
     * Checks collisions with a list of GameObjects and responds to collisions if
     * they occur.
     *
     * @param gameObjects the list of GameObjects to check collisions with
     */
    public void checkCollisions(List<GameObject> gameObjects) {
        for (GameObject otherObject : gameObjects) {
            if (otherObject != parentObject) { // Avoid self-collision
                BoxCollider otherCollision = otherObject.getComponent(
                        BoxCollider.class);
                if (otherCollision != null && this.collidesWith(otherCollision)) {
                    respondToCollision(otherCollision);
                }
            }
        }
    }

    /**
     * Responds to a collision with another BoxCollider.
     *
     * @param other the BoxCollider that this BoxCollider collided with
     */
    private void respondToCollision(BoxCollider other) {
        parentObject.onCollision(other.parentObject);
    }
}
