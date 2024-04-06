package project.levels;

import java.io.IOException;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import project.PlaceholderSpriteSheet;
import project.gameobjects.Block;
import project.gameobjects.LevelSwitchTrigger;
import project.gameobjects.Player;
import project.sprites.PlayerSpriteSheet.PantColor;

public class DeveloperLevel1Loader implements LevelLoader {

    @Override
    public void load(GameLoop loop) {
        PlaceholderSpriteSheet placeholders = PlaceholderSpriteSheet.getInstance();

        Player player;
        try {
            player = new Player(PantColor.Blue, 0, 0);
            player.setPosition(100, 500 - player.getTransform().height - 10);
        } catch (IOException e) {
            System.err.print("IOException creating Player: ");
            e.printStackTrace();
            return;
        }

        loop.addGameObject(player);

        // Ground
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
                Level.Developer2,
                800, 600 - 100 - 400,
                50, 400);
        trigger.setDebug(true);
        loop.addGameObject(trigger);
    }

}
