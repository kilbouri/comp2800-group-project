package project.levels;

import static project.levels.Level.MAX_GRID_Y;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import project.gameobjects.BackgroundImage;
import project.gameobjects.LifeCounterOverlay;
import project.gameobjects.Player;
import project.gameobjects.triggers.LevelExit;
import project.gameobjects.StaticSprite;
import project.gameobjects.blocks.Ground;
import project.gameobjects.blocks.MovingPlatform;
import project.sprites.DecorationSpriteSheet;
import project.sprites.DecorationSpriteSheet.Decoration;

public class Tutorial2Loader implements LevelLoader {

    private static final DecorationSpriteSheet decor = DecorationSpriteSheet.getInstance();

    @Override
    public void load(GameLoop loop) throws Exception {
        int groundLevel = MAX_GRID_Y - 2;

        // Background
        loop.addGameObject(new BackgroundImage(-1, -4));

        // Ground
        loop.addGameObject(new Ground(-1, groundLevel, 7, 3));
        loop.addGameObject(new MovingPlatform(8, groundLevel - 2, 13, groundLevel - 2, 3, 1.0, 3));
        loop.addGameObject(new MovingPlatform(17, groundLevel - 3, 17, groundLevel - 10, 3, 5.0, 3));
        loop.addGameObject(new Ground(22, groundLevel - 11, 7, 14));

        // Player
        Player player = loop.addGameObject(new Player(2, groundLevel - 2));

        // Level Exit
        loop.addGameObject(new LevelExit(Level.Main1, player, 22, groundLevel - 16));

        // Decor
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.SmallBush2), 2, groundLevel - 1));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 4, groundLevel - 1));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 26, groundLevel - 12));

        // UI Overlay
        loop.addGameObject(new LifeCounterOverlay());
    }
}
