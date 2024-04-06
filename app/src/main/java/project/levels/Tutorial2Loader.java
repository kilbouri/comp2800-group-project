package project.levels;

import static project.levels.Level.MAX_GRID_X;
import static project.levels.Level.MAX_GRID_Y;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import project.gameobjects.Player;
import project.gameobjects.blocks.Ground;
import project.gameobjects.blocks.MovingPlatform;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Tutorial2Loader implements LevelLoader {
    @Override
    public void load(GameLoop loop) throws Exception {
        int x = -1;
        int groundLevel = MAX_GRID_Y - 2;

        loop.addGameObject(new Ground(x, groundLevel, 5, 2));

        loop.addGameObject(new MovingPlatform(
                x += 6, groundLevel,
                x += 7, groundLevel,
                4, 0.0, 5.0));

        loop.addGameObject(new MovingPlatform(
                x += 4, groundLevel,
                x += 0, 6,
                4, 4.0, 5.0));

        loop.addGameObject(new Ground(MAX_GRID_X - 6, 4, 6, MAX_GRID_Y - 4));
        loop.addGameObject(new Player(PantColor.Blue, 2, groundLevel - 2));
    }
}
