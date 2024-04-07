package project.gameobjects.triggers;

import static project.levels.Level.GRID_SIZE;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.physics.BoxCollider;
import engine.physics.CollisionEvent;
import engine.physics.Trigger;
import engine.sprites.SpriteRenderer;
import engine.sprites.SpriteUtils;
import project.PlayerAttributes;
import project.gameobjects.Player;
import project.levels.Level;

public class LevelExit extends Trigger {
    private static final BufferedImage sourceImage = loadSourceImage();

    private static final int GRID_WIDTH = 5;
    private static final int GRID_HEIGHT = 5;

    private Player player;
    private Level nextLevel;

    private SpriteRenderer spriteComponent;

    public LevelExit(Level nextLevel, Player player, double gridX, double gridY) {
        super(
                gridX * GRID_SIZE, gridY * GRID_SIZE,
                GRID_WIDTH * GRID_SIZE, GRID_HEIGHT * GRID_SIZE,
                new BoxCollider(new Rectangle2D.Double(70, 130, 15, 30)));

        this.player = player;
        this.nextLevel = nextLevel;

        addComponent(spriteComponent = new SpriteRenderer(sourceImage));
    }

    @Override
    public void render(Graphics2D graphics) {
        spriteComponent.render(graphics);
        super.render(graphics);
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        if (event.getOther(this) != player) {
            return;
        }

        PlayerAttributes.levelsCompleted = Math.max(
                PlayerAttributes.levelsCompleted,
                nextLevel.ordinal());
        getGameLoop().loadLevel(nextLevel.getLoader());
    }

    private static BufferedImage loadSourceImage() {
        try {
            return SpriteUtils.load("sprites/prod/world/exit.png");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
