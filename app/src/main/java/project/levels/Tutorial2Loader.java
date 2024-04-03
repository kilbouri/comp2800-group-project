package project.levels;

import java.awt.image.BufferedImage;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.SpriteSheet;
import project.PlaceholderSpriteSheet;
import project.gameobjects.Block;
import project.gameobjects.LevelSwitchTrigger;
import project.gameobjects.MovingPlatform;
import project.gameobjects.Player;

public class Tutorial2Loader implements LevelLoader {
    @Override
    public void load(GameLoop loop) {
        SpriteSheet placeholders = PlaceholderSpriteSheet.getInstance();

        final int groundLevel = 500;

        BufferedImage ground = placeholders.getTile(0);

        loop.addGameObject(new Block(ground, 0, groundLevel, 100, 100));
        loop.addGameObject(new Block(ground, 800, 100, 100, 600));

        loop.addGameObject(new MovingPlatform(
                placeholders.getTile(2),
                150, groundLevel,
                500, groundLevel,
                0, 5,
                100, 32));

        loop.addGameObject(new MovingPlatform(
                placeholders.getTile(2),
                600, groundLevel,
                600, 100,
                4, 5,
                100, 32));

        Player player = new Player(placeholders.getTile(1), 32, groundLevel - placeholders.getTileHeight());
        loop.addGameObject(player);

        loop.addGameObject(new LevelSwitchTrigger(player, Level.Developer1, 850, 50, 50, 50)).setDebug(true);
    }
}
