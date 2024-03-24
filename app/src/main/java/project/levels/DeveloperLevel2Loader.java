package project.levels;

import java.awt.image.BufferedImage;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import project.PlaceholderSpriteSheet;
import project.gameobjects.Block;
import project.gameobjects.LevelSwitchTrigger;
import project.gameobjects.MovingPlatform;
import project.gameobjects.Player;

public class DeveloperLevel2Loader implements LevelLoader {

    @Override
    public void load(GameLoop loop) {
        PlaceholderSpriteSheet placeholders = PlaceholderSpriteSheet.getInstance();

        BufferedImage groundSprite = placeholders.getTile(0);
        BufferedImage playerSprite = placeholders.getTile(3);
        BufferedImage staticBlockSprite = placeholders.getTile(2);

        final int groundLevel = 500;

        Player player = new Player(
                playerSprite,
                64, groundLevel - placeholders.getTileHeight());

        loop.addGameObject(player);

        // Ground
        loop.addGameObject(new Block(groundSprite, 0, groundLevel, 32 * 4, 32));
        loop.addGameObject(new Block(groundSprite, 32 * 20, groundLevel - 128, 128, 32));

        loop.addGameObject(new MovingPlatform(
                staticBlockSprite,
                32 * 12, 32 * 8, // track end pos
                32 * 6, 32 * 12, // track start pos
                2.2, 4.0, // start time and period
                128, 32)); // width and height

        LevelSwitchTrigger trigger = new LevelSwitchTrigger(
                player,
                Level.Developer1,
                800, groundLevel - 400,
                50, 400);

        trigger.setDebug(true);
        loop.addGameObject(trigger);
    }
}
