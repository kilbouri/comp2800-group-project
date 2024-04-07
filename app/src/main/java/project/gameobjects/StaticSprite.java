package project.gameobjects;

import static project.levels.Level.GRID_SIZE;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.core.GameObject;
import engine.sprites.SpriteRenderer;

public class StaticSprite extends GameObject {

    private SpriteRenderer spriteRenderer;

    public StaticSprite(BufferedImage image, int gridX, int gridY) {
        addComponent(spriteRenderer = new SpriteRenderer(image));

        this.transform.x = GRID_SIZE * gridX;
        this.transform.y = GRID_SIZE * gridY;
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

    public SpriteRenderer getRenderer() {
        return spriteRenderer;
    }
}
