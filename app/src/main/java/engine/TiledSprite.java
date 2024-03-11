package engine;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class TiledSprite extends Sprite {

    protected BufferedImage sourceImage;

    public TiledSprite(Point2D.Double position, BufferedImage sprite, int tilesX, int tilesY) {
        super(position, sprite);

        // Save the original display image, then compute a tiled version
        this.sourceImage = super.displayImage;
        super.displayImage = SpriteUtils.tile(this.sourceImage, tilesX, tilesY);
    }

    public TiledSprite(BufferedImage sprite) {
        this(new Point2D.Double(0, 0), sprite, 1, 1);
    }
}
