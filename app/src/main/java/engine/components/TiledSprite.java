package engine.components;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import engine.Component;
import engine.GameObject;
import engine.SpriteUtils;

/**
 * The TiledSprite class represents a component that renders a tiled sprite on a game object.
 * It extends the Component class and provides methods for updating and rendering the sprite.
 */
public class TiledSprite extends Component {

    protected BufferedImage sourceImage;
    Sprite sprite;

    /**
     * Constructs a TiledSprite object with the specified position, sprite image, and number of tiles in the x and y directions.
     * The sprite is created using the given position and sprite image, and then tiled based on the specified number of tiles.
     *
     * @param position the position of the sprite
     * @param sprite the sprite image
     * @param tilesX the number of tiles in the x direction
     * @param tilesY the number of tiles in the y direction
     */
    public TiledSprite(Point2D.Double position, BufferedImage sprite, int tilesX, int tilesY) {
        this.sprite = new Sprite(ParentObject, position, sprite);

        // Save the original display image, then compute a tiled version
        this.sourceImage = this.sprite.getDisplayImage();
        this.sprite.setDisplayImage(SpriteUtils.tile(this.sourceImage, tilesX, tilesY));
    }

    /**
     * Constructs a TiledSprite object with the specified game object and sprite image.
     * The sprite is created using the default position (0, 0) and the given sprite image.
     *
     * @param ParentObject the parent game object
     * @param sprite the sprite image
     */
    public TiledSprite(GameObject ParentObject, BufferedImage sprite) {
        this(new Point2D.Double(0, 0), sprite, 1, 1);
    }

    /**
     * Updates the sprite based on the elapsed time since the last update.
     *
     * @param deltaTime the elapsed time since the last update
     */
    @Override
    public void update(double deltaTime) {
        this.sprite.update(deltaTime);
    }

    /**
     * Renders the sprite using the specified graphics object.
     *
     * @param graphics the graphics object used for rendering
     */
    public void render(java.awt.Graphics2D graphics) {
        this.sprite.render(graphics);
    }
}
