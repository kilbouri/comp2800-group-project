package project.levels;

import static project.levels.Level.MAX_GRID_Y;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import project.gameobjects.BackgroundImage;
import project.gameobjects.Player;
import project.gameobjects.StaticSprite;
import project.gameobjects.blocks.FloatingGround;
import project.gameobjects.blocks.Ground;
import project.gameobjects.blocks.MovingPlatform;
import project.gameobjects.triggers.LevelExit;
import project.sprites.DecorationSpriteSheet;
import project.sprites.DecorationSpriteSheet.Decoration;

public class Main2Loader implements LevelLoader {
    private static final DecorationSpriteSheet decor = DecorationSpriteSheet.getInstance();

    @Override
    public void load(GameLoop loop) throws Exception {

        int groundLevel = MAX_GRID_Y - 3;

        // Background
        loop.addGameObject(new BackgroundImage(-1, -4));

        // Platforms
        loop.addGameObject(new Ground(-1, groundLevel, 5, 3));

        loop.addGameObject(new FloatingGround(4, groundLevel - 2.5, 3));
        loop.addGameObject(new FloatingGround(0, groundLevel - 4.5, 3));
        loop.addGameObject(new FloatingGround(4, groundLevel - 6.5, 3));
        loop.addGameObject(new FloatingGround(0, groundLevel - 8.5, 3));
        loop.addGameObject(new FloatingGround(4, groundLevel - 10.5, 3));
        loop.addGameObject(new FloatingGround(0, groundLevel - 12.5, 3));
        loop.addGameObject(new Ground(7.5, groundLevel - 14, 2, 18));

        loop.addGameObject(new Ground(12.5, -1, 2, groundLevel));

        loop.addGameObject(new MovingPlatform(10, groundLevel + 2, 20, groundLevel + 2, 2, 0.0, 3));

        loop.addGameObject(new FloatingGround(23, groundLevel - 0.0, 3));
        loop.addGameObject(new FloatingGround(19, groundLevel - 2.5, 3));
        loop.addGameObject(new FloatingGround(15, groundLevel - 5.0, 3));
        loop.addGameObject(new FloatingGround(19, groundLevel - 7.5, 3));

        loop.addGameObject(new Ground(23.5, 7, 7, MAX_GRID_Y - 6, true)).setLayer(-1);

        // Player
        Player player = loop.addGameObject(new Player(2, groundLevel - 2));

        // Level Exit
        loop.addGameObject(new LevelExit(Level.Main2, player, 23.5, 2));

        // Decorations
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Bush1), 1.25, groundLevel - 1));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.PinkMushroom), 1.5, groundLevel - 0.75))
                .getRenderer().setScale(0.75);

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 1, groundLevel - 13.5));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 7.75, groundLevel - 15));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Bush2), 19.25, groundLevel - 3.5));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.PinkMushroom), 15, groundLevel - 6));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.OrangeMushroom), 15.5, groundLevel - 5.75))
                .getRenderer().setScale(0.75);
    }
}
