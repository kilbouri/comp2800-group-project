package project.gameobjects.blocks;

import static project.levels.Level.GRID_SIZE;

import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;
import project.PlaceholderSpriteSheet;

public class Ground extends Block {
    private static final SpriteSheet sourceSheet = PlaceholderSpriteSheet.getInstance();

    public Ground(int gridX, int gridY, int gridWidth, int gridHeight) {
        super(
                SpriteUtils.tileToSize(sourceSheet.getTile(0), gridWidth * GRID_SIZE, gridHeight * GRID_SIZE),
                gridX, gridY,
                gridWidth, gridHeight);
    }
}
