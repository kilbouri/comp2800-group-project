package project.gameobjects;

import engine.GameObject;
import engine.Sprite;
import engine.SpriteUtils;
import engine.collision.BoxCollider;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The Block class represents a game object that represents a block in the game.
 * It extends the GameObject class and provides methods for updating and
 * rendering the block.
 */
public class Block extends GameObject {

    Sprite sprite;
    BoxCollider boxCollider;

    /**
     * Constructs a Ground object with the specified position, image, and tile
     * dimensions.
     *
     * @param x      The x-coordinate of the ground's position.
     * @param y      The y-coordinate of the ground's position.
     * @param image  The image used for rendering the ground.
     * @param tilesX The number of tiles in the x-direction.
     * @param tilesY The number of tiles in the y-direction.
     */
    public Block(BufferedImage image, int x, int y, int width, int height) {
        this.transform = new Rectangle2D.Double(x, y, width, height);
        this.addComponent(sprite = new Sprite(SpriteUtils.tileToSize(image, width, height)));
        this.addComponent(boxCollider = new BoxCollider());
    }

    @Override
    public void render(Graphics2D graphics) {
        sprite.render(graphics);
    }
}
