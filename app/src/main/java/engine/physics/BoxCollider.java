package engine.physics;

import java.awt.Color;
import java.awt.Graphics2D;
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

    private Rectangle2D box;

    /**
     * Create a new box collider based on the transform of the parent object.
     * The X, Y, width, and height of the collider match those of the parent
     * object transform.
     */
    public BoxCollider() {
        this.box = null;
    }

    /**
     * Create a new box collider based on the provided rectangle. The rectangle
     * will be positioned relative to the parent object, and have the width
     * and height of the provided box.
     *
     * @param x      the x-offset from the parent at which the box is positioned
     * @param y      the y-offset from the parent at which the box is positioned
     * @param width  the width of the box
     * @param height the height of the box
     */
    public BoxCollider(double x, double y, double width, double height) {
        this(new Rectangle2D.Double(x, y, width, height));
    }

    /**
     * Create a new box collider based on the provided rectangle. The rectangle
     * will be positioned relative to the parent object, and have the width
     * and height of the provided box.
     *
     * @param box the parent-relative box to use
     */
    public BoxCollider(Rectangle2D box) {
        setBox(box);
    }

    /**
     * @return the Rectangle2D representing the bounds of the box collider
     */
    public Rectangle2D getBox() {
        if (this.box == null) {
            return parentObject.getTransform();
        }

        return new Rectangle2D.Double(
                parentObject.getTransform().x + box.getX(),
                parentObject.getTransform().y + box.getY(),
                box.getWidth(),
                box.getHeight());
    }

    public void setBox(Rectangle2D newBox) {
        this.box = newBox.getBounds();
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
        final Rectangle2D.Double selfTrans = this.parentObject.getTransform();
        final Rectangle2D selfBox = this.getBox();
        final Rectangle2D otherBox = other.getBox();
        final Rectangle2D intersection = selfBox.createIntersection(otherBox);

        if (intersection.isEmpty()) {
            return; // No intersection, no collision to resolve
        }

        // Create a temporary collider centered at the intersection's center
        // with a size small enough to fit inside the intersection
        final double tempSize = Math.min(intersection.getWidth(), intersection.getHeight());
        final Rectangle2D.Double tempCollider = new Rectangle2D.Double(
                intersection.getCenterX() - tempSize / 2,
                intersection.getCenterY() - tempSize / 2,
                tempSize, tempSize);

        // Now determine the overlap between the temp collider and the original
        // colliderTransform
        double overlapX = 0;
        double overlapY = 0;

        // Horizontal overlap
        if (tempCollider.getCenterX() < selfBox.getCenterX()) {
            // Collision from the left
            overlapX = selfBox.getX() - tempCollider.getMaxX();
        } else {
            // Collision from the right
            overlapX = selfBox.getMaxX() - tempCollider.getX();
        }

        // Vertical overlap
        if (tempCollider.getCenterY() < selfBox.getCenterY()) {
            // Collision from the top
            overlapY = selfBox.getY() - tempCollider.getMaxY();
        } else {
            // Collision from the bottom
            overlapY = selfBox.getMaxY() - tempCollider.getY();
        }

        // Resolve the collision based on the direction with the smallest overlap
        if (Math.abs(overlapX) < Math.abs(overlapY)) {
            // Resolve horizontally
            if (overlapX >= 0) {
                // Collision from the left
                selfTrans.x -= overlapX;
            } else {
                // Collision from the right
                final double selfBoxXOffset = selfBox.getX() - selfTrans.x;
                selfTrans.x = otherBox.getMaxX() - selfBoxXOffset;
            }
        } else {
            if (overlapY >= 0) {
                // Collision from the top
                selfTrans.y -= overlapY;
            } else {
                // Collision from the bottom
                final double selfBoxYOffset = selfBox.getY() - selfTrans.y;
                selfTrans.y = otherBox.getMaxY() - selfBoxYOffset;
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

    public void drawDebug(Graphics2D g) {
        g.setColor(Color.green);
        g.draw(getBox());
    }

    private static boolean between(double val, double min, double max) {
        return min <= val && val <= max;
    }
}
