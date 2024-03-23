package engine.collision;

import java.util.Objects;

import engine.GameObject;
import java.awt.geom.Rectangle2D;

/**
 * Represents a collision event between two game objects.
 */
public class CollisionEvent {

    private GameObject first;
    private BoxCollider firstCollider;

    private GameObject second;
    private BoxCollider secondCollider;

    private Rectangle2D overlap = null;

    /**
     * @param first          the first GameObject involved in the collision
     * @param firstCollider  the collider of the first GameObject
     * @param second         the second GameObject involved in the collision
     * @param secondCollider the collider of the second GameObject
     */
    public CollisionEvent(GameObject first, BoxCollider firstCollider, GameObject second, BoxCollider secondCollider) {
        this.first = first;
        this.firstCollider = firstCollider;

        this.second = second;
        this.secondCollider = secondCollider;
    }

    /**
     * @return the first GameObject involved in the collision
     */
    public GameObject getFirst() {
        return first;
    }

    /**
     * @return the collider of the first GameObject
     * @see #getFirst()
     */
    public BoxCollider getFirstCollider() {
        return firstCollider;
    }

    /**
     * @return the second GameObject involved in the collision
     */
    public GameObject getSecond() {
        return second;
    }

    /**
     * @return the collider of the second GameObject
     * @see #getSecond()
     */
    public BoxCollider getSecondCollider() {
        return secondCollider;
    }

    /**
     * Returns the other game object in the collision.
     *
     * @param self the known game object
     * @return {@link #getFirst()} if self is not the same object, otherwise
     *         {@link #getSecond()}
     */
    public GameObject getOther(GameObject self) {
        return (first != self) ? first : second;
    }

    /**
     * Returns the collider of the other game object in the collision.
     *
     * @param self the known game object
     * @return {@link #getFirstCollider()} if self is not the same object, otherwise
     *         {@link #getSecondCollider()}
     */
    public BoxCollider getOtherCollider(GameObject self) {
        return (first != self) ? firstCollider : secondCollider;
    }

    public Rectangle2D getOverlap() {
        if (overlap == null) {
            overlap = firstCollider.getBox().createIntersection(secondCollider.getBox());
        }

        return overlap;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CollisionEvent)) {
            return false;
        }

        CollisionEvent other = (CollisionEvent) obj;

        // Note the use of == rather than .equals. We are concerned with
        // referential equality, to ensure that hashCode is correctly related.
        return first == other.first && second == other.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
