package project.levels;

import static project.levels.Level.GRID_SIZE;
import static project.levels.Level.MAX_GRID_Y;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.Animation;
import engine.sprites.SpriteUtils;
import project.gameobjects.AnimatedSprite;
import project.gameobjects.BackgroundImage;
import project.gameobjects.Player;
import project.gameobjects.StaticSprite;
import project.gameobjects.blocks.FloatingGround;
import project.gameobjects.blocks.Ground;
import project.gameobjects.triggers.LevelExit;
import project.sprites.DecorationSpriteSheet;
import project.sprites.KeyboardExtraSheet;
import project.sprites.KeyboardExtraSheet.ExtraKey;
import project.sprites.KeyboardMainSheet;
import project.sprites.DecorationSpriteSheet.Decoration;
import project.sprites.KeyboardMainSheet.MainKey;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Tutorial1Loader implements LevelLoader {
    private static final KeyboardMainSheet mainKeys = KeyboardMainSheet.getInstance();
    private static final KeyboardExtraSheet extraKeys = KeyboardExtraSheet.getInstance();
    private static final DecorationSpriteSheet decor = DecorationSpriteSheet.getInstance();

    @Override
    public void load(GameLoop loop) throws Exception {

        int groundLevel = MAX_GRID_Y - 2;

        // Background
        loop.addGameObject(new BackgroundImage(-1, -4));

        // Platforms
        loop.addGameObject(new Ground(-1, groundLevel, 7, 3));
        loop.addGameObject(new FloatingGround(8, groundLevel - 2, 3));
        loop.addGameObject(new FloatingGround(15, groundLevel - 4, 3));
        loop.addGameObject(new FloatingGround(6, groundLevel - 7, 4));
        loop.addGameObject(new Ground(22, groundLevel - 6, 7, 9));

        // Player
        Player player = loop.addGameObject(new Player(PantColor.Blue, 2, groundLevel - 2));

        // Controls
        loop.addGameObject(new AnimatedSprite(getKeyAnimation(MainKey.A), 1, groundLevel - 3));
        loop.addGameObject(new AnimatedSprite(getKeyAnimation(MainKey.D), 3, groundLevel - 3));
        loop.addGameObject(new AnimatedSprite(getKeyAnimation(ExtraKey.Space), 2, groundLevel - 4))
                .getTransform().x -= 0.5 * GRID_SIZE;

        // Level Exit
        loop.addGameObject(new LevelExit(Level.Tutorial2, player, 22, groundLevel - 11));

        // Decorations
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.ArrowSign), 4, groundLevel - 1));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Bush2), 0, groundLevel - 1));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 9, groundLevel - 3))
                .getTransform().x -= 0.5 * GRID_SIZE;
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 10, groundLevel - 3))
                .getTransform().x -= 0.5 * GRID_SIZE;
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Crate), 9, groundLevel - 4));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Bush1), 7, groundLevel - 8));
        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.PinkMushroom), 9, groundLevel - 8));

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.OrangeMushroom), 16, groundLevel - 5))
                .getTransform().x -= 0.5 * GRID_SIZE;

        loop.addGameObject(new StaticSprite(decor.getDecoration(Decoration.Boulder), 26, groundLevel - 7));
    }

    private static Animation getKeyAnimation(MainKey key) {
        final int scaledW = mainKeys.getTileWidth() * 2;
        final int scaledH = mainKeys.getTileHeight() * 2;

        return new Animation(2,
                SpriteUtils.scale(mainKeys.getKey(key, false), scaledW, scaledH),
                SpriteUtils.scale(mainKeys.getKey(key, true), scaledW, scaledH));
    }

    private static Animation getKeyAnimation(ExtraKey key) {
        final int scaledW = extraKeys.getTileWidth() * 2;
        final int scaledH = extraKeys.getTileHeight() * 2;

        return new Animation(2,
                SpriteUtils.scale(extraKeys.getKey(key, false), scaledW, scaledH),
                SpriteUtils.scale(extraKeys.getKey(key, true), scaledW, scaledH));
    }
}
