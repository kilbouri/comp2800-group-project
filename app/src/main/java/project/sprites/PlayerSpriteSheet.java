package project.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;

public class PlayerSpriteSheet extends SpriteSheet {
    public enum PantColor {
        Blue("blue.png"),
        Green("green.png"),
        Maroon("maroon.png"),
        Orange("orange.png"),
        Gold("gold.png");

        private final String fileName;

        PantColor(String name) {
            this.fileName = name;
        }

        protected String getResourceName() {
            return "sprites/prod/player/" + fileName;
        }
    }

    private static EnumMap<PantColor, BufferedImage> imageCache = new EnumMap<>(PantColor.class);

    public PlayerSpriteSheet(PantColor pantColor) throws IOException {
        super(loadPantColorSheet(pantColor), 140, 200);
    }

    private static BufferedImage loadPantColorSheet(PantColor color) throws IOException {
        if (!imageCache.containsKey(color)) {
            imageCache.put(color, SpriteUtils.load(color.getResourceName()));
        }

        return imageCache.get(color);
    }
}
