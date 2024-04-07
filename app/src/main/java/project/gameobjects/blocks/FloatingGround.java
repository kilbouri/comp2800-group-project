package project.gameobjects.blocks;

import static project.levels.Level.GRID_SIZE;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.physics.BoxCollider;
import engine.sprites.SpriteUtils;

public class FloatingGround extends Block {

    private static final BufferedImage sourceImage = getSourceSprite();

    public FloatingGround(double gridX, double gridY, int gridWidth) {
        super(getScaledSprite(gridWidth, 1), gridX, gridY, gridWidth, 1);

        // Override the box such that only the grassy part actually has
        // collision
        getComponent(BoxCollider.class).setBox(new Rectangle2D.Double(
                0, 0,
                gridWidth * GRID_SIZE,
                0.25 * GRID_SIZE));
    }

    private static BufferedImage getSourceSprite() {
        try {
            return SpriteUtils.load("sprites/prod/world/floating.png");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage getScaledSprite(int gridWidth, int gridHeight) {
        return SpriteUtils.scaleSliced(
                sourceImage,
                new Rectangle(32, 1, 32, 30),
                gridWidth * GRID_SIZE, gridHeight * GRID_SIZE);
    }
}
