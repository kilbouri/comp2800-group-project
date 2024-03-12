package project;

import engine.BoxCollider;
import engine.TiledSprite;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * The Ground class represents a tiled sprite that acts as a ground in a game.
 * It extends the TiledSprite class and provides a box collider for collision detection.
 */
public class Ground extends TiledSprite {

    BoxCollider boxCollider = new BoxCollider(0, 0, 0, 0);

    /**
     * Constructs a Ground object with the specified position, image, and tile dimensions.
     *
     * @param x       The x-coordinate of the ground's position.
     * @param y       The y-coordinate of the ground's position.
     * @param image   The image used for rendering the ground.
     * @param tilesX  The number of tiles in the x-direction.
     * @param tilesY  The number of tiles in the y-direction.
     */
    public Ground(int x, int y, BufferedImage image, int tilesX, int tilesY) {
        super(
            new Point2D.Double(x, y),
            image,
            tilesX,
            tilesY
        );
        boxCollider = new BoxCollider(x, y, image.getWidth() * tilesX, image.getHeight() * tilesY);
    }

    /**
     * Returns the box collider associated with the ground.
     *
     * @return The box collider.
     */
    public BoxCollider getCollider() {
        return boxCollider;
    }
}
