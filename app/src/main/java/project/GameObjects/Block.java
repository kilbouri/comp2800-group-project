package project.gameObjects;

import engine.GameObject;
import engine.SpriteUtils;
import engine.collision.BoxCollider;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The Block class represents a game object that represents a block in the game.
 * It extends the GameObject class and provides methods for updating and
 * rendering
 * the block.
 */
public class Block extends GameObject {

    BoxCollider boxCollider;
    BufferedImage clippedImage;

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
        clippedImage = SpriteUtils.tileToSize(image, width, height);
        boxCollider = new BoxCollider();
        boxCollider.setCollisionLayer(engine.collision.CollisionLayer.DEFAULT);
        this.transform = new Rectangle2D.Double(x, y, width, height);

        this.addComponent(boxCollider);
    }

    @Override
    public void update(double deltaTime) {
        updateComponents(deltaTime);
    }

    @Override
    public void render(java.awt.Graphics2D graphics) {
        graphics.drawImage(clippedImage, (int) transform.x, (int) transform.y, null);
    }

}
