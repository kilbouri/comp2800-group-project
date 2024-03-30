package project.levels;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.SpriteUtils;
import project.gameobjects.Block;

public class ScaledSpriteLevel implements LevelLoader {

    @Override
    public void load(GameLoop loop) {
        BufferedImage source;
        try {
            source = SpriteUtils.load("sprites/dev/9slice.png");

            BufferedImage scaled = SpriteUtils.scaleSliced(source, new Rectangle(8, 8, 16, 16), 128, 128);
            Block block = new Block(scaled, 50, 50, 128, 128);
            loop.addGameObject(block);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
