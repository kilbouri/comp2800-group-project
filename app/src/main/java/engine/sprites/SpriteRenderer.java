package engine.sprites;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import engine.core.Component;

public class SpriteRenderer extends Component {

    protected boolean flipX;
    protected boolean flipY;

    protected double scale;

    protected Point2D.Double offset;
    protected BufferedImage displayImage;

    /**
     * Constructs a Sprite object with the specified parent game object, position,
     * and sprite image.
     *
     * @param parentObject The parent game object to which this sprite belongs.
     * @param offset       The initial position of the sprite.
     * @param sprite       The image to be displayed as the sprite.
     */
    public SpriteRenderer(Point2D.Double offset, BufferedImage sprite) {
        this.offset = offset;
        this.displayImage = sprite;
        this.flipX = false;
        this.flipY = false;
        this.scale = 1.0;
    }

    /**
     * Constructs a Sprite object with the specified parent game object and sprite
     * image.
     * The initial position of the sprite is set to (0, 0).
     *
     * @param ParentObject The parent game object to which this sprite belongs.
     * @param sprite       The image to be displayed as the sprite.
     */
    public SpriteRenderer(BufferedImage sprite) {
        this(new Point2D.Double(0, 0), sprite);
    }

    /**
     * Renders the sprite on the specified graphics context.
     *
     * @param graphics The graphics context on which to render the sprite.
     */
    public void render(Graphics2D graphics) {
        double posX = offset.x + getParentObject().getTransform().x;
        double posY = offset.y + getParentObject().getTransform().y;

        double width = displayImage.getWidth() * scale;
        double height = displayImage.getHeight() * scale;

        if (flipX) {
            posX += width;
            width = -width;
        }

        if (flipY) {
            posY += height;
            height = -height;
        }

        graphics.drawImage(
                displayImage,
                (int) posX, (int) posY,
                (int) width, (int) height,
                null);
    }

    /**
     * Sets the position of the sprite to the specified coordinates.
     *
     * @param x The x-coordinate of the new position.
     * @param y The y-coordinate of the new position.
     */
    public void setOffset(double x, double y) {
        this.offset.x = x;
        this.offset.y = y;
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
     * Sets the display image of the sprite to the specified image.
     *
     * @param displayImage The new display image of the sprite.
     */
    public void setDisplayImage(BufferedImage displayImage) {
        this.displayImage = displayImage;
    }

    public void setIsFlippedX(boolean flip) {
        this.flipX = flip;
    }

    public void setIsFlippedY(boolean flip) {
        this.flipY = flip;
    }

    /**
     * @param scale the new scale for the image
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public void update(double deltaTime) {
    }
}
