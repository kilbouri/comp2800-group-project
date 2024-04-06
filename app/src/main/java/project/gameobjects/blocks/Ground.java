package project.gameobjects.blocks;

import static project.levels.Level.GRID_SIZE;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.sprites.SpriteUtils;

public class Ground extends Block {
    private static final BufferedImage sourceSprite = getSourceSprite();

    public Ground(int gridX, int gridY, int gridWidth, int gridHeight) {
        super(
                getScaledSprite(gridWidth, gridHeight),
                gridX, gridY,
                gridWidth, gridHeight);
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
