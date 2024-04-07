package project.levels;

import static project.levels.Level.MAX_GRID_Y;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.SpriteUtils;
import project.gameobjects.Player;
import project.gameobjects.triggers.LevelExit;
import project.gameobjects.StaticSprite;
import project.gameobjects.blocks.Ground;
import project.gameobjects.blocks.MovingPlatform;
import project.sprites.DecorationSpriteSheet;
import project.sprites.DecorationSpriteSheet.Decoration;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Tutorial2Loader implements LevelLoader {

    private static final DecorationSpriteSheet decor = DecorationSpriteSheet.getInstance();
    private static final String backgroundResource = "sprites/prod/world/sky.png";

    @Override
    public void load(GameLoop loop) throws Exception {
        int groundLevel = MAX_GRID_Y - 2;

        // Background
        loop.addGameObject(new StaticSprite(SpriteUtils.load(backgroundResource), -1, -4));

        // Ground
        loop.addGameObject(new Ground(-1, groundLevel, 7, 3));
        loop.addGameObject(new MovingPlatform(8, groundLevel - 2, 13, groundLevel - 2, 3, 0.0, 3));
        loop.addGameObject(new MovingPlatform(17, groundLevel - 4, 17, groundLevel - 9, 3, 5.0, 4.2));
        loop.addGameObject(new Ground(22, groundLevel - 11, 7, 14));

        // Player
        Player player = loop.addGameObject(new Player(PantColor.Blue, 2, groundLevel - 2));

        // Level Exit
        loop.addGameObject(new LevelExit(Level.Tutorial2, player, 22, groundLevel - 16));

        // Decor
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 4, groundLevel - 1));

    }
}
