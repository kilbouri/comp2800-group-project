package project.levels;

import static project.levels.Level.GRID_SIZE;
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
        loop.addGameObject(new FloatingGround(19, groundLevel - 4, 3))
                .getTransform().x -= 0.5 * GRID_SIZE;
        loop.addGameObject(new MovingPlatform(16, groundLevel - 6, 5, groundLevel - 6, 2, 2.0, 4.0));
        loop.addGameObject(new FloatingGround(1, 9, 4))
                .getTransform().x -= 0.5 * GRID_SIZE;
        loop.addGameObject(new FloatingGround(8, 7, 4))
                .getTransform().x -= 0.5 * GRID_SIZE;
        loop.addGameObject(new FloatingGround(16, 5, 4))
                .getTransform().x -= 0.5 * GRID_SIZE;

        loop.addGameObject(new Ground(22, 7, 7, MAX_GRID_Y - 6));

        // Player
        Player player = loop.addGameObject(new Player(PantColor.Blue, 2, groundLevel - 2));

        // Level Exit
        loop.addGameObject(new LevelExit(Level.Main2, player, 22, 2));

        // Decorations
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 4, groundLevel - 1));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Bush1), 0, groundLevel - 1));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.SmallBush2), 19, groundLevel - 5))
                .getTransform().x -= 0.5 * GRID_SIZE;
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 20, groundLevel - 5))
                .getRenderer().setIsFlippedX(true);

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 1, 8));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.PinkMushroom), 3, 8))
                .getTransform().x -= 0.25 * GRID_SIZE;

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 9, 6));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 10, 6));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 9, 5))
                .getTransform().x += 0.25 * GRID_SIZE;

        StaticSprite miniOrangeMushroom = new StaticSprite(decor.getDecoration(Decoration.OrangeMushroom), 8, 6);
        miniOrangeMushroom.getRenderer().setScale(0.75);
        miniOrangeMushroom.getTransform().y += 0.25 * GRID_SIZE;
        loop.addGameObject(miniOrangeMushroom);

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Stump), 26, 6));
    }
}

class Main2Loader implements LevelLoader {

    @Override
    public void load(GameLoop loop) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

}
