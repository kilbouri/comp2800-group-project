package project.levels;

import static project.levels.Level.MAX_GRID_Y;

import engine.core.*;
import project.gameobjects.*;
import project.gameobjects.blocks.*;
import project.gameobjects.triggers.*;
import project.sprites.DecorationSpriteSheet;
import project.sprites.DecorationSpriteSheet.Decoration;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Main1Loader implements LevelLoader {
    private static final DecorationSpriteSheet decor = DecorationSpriteSheet.getInstance();

    @Override
    public void load(GameLoop loop) throws Exception {

        int groundLevel = MAX_GRID_Y - 2;

        // Background
        loop.addGameObject(new BackgroundImage(-1, -4));

        // Platforms
        loop.addGameObject(new Ground(-1, groundLevel, 7, 3));
        loop.addGameObject(new MovingPlatform(6, groundLevel - 2, 16, groundLevel - 2, 2, 2.0, 4.0));
        loop.addGameObject(new Ground(19.5, groundLevel - 4, 5, MAX_GRID_Y - (groundLevel - 4)));
        loop.addGameObject(new MovingPlatform(16, groundLevel - 6, 5, groundLevel - 6, 2, 2.0, 4.0));
        loop.addGameObject(new FloatingGround(0.5, 9, 4));
        loop.addGameObject(new FloatingGround(7.5, 7, 4));
        loop.addGameObject(new FloatingGround(15.5, 5, 4));
        loop.addGameObject(new Ground(22, 7, 7, MAX_GRID_Y - 6, true))
                .setLayer(-1);

        // Player
        Player player = loop.addGameObject(new Player(PantColor.Blue, 2, groundLevel - 2));

        // Level Exit
        loop.addGameObject(new LevelExit(Level.Main2, player, 22, 2));

        // Decorations
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 4, groundLevel - 1));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Bush1), 0, groundLevel - 1));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.SmallBush2), 20.5, groundLevel - 5));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 23, groundLevel - 5))
                .getRenderer().setIsFlippedX(true);

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 1, 8));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.PinkMushroom), 2.75, 8));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 9, 6));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 10, 6));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 9.625, 5.25))
                .getRenderer().setScale(0.75);
        ;

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.OrangeMushroom), 8, 6.25))
                .getRenderer().setScale(0.75);

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Stump), 26, 6));
    }
}
