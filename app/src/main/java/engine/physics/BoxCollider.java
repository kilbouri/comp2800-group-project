package engine.physics;

import java.awt.geom.Rectangle2D;

import engine.core.Component;

/**
 * The BoxCollider class represents a collider component that can be attached to
 * a game object.
 */
public class BoxCollider extends Component {

    public static class OverlapFlags {
        public static final int TOP_EDGE = 1 << 0;
        public static final int LEFT_EDGE = 1 << 1;
        public static final int RIGHT_EDGE = 1 << 2;
        public static final int BOTTOM_EDGE = 1 << 3;

        public static boolean checkEdge(int flags, int edge) {
            return (flags & edge) != 0;
        }
    }

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

    /**
     * Computes the bitwise OR of the flags defined in {@link OverlapFlags}
     * representing the set of edges of this collider which overlap with `other`.
     *
     * @param other the collider to check overlap with
     * @return the bitwise OR of the overlapping edges, or 0 if no overlap.
     */
    public int overlapWith(BoxCollider other) {
        int result = 0;

        Rectangle2D box = getBox();
        Rectangle2D otherBox = other.getBox();

        final double boxMinX = box.getMinX();
        final double boxMaxX = box.getMaxX();
        final double boxMinY = box.getMinY();
        final double boxMaxY = box.getMaxY();
        final double otherMinX = otherBox.getMinX();
        final double otherMaxX = otherBox.getMaxX();
        final double otherMinY = otherBox.getMinY();
        final double otherMaxY = otherBox.getMaxY();

        final boolean top = between(boxMinY, otherMinY, otherMaxY);
        final boolean bottom = between(boxMaxY, otherMinY, otherMaxY);
        final boolean left = between(boxMinX, otherMinX, otherMaxX);
        final boolean right = between(boxMaxX, otherMinX, otherMaxX);

        if (top || bottom) {
            if (left) {
                result |= OverlapFlags.LEFT_EDGE;
            }

            if (right) {
                result |= OverlapFlags.RIGHT_EDGE;
            }
        }

        if (left || right) {
            if (top) {
                result |= OverlapFlags.TOP_EDGE;
            }

            if (bottom) {
                result |= OverlapFlags.BOTTOM_EDGE;
            }
        }

        return result;
    }

    private static boolean between(double val, double min, double max) {
        return min <= val && val <= max;
    }
}
