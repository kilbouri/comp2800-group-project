package engine.sprites;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * The TiledSprite class represents a component that renders a tiled sprite on a
 * game object.
 * It extends the Component class and provides methods for updating and
 * rendering the sprite.
 */
public class TiledSprite extends SpriteRenderer {

    protected BufferedImage sourceImage;

    /**
     * Constructs a TiledSprite object with the specified position, sprite image,
     * and number of tiles in the x and y directions.
     *
     * @param position the position of the sprite
     * @param sprite   the sprite image
     * @param tilesX   the number of tiles in the x direction
     * @param tilesY   the number of tiles in the y direction
     */
    public TiledSprite(Point2D.Double position, BufferedImage sprite, int tilesX, int tilesY) {
        super(position, SpriteUtils.tile(sprite, tilesX, tilesY));
    }

    /**
     * Constructs a TiledSprite object with the specified sprite image
     * and number of tiles in the x and y directions.
     *
     * @param sprite the sprite image
     * @param tilesX the number of tiles in the x direction
     * @param tilesY the number of tiles in the y direction
     */
    public TiledSprite(BufferedImage sprite, int tilesX, int tilesY) {
        this(new Point2D.Double(), sprite, tilesX, tilesY);
    }
}
