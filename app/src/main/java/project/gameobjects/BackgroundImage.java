package project.gameobjects;

import java.io.IOException;

import engine.sprites.SpriteUtils;

public class BackgroundImage extends StaticSprite {

    private static final String backgroundResource = "sprites/prod/world/sky.png";

    public BackgroundImage(int gridX, int gridY) throws IOException {
        super(SpriteUtils.load(backgroundResource), gridX, gridY);
        setLayer(-100);
    }
}
