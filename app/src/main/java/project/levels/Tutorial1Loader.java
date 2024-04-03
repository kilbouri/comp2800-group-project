package project.levels;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.Animation;
import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;
import project.PlaceholderSpriteSheet;
import project.gameobjects.AnimatedSprite;
import project.gameobjects.Block;
import project.gameobjects.LevelSwitchTrigger;
import project.gameobjects.Player;
import project.sprites.KeyboardExtraSheet;
import project.sprites.KeyboardMainSheet;

public class Tutorial1Loader implements LevelLoader {
    @Override
    public void load(GameLoop loop) {
        final SpriteSheet placeholders = PlaceholderSpriteSheet.getInstance();
        final KeyboardMainSheet mainKeys = KeyboardMainSheet.getInstance();
        final KeyboardExtraSheet extraKeys = KeyboardExtraSheet.getInstance();

        /**
         * @formatter:off
         *            space        F
         *  a d       _____        F
         * __P_____           _____F_
         * @formatter:on
         */

        BufferedImage groundSprite = placeholders.getTile(0);
        BufferedImage playerSprite = placeholders.getTile(1);

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

        final int groundLevel = 500;
        final int pitWidth = 175;

        loop.addGameObject(new Block(groundSprite, 0, groundLevel, 200, 100));
        loop.addGameObject(new Block(groundSprite, 200 + pitWidth, groundLevel - 100, pitWidth, 200));
        loop.addGameObject(new Block(groundSprite, 200 + 3 * pitWidth, groundLevel, 900 - (200 + 3 * pitWidth), 100));

        Player player = new Player(playerSprite, 50, groundLevel - placeholders.getTileHeight());
        Rectangle2D.Double playerTrans = player.getTransform();

        AnimatedSprite moveLeft = new AnimatedSprite(aAnim, 0, 0);
        AnimatedSprite moveRight = new AnimatedSprite(dAnim, 0, 0);
        AnimatedSprite jump = new AnimatedSprite(spaceAnim, 200 + (pitWidth / 2) - 32, playerTrans.y - 32);

        moveLeft.getTransform().x = playerTrans.x - aAnim.getSprite().getWidth() - 8;
        moveRight.getTransform().x = playerTrans.x + playerTrans.width + 8;
        moveLeft.getTransform().y = moveRight.getTransform().y = playerTrans.y - 32;

        loop.addGameObject(player);
        loop.addGameObject(moveLeft);
        loop.addGameObject(moveRight);
        loop.addGameObject(jump);

        loop.addGameObject(new LevelSwitchTrigger(player, Level.Tutorial2, 850, 100, 20, groundLevel - 100))
                .setDebug(true);
        ;
    }
}
