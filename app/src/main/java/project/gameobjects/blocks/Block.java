package project.gameobjects.blocks;

import engine.core.GameObject;
import engine.physics.BoxCollider;
import engine.sprites.SpriteRenderer;
import engine.sprites.SpriteUtils;

import static project.levels.Level.GRID_SIZE;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The Block class represents a game object that represents a block in the game.
 * It extends the GameObject class and provides methods for updating and
 * rendering the block.
 */
public class Block extends GameObject {

    private SpriteRenderer sprite;

    /**
     * Constructs a Ground object with the specified position, image, and tile
     * dimensions.
     *
     * @param image      The image used for rendering the block.
     * @param x          The x-coordinate (in grid-space) of the block's position.
     * @param y          The y-coordinate (in grid-space) of the block's position.
     * @param gridWidth  The number of tiles in the x-direction.
     * @param gridHeight The number of tiles in the y-direction.
     */
    protected Block(BufferedImage image, double gridX, double gridY, int gridWidth, int gridHeight) {
        final double x = gridX * GRID_SIZE;
        final double y = gridY * GRID_SIZE;
        final int width = GRID_SIZE * gridWidth;
        final int height = GRID_SIZE * gridHeight;

        this.transform = new Rectangle2D.Double(x, y, width, height);
        this.addComponent(sprite = new SpriteRenderer(SpriteUtils.tileToSize(image, width, height)));
        this.addComponent(new BoxCollider());
    }

    @Override
    public void render(Graphics2D graphics) {
        sprite.render(graphics);
    }
}
