package engine.collision;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import engine.Component;

/**
 * The BoxCollider class represents a collider component that can be attached to
 * a game object.
 * It defines a rectangular area that can be used to detect collisions with
 * other colliders.
 */
public class BoxCollider extends Component {

    boolean moveable = false;
    private CollisionLayer collisionLayer;

    /**
     * Using the built in Rectangle2D.Double class to represent the collider's area,
     * this function checks whether the
     * game object that this Box Collider is attached to is colliding with another
     * BoxCollider.
     *
     * @param other the other BoxCollider to check for collision with
     * @return true if the game object is colliding with the other BoxCollider,
     *         false otherwise
     * @see RectangularShape#intersects(Rectangle2D)
     *
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
     * Responds to a collision event by resolving the collision between the parent
     * object and the other object.
     *
     * @param event the collision event to respond to
     */
    public void respondToCollision(CollisionEvent event) {
        if (!this.moveable)
            return;

        Rectangle2D.Double colliderTransform = this.parentObject.getTransform();
        Rectangle2D.Double otherTransform = event.getOther().getTransform();
        Rectangle2D intersection = colliderTransform.createIntersection(otherTransform);

        if (intersection.isEmpty())
            return; // No intersection, no collision to resolve

        // Create a temporary collider centered at the intersection's center
        // with a size small enough to fit inside the intersection
        double tempSize = Math.min(intersection.getWidth(), intersection.getHeight());
        Rectangle2D.Double tempCollider = new Rectangle2D.Double(
                intersection.getCenterX() - tempSize / 2,
                intersection.getCenterY() - tempSize / 2,
                tempSize,
                tempSize);

        // Now determine the overlap between the temp collider and the original
        // colliderTransform
        double colliderCenterX = colliderTransform.getCenterX();
        double colliderCenterY = colliderTransform.getCenterY();
        double tempCenterX = tempCollider.getCenterX();
        double tempCenterY = tempCollider.getCenterY();

        double overlapX = 0;
        double overlapY = 0;

        // Horizontal overlap
        if (tempCenterX < colliderCenterX) {
            // Collision from the left
            overlapX = colliderTransform.getX() - (tempCollider.getX() + tempCollider.getWidth());
        } else {
            // Collision from the right
            overlapX = (colliderTransform.getX() + colliderTransform.getWidth()) - tempCollider.getX();
        }

        // Vertical overlap
        if (tempCenterY < colliderCenterY) {
            // Collision from the top
            overlapY = colliderTransform.getY() - (tempCollider.getY() + tempCollider.getHeight());
        } else {
            // Collision from the bottom
            overlapY = (colliderTransform.getY() + colliderTransform.getHeight()) - tempCollider.getY();
        }

        // Resolve the collision based on the direction with the smallest overlap
        if (Math.abs(overlapX) < Math.abs(overlapY)) {
            // Resolve horizontally
            if (overlapX > 0) {
                // Collision from the left
                this.parentObject.getTransform().x = event.getOther().getTransform().x
                        - this.parentObject.getTransform().width;
                event.setCollisionType(CollisionType.LEFT);
            } else {
                // Collision from the right
                this.parentObject.getTransform().x = event.getOther().getTransform().x
                        + event.getOther().getTransform().width;
                event.setCollisionType(CollisionType.RIGHT);
            }
        } else {
            if (overlapY > 0) {
                // Collision from the top
                this.parentObject.getTransform().y = event.getOther().getTransform().y
                        - this.parentObject.getTransform().height;
                event.setCollisionType(CollisionType.TOP);
            } else {
                // Collision from the bottom
                this.parentObject.getTransform().y = event.getOther().getTransform().y
                        + event.getOther().getTransform().height;
                event.setCollisionType(CollisionType.BOTTOM);
            }
        }
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public void setCollisionLayer(CollisionLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public CollisionLayer getCollisionLayer() {
        return collisionLayer;
    }
}