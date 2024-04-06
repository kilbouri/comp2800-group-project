package project.levels;

import static project.levels.Level.MAX_GRID_Y;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.core.GameLoop;
import engine.core.LevelLoader;
import engine.sprites.Animation;
import engine.sprites.SpriteUtils;
import project.gameobjects.AnimatedSprite;
import project.gameobjects.Player;
import project.gameobjects.blocks.FloatingGround;
import project.gameobjects.blocks.Ground;
import project.gameobjects.blocks.LevelExit;
import project.sprites.KeyboardExtraSheet;
import project.sprites.KeyboardMainSheet;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Tutorial1Loader implements LevelLoader {
    private static final KeyboardMainSheet mainKeys = KeyboardMainSheet.getInstance();
    private static final KeyboardExtraSheet extraKeys = KeyboardExtraSheet.getInstance();

    @Override
    public void load(GameLoop loop) {

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

        int x = -1;
        int groundLevel = MAX_GRID_Y - 2;
        loop.addGameObject(new Ground(x += 0, groundLevel - 0, 6, 3));
        loop.addGameObject(new FloatingGround(x += 9, groundLevel - 2, 3));
        loop.addGameObject(new FloatingGround(x += 7, groundLevel - 4, 3));
        loop.addGameObject(new Ground(x += 7, groundLevel - 6, 7, 9));

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

        LevelExit exit = new LevelExit(Level.Tutorial2, player, x + 2, groundLevel - 8);
        exit.setDebug(true);
        loop.addGameObject(exit);
    }
}
