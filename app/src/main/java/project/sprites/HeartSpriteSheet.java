package project.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;

public class HeartSpriteSheet extends SpriteSheet {

    public enum Heart {
        Full(0),
        Half(1),
        Empty(2);

        private int index;

        Heart(int index) {
            this.index = index;
        }
    }

    private static HeartSpriteSheet instance;

    public static HeartSpriteSheet getInstance() {
        if (instance == null) {
            try {
                instance = new HeartSpriteSheet();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private HeartSpriteSheet() throws IOException {
        super(SpriteUtils.load("sprites/prod/player/hearts.png"), 32, 32);
    }

    public BufferedImage getHeart(Heart heart) {
        return getTile(heart.index);
    }
}
