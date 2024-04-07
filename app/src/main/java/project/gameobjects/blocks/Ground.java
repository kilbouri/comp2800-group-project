package project.gameobjects.blocks;

import static project.levels.Level.GRID_SIZE;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.physics.BoxCollider;
import engine.sprites.SpriteUtils;

public class Ground extends Block {
    private static final BufferedImage sourceSprite = getSourceSprite();

    public Ground(double gridX, double gridY, int gridWidth, int gridHeight) {
        this(gridX, gridY, gridWidth, gridHeight, false);
    }

    public Ground(double gridX, double gridY, int gridWidth, int gridHeight, boolean grassCollisionOnly) {
        super(
                getScaledSprite(gridWidth, gridHeight),
                gridX, gridY,
                gridWidth, gridHeight);

        // If only grass collision is requested, we need to resize the box.
        // Otherwise, the default box is sufficient.
        if (grassCollisionOnly) {
            getComponent(BoxCollider.class).setBox(new Rectangle2D.Double(
                    0, 0,
                    gridWidth * GRID_SIZE,
                    0.25 * GRID_SIZE));
        }
    }

    private static BufferedImage getSourceSprite() {
        try {
            return SpriteUtils.load("sprites/prod/world/ground.png");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage getScaledSprite(int gridWidth, int gridHeight) {
        return SpriteUtils.scaleSliced(
                sourceSprite,
                new Rectangle(32, 32, 32, 32),
                gridWidth * GRID_SIZE, gridHeight * GRID_SIZE);
    }
}
