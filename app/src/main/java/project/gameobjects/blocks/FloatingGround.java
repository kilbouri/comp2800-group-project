package project.gameobjects.blocks;

import engine.sprites.SpriteSheet;
import project.PlaceholderSpriteSheet;

public class FloatingGround extends Block {

    private static final SpriteSheet sourceSheet = PlaceholderSpriteSheet.getInstance();

    public FloatingGround(int gridX, int gridY, int gridWidth) {
        super(sourceSheet.getTile(3), gridX, gridY, gridWidth, 1);
    }
}
