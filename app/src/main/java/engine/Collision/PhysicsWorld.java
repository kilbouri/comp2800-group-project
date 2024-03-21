package engine.collision;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import engine.GameObject;

/**
 * The PhysicsWorld class represents a physics simulation environment where
 * collision events are detected and handled.
 * It keeps track of the current and last collisions between game objects and
 * updates their states accordingly.
 */
public class PhysicsWorld {
    private Set<CollisionEvent> currentCollisions = new HashSet<>();
    private Set<CollisionEvent> lastCollisions = new HashSet<>();

    /**
     * Updates the physics simulation by detecting and handling collisions between
     * game objects.
     *
     * @param gameObjects An array of game objects to check for collisions.
     */
    public void update(GameObject[] gameObjects) {
        currentCollisions.clear();

        for (int i = 0; i < gameObjects.length - 1; i++) {
            for (int j = i + 1; j < gameObjects.length; j++) {
                GameObject gameObject = gameObjects[i];
                GameObject otherObject = gameObjects[j];
                BoxCollider gameObjectCollider = gameObject.getComponent(BoxCollider.class);
                BoxCollider otherObjectCollider = otherObject.getComponent(BoxCollider.class);

                if (gameObjectCollider != null && otherObjectCollider != null) {
                    if (gameObject != otherObject && gameObjectCollider.collidesWith(otherObjectCollider)) {
                        CollisionEvent event = new CollisionEvent(gameObject, otherObject);
                        currentCollisions.add(event);

                        if (!lastCollisions.contains(event)) {
                            // New collision
                            gameObject.onCollisionEnter(event);
                            otherObject.onCollisionEnter(event);
                        } else {
                            // Collision stay
                            // gameObject.onCollisionStay(event);
                            // otherObject.onCollisionStay(event);
                        }

                        // Respond to collision if the object is moveable
                        if (gameObjectCollider.moveable) {
                            gameObjectCollider.respondToCollision(event);
                        }
                        if (otherObjectCollider.moveable) {
                            otherObjectCollider.respondToCollision(event);
                        }
                    }
                }
            }
        }

        // Check for collisions that have ended
        for (CollisionEvent lastEvent : lastCollisions) {
            if (!currentCollisions.contains(lastEvent)) {
                // Collision ended
                lastEvent.getCollider().onCollisionExit(lastEvent);
                lastEvent.getOther().onCollisionExit(lastEvent);
            }
        }
        // Prepare for the next update
        lastCollisions.clear();
        lastCollisions.addAll(currentCollisions);
    }

    /**
     * Calculates the hash code for the PhysicsWorld object based on its
     * currentCollisions and lastCollisions sets.
     *
     * @return The hash code value for the PhysicsWorld object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(currentCollisions, lastCollisions);

    }
}
