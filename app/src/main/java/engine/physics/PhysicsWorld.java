package engine.physics;

import java.util.HashSet;
import java.util.Set;

import engine.core.GameObject;

/**
 * The PhysicsWorld class represents a physics simulation environment where
 * collision events are detected and handled.
 * It keeps track of the current and last collisions between game objects and
 * updates their states accordingly.
 */
public class PhysicsWorld {
    /**
     * The number of pixels that "represent" a meter. Chosen by fair dice roll.
     */
    public static final int PIXELS_PER_METER = 75;

    /**
     * The global gravity constant.
     */
    public static final double GRAVITY = 9.81 * PIXELS_PER_METER;

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
            GameObject first = gameObjects[i];
            BoxCollider firstCollider = first.getComponent(BoxCollider.class);

            if (firstCollider == null) {
                continue;
            }

            for (int j = i + 1; j < gameObjects.length; j++) {
                GameObject second = gameObjects[j];
                if (first == second) {
                    return;
                }

                BoxCollider secondCollider = second.getComponent(BoxCollider.class);
                if (secondCollider == null) {
                    continue;
                }

                if (firstCollider.getBox().intersects(secondCollider.getBox())) {
                    // We cache the colliders in the event so that consumers don't need to enumerate
                    // the components on the two game objects just to find the collider.
                    CollisionEvent event = new CollisionEvent(first, firstCollider, second, secondCollider);

                    currentCollisions.add(event); // CollisionEvent implements equals
                    lastCollisions.remove(event); // and hashCode to enable this

                    if (!lastCollisions.contains(event)) {
                        // New collision
                        first.onCollisionEnter(event);
                        second.onCollisionEnter(event);
                    } else {
                        // Collision stay
                        // gameObject.onCollisionStay(event);
                        // otherObject.onCollisionStay(event);
                    }
                }
            }
        }

        // All continued collisions are removed already
        for (CollisionEvent endedCollision : lastCollisions) {
            endedCollision.getFirst().onCollisionExit(endedCollision);
            endedCollision.getSecond().onCollisionExit(endedCollision);
        }

        lastCollisions.clear();
        lastCollisions.addAll(currentCollisions);
    }
}
