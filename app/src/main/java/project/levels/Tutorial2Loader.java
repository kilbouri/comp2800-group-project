package project.levels;

import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.SpriteSheet;
import project.PlaceholderSpriteSheet;
import project.gameobjects.LevelSwitchTrigger;
import project.gameobjects.Player;
import project.gameobjects.blocks.Block;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Tutorial2Loader implements LevelLoader {
    @Override
    public void load(GameLoop loop) {
        // SpriteSheet placeholders = PlaceholderSpriteSheet.getInstance();

        // final int groundLevel = 500;

        // BufferedImage ground = placeholders.getTile(0);

        // loop.addGameObject(new Ground(0, groundLevel, 200, 100));
        // loop.addGameObject(new Ground(800, 100, 100, 600));

        // loop.addGameObject(new MovingPlatform(
        // placeholders.getTile(2),
        // 250, groundLevel,
        // 500, groundLevel,
        // 0, 5,
        // 100, 32));

        // loop.addGameObject(new MovingPlatform(
        // placeholders.getTile(2),
        // 600, groundLevel,
        // 600, 100,
        // 4, 5,
        // 100, 32));

        // Player player;
        // try {
        // player = new Player(PantColor.Blue, 50, groundLevel);
        // player.getTransform().y -= player.getTransform().height;
        // } catch (IOException e) {
        // e.printStackTrace();
        // return;
        // }

        // loop.addGameObject(player);
        // loop.addGameObject(new LevelSwitchTrigger(player, Level.Developer1, 850, 50,
        // 50, 50)).setDebug(true);
    }
}
