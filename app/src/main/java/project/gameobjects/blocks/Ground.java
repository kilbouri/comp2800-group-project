package project.gameobjects.blocks;

import engine.sprites.SpriteSheet;
import project.PlaceholderSpriteSheet;

public class Ground extends Block {
    private static final SpriteSheet sourceSheet = PlaceholderSpriteSheet.getInstance();

    public Ground(int gridX, int gridY, int gridWidth, int gridHeight) {
        super(sourceSheet.getTile(0), gridX, gridY, gridWidth, gridHeight);
    }
}
