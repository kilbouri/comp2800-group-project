package project;

import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.SpriteSheet;
import engine.SpriteUtils;

public class PlaceholderSpriteSheet extends SpriteSheet {

    private static PlaceholderSpriteSheet instance;

    public static PlaceholderSpriteSheet getInstance() {
        if (instance == null) {
            try {
                final BufferedImage source = SpriteUtils.load("sprites/dev/placeholders.png");
                instance = new PlaceholderSpriteSheet(source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    protected PlaceholderSpriteSheet(BufferedImage image) {
        super(image, 32, 32);
    }
}
