package engine;

/**
 * The BoxCollider class represents a rectangular collision box in a 2D space.
 * It provides methods to check for collisions with other BoxColliders and to manipulate its position and size.
 */
public class BoxCollider {

    private double x, y, width, height;

    /**
     * Constructs a new BoxCollider with the specified position and size.
     *
     * @param x      the x-coordinate of the top-left corner of the collider
     * @param y      the y-coordinate of the top-left corner of the collider
     * @param width  the width of the collider
     * @param height the height of the collider
     */
    public BoxCollider(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Checks if this BoxCollider is colliding with another BoxCollider.
     *
     * @param other the other BoxCollider to check for collision with
     * @return true if the colliders are colliding, false otherwise
     */
    public boolean isColliding(BoxCollider other) {
        return (
            x < other.x + other.width &&
            x + width > other.x &&
            y < other.y + other.height &&
            y + height > other.y
        );
    }

    /**
     * Sets the position of this BoxCollider to the specified coordinates.
     * 
     * @param x the new x-coordinate of the top-left corner of the collider
     * @param y the new y-coordinate of the top-left corner of the collider
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
