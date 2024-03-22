package engine;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Sprite {

    protected Point2D.Double position;
    protected BufferedImage displayImage;

    /**
     * Constructs a Sprite object with the specified parent game object, position,
     * and sprite image.
     *
     * @param parentObject The parent game object to which this sprite belongs.
     * @param position     The initial position of the sprite.
     * @param sprite       The image to be displayed as the sprite.
     */
    public Sprite(
            Point2D.Double position,
            BufferedImage sprite) {
        this.position = position;
        this.displayImage = sprite;
    }

    /**
     * Constructs a Sprite object with the specified parent game object and sprite
     * image.
     * The initial position of the sprite is set to (0, 0).
     *
     * @param ParentObject The parent game object to which this sprite belongs.
     * @param sprite       The image to be displayed as the sprite.
     */
    public Sprite(BufferedImage sprite) {
        this(new Point2D.Double(0, 0), sprite);
    }

    /**
     * Renders the sprite on the specified graphics context.
     *
     * @param graphics The graphics context on which to render the sprite.
     */
    public void render(Graphics2D graphics) {
        graphics.drawImage(displayImage, (int) position.x, (int) position.y, null);
    }

    /**
     * Returns the display image of the sprite.
     *
     * @return The display image of the sprite.
     */
    public BufferedImage getDisplayImage() {
        return displayImage;
    }

    /**
     * Sets the position of the sprite to the specified coordinates.
     *
     * @param x The x-coordinate of the new position.
     * @param y The y-coordinate of the new position.
     */
    public void setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y;
    }

    /**
     * Sets the display image of the sprite to the specified image.
     *
     * @param displayImage The new display image of the sprite.
     */
    public void setDisplayImage(BufferedImage displayImage) {
        this.displayImage = displayImage;
    }
}
