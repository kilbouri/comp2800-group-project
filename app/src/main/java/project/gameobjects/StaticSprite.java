package project.gameobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.core.GameObject;
import engine.sprites.Sprite;

public class StaticSprite extends GameObject {

    private Sprite spriteRenderer;

    public StaticSprite(BufferedImage image, double x, double y) {
        addComponent(spriteRenderer = new Sprite(image));

        this.transform.x = x;
        this.transform.y = y;
        this.transform.width = image.getWidth();
        this.transform.height = image.getHeight();
    }

    @Override
    public void render(Graphics2D graphics) {
        spriteRenderer.render(graphics);
    }

    public void setSprite(BufferedImage image) {
        spriteRenderer.setDisplayImage(image);
        this.transform.width = image.getWidth();
        this.transform.height = image.getHeight();
    }
}
