package project.levels;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import project.PlaceholderSpriteSheet;
import project.gameobjects.Block;
import project.gameobjects.LevelSwitchTrigger;
import project.gameobjects.Player;

public class DeveloperLevelLoader implements LevelLoader {

    @Override
    public void load(GameLoop loop) {
        PlaceholderSpriteSheet placeholders = PlaceholderSpriteSheet.getInstance();

        Player player = new Player(
                placeholders.getTile(3),
                100, 500 - placeholders.getTileHeight());
        loop.addGameObject(player);

        loop.addGameObject(new Block(
                placeholders.getTile(0),
                0, 600 - 100,
                900, 60));

        loop.addGameObject(new Block(
                placeholders.getTile(1),
                300, 600 - 100 - 100,
                60, 100));

        loop.addGameObject(new Block(
                placeholders.getTile(1),
                20, 600 - 100 - 100,
                60, 100));

        loop.addGameObject(new Block(
                placeholders.getTile(2),
                180, 600 - 100 - 200 - 32,
                60, 200));

        loop.addGameObject(new Block(
                placeholders.getTile(2),
                300, 600 - 100 - 200 - 32,
                300, 30));

        loop.addGameObject(new Block(
                placeholders.getTile(2),
                600, 600 - 100 - 200 - 32,
                60, 200));

        LevelSwitchTrigger trigger = new LevelSwitchTrigger(
                player,
                300, 300,
                150, 150);
        trigger.setDebug(true);
        loop.addGameObject(trigger);
    }

}
