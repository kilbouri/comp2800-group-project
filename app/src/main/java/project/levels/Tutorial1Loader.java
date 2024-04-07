package project.levels;

import static project.levels.Level.GRID_SIZE;
import static project.levels.Level.MAX_GRID_Y;

import java.awt.image.BufferedImage;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.Animation;
import engine.sprites.SpriteUtils;
import project.gameobjects.AnimatedSprite;
import project.gameobjects.Player;
import project.gameobjects.StaticSprite;
import project.gameobjects.blocks.FloatingGround;
import project.gameobjects.blocks.Ground;
import project.gameobjects.triggers.LevelExit;
import project.sprites.DecorationSpriteSheet;
import project.sprites.KeyboardExtraSheet;
import project.sprites.KeyboardMainSheet;
import project.sprites.DecorationSpriteSheet.Decoration;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Tutorial1Loader implements LevelLoader {
    private static final KeyboardMainSheet mainKeys = KeyboardMainSheet.getInstance();
    private static final KeyboardExtraSheet extraKeys = KeyboardExtraSheet.getInstance();
    private static final DecorationSpriteSheet decor = DecorationSpriteSheet.getInstance();
    private static final String backgroundResource = "sprites/prod/world/sky.png";

    @Override
    public void load(GameLoop loop) throws Exception {

        BufferedImage[] a = {
                mainKeys.getKey(KeyboardMainSheet.Key.A, false),
                mainKeys.getKey(KeyboardMainSheet.Key.A, true),
        };

        BufferedImage[] d = {
                mainKeys.getKey(KeyboardMainSheet.Key.D, false),
                mainKeys.getKey(KeyboardMainSheet.Key.D, true),
        };

        BufferedImage[] space = {
                extraKeys.getKey(KeyboardExtraSheet.Key.Space, false),
                extraKeys.getKey(KeyboardExtraSheet.Key.Space, true),
        };

        // Upscale the sprites to 2x, because they're small
        for (BufferedImage[] arr : new BufferedImage[][] { a, d, space }) {
            for (int i = 0; i < arr.length; i++) {
                BufferedImage img = arr[i];
                arr[i] = SpriteUtils.scale(img, img.getWidth() * 2, img.getHeight() * 2);
            }
        }

        Animation aAnim = new Animation(2, a);
        Animation dAnim = new Animation(2, d);
        Animation spaceAnim = new Animation(2, space);

        int groundLevel = MAX_GRID_Y - 2;

        // Background
        loop.addGameObject(new StaticSprite(SpriteUtils.load(backgroundResource), -1, -4));

        // Platforms
        loop.addGameObject(new Ground(-1, groundLevel - 0, 7, 3));
        loop.addGameObject(new FloatingGround(8, groundLevel - 2, 3));
        loop.addGameObject(new FloatingGround(15, groundLevel - 4, 3));
        loop.addGameObject(new FloatingGround(6, groundLevel - 7, 4));
        loop.addGameObject(new Ground(22, groundLevel - 6, 7, 9));

        // Player
        Player player = loop.addGameObject(new Player(PantColor.Blue, 2, groundLevel - 2));

        // Controls
        loop.addGameObject(new AnimatedSprite(aAnim, 1, groundLevel - 3));
        loop.addGameObject(new AnimatedSprite(dAnim, 3, groundLevel - 3));
        loop.addGameObject(new AnimatedSprite(spaceAnim, 2, groundLevel - 4))
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
}
