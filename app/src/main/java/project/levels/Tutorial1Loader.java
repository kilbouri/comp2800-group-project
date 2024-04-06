package project.levels;

import static project.levels.Level.GRID_SIZE;
import static project.levels.Level.MAX_GRID_Y;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.Animation;
import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;
import project.PlaceholderSpriteSheet;
import project.gameobjects.AnimatedSprite;
import project.gameobjects.LevelSwitchTrigger;
import project.gameobjects.Player;
import project.gameobjects.blocks.Ground;
import project.sprites.KeyboardExtraSheet;
import project.sprites.KeyboardMainSheet;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Tutorial1Loader implements LevelLoader {
    private static final KeyboardMainSheet mainKeys = KeyboardMainSheet.getInstance();
    private static final KeyboardExtraSheet extraKeys = KeyboardExtraSheet.getInstance();

    @Override
    public void load(GameLoop loop) {

        /**
         * @formatter:off
         *            space        F
         *  a d       _____        F
         * __P_____           _____F_
         * @formatter:on
         */

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

        int x = 0;
        int groundLevel = MAX_GRID_Y - 2;
        loop.addGameObject(new Ground(x += 0, groundLevel - 0, 4, 2));
        loop.addGameObject(new Ground(x += 8, groundLevel - 2, 4, 4)); // todo: replace these with floaters
        loop.addGameObject(new Ground(x += 8, groundLevel - 4, 4, 6)); // todo: replace these with floaters
        loop.addGameObject(new Ground(x += 8, groundLevel - 6, 5, 8));

        Player player;
        try {
            player = new Player(PantColor.Blue, 2, groundLevel - 2);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Rectangle2D.Double playerTrans = player.getTransform();

        AnimatedSprite moveLeft = new AnimatedSprite(aAnim, 0, 0);
        AnimatedSprite moveRight = new AnimatedSprite(dAnim, 0, 0);
        AnimatedSprite jump = new AnimatedSprite(spaceAnim, 200 + 125 - 32, playerTrans.y - 32);

        moveLeft.getTransform().x = playerTrans.x - aAnim.getSprite().getWidth() - 8;
        moveRight.getTransform().x = playerTrans.x + playerTrans.width + 8;
        moveLeft.getTransform().y = moveRight.getTransform().y = playerTrans.y - 32;

        loop.addGameObject(player);
        loop.addGameObject(moveLeft);
        loop.addGameObject(moveRight);
        loop.addGameObject(jump);

        loop.addGameObject(
                new LevelSwitchTrigger(player, Level.Tutorial2, 850, 100, 20, (MAX_GRID_Y - 2) * GRID_SIZE - 100))
                .setDebug(true);
        ;
    }
}
