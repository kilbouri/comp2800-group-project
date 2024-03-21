package engine.collision;

import engine.GameObject;
import java.util.Objects;

/**
 * Represents a collision event between two game objects.
 */
public class CollisionEvent {
    private GameObject collider;
    private CollisionLayer colliderCollisionLayer;
    private GameObject other;
    private CollisionLayer otherCollisionLayer;
    private CollisionType collisionType;

    /**
     * Constructs a CollisionEvent object with the specified collider and other game objects.
     *
     * @param collider The game object that caused the collision.
     * @param other    The game object that collided with the collider.
     */
    public CollisionEvent(GameObject collider, GameObject other) {
        this.collider = collider;
        this.other = other;
        this.collisionType = CollisionType.NONE;

        if (collider != null) {
            this.colliderCollisionLayer = collider.getComponent(BoxCollider.class).getCollisionLayer() != null
                    ? collider.getComponent(BoxCollider.class).getCollisionLayer()
                    : CollisionLayer.DEFAULT;
        }
        if (other != null) {
            this.otherCollisionLayer = other.getComponent(BoxCollider.class).getCollisionLayer() != null
                    ? other.getComponent(BoxCollider.class).getCollisionLayer()
                    : CollisionLayer.DEFAULT;
        }
    }

    /**
     * Returns the game object that caused the collision.
     *
     * @return The collider game object.
     */
    public GameObject getCollider() {
        return collider;
    }

    /**
     * Returns the game object that collided with the collider.
     *
     * @return The other game object.
     * @see GameObject
     */
    public GameObject getOther() {
        return other;
    }

    /**
     * Returns the type of collision that occurred.
     *
     * @return The collision type.
     * @see CollisionType
     */
    public CollisionType getCollisionType() {
        return collisionType;
    }

    /**
     * Sets the type of collision that occurred.
     *
     * @param collisionType The collision type to set.
     * @see CollisionType
     */
    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

    /**
     * Returns the collision layer of the collider game object.
     *
     * @return The collider's collision layer.
     * @see CollisionLayer
     */
    public CollisionLayer getColliderCollisionLayer() {
        return colliderCollisionLayer;
    }

    /**
     * Returns the collision layer of the other game object.
     *
     * @return The other game object's collision layer.
     * @see CollisionLayer
     */
    public CollisionLayer getOtherCollisionLayer() {
        return otherCollisionLayer;
    }

    /**
     * Checks if this CollisionEvent is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CollisionEvent that = (CollisionEvent) o;
        return Objects.equals(collider, that.collider) &&
                Objects.equals(other, that.other)
                &&
                collisionType == that.collisionType;
    }
}
