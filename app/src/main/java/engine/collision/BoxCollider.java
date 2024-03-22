package engine.collision;

import java.awt.geom.Rectangle2D;
import engine.Component;

/**
 * The BoxCollider class represents a collider component that can be attached to
 * a game object.
 */
public class BoxCollider extends Component {

    /**
     * @return the Rectangle2D representing the bounds of the box collider
     */
    public Rectangle2D getBox() {
        return parentObject.getTransform();
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
     * Pushes this collider out of the provided box collider
     *
     * @param other the object to push
     */
    public void resolveCollisionWith(BoxCollider other) {
        Rectangle2D.Double selfTrans = this.parentObject.getTransform();
        Rectangle2D.Double otherTrans = other.parentObject.getTransform();

        Rectangle2D intersection = selfTrans.createIntersection(otherTrans);

        if (intersection.isEmpty()) {
            return; // No intersection, no collision to resolve
        }

        // Create a temporary collider centered at the intersection's center
        // with a size small enough to fit inside the intersection
        double tempSize = Math.min(intersection.getWidth(), intersection.getHeight());
        Rectangle2D.Double tempCollider = new Rectangle2D.Double(
                intersection.getCenterX() - tempSize / 2,
                intersection.getCenterY() - tempSize / 2,
                tempSize, tempSize);

        // Now determine the overlap between the temp collider and the original
        // colliderTransform
        double overlapX = 0;
        double overlapY = 0;

        // Horizontal overlap
        if (tempCollider.getCenterX() < selfTrans.getCenterX()) {
            // Collision from the left
            overlapX = selfTrans.getX() - tempCollider.getMaxX();
        } else {
            // Collision from the right
            overlapX = selfTrans.getMaxX() - tempCollider.getX();
        }

        // Vertical overlap
        if (tempCollider.getCenterY() < selfTrans.getCenterY()) {
            // Collision from the top
            overlapY = selfTrans.getY() - tempCollider.getMaxY();
        } else {
            // Collision from the bottom
            overlapY = selfTrans.getMaxY() - tempCollider.getY();
        }

        // Resolve the collision based on the direction with the smallest overlap
        if (Math.abs(overlapX) < Math.abs(overlapY)) {
            // Resolve horizontally
            if (overlapX > 0) {
                // Collision from the left
                selfTrans.x = otherTrans.x - selfTrans.width;
            } else {
                // Collision from the right
                selfTrans.x = otherTrans.getMaxX();
            }
        } else {
            if (overlapY > 0) {
                // Collision from the top
                selfTrans.y = otherTrans.y - selfTrans.height;
            } else {
                // Collision from the bottom
                selfTrans.y = otherTrans.getMaxY();
            }
        }
    }
}
