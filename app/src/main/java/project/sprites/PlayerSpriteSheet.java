package project.sprites;

import java.io.IOException;

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

    public PlayerSpriteSheet(PantColor pantColor) throws IOException {
        super(SpriteUtils.load(pantColor.getResourceName()), 140, 200);
    }
}
