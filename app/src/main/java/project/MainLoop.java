package project;

import engine.GameLoop;
import engine.collision.BoxCollider;
import project.gameObjects.Block;
import project.gameObjects.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class MainLoop extends GameLoop {

    private static final Dimension SCREEN_SIZE = new Dimension(900, 600);

    Block ground = new Block(
            PlaceholderSpriteSheet.getInstance().getTile(0),
            0,
            600 - 100,
            900,
            60);

    Player player = new Player(
            PlaceholderSpriteSheet.getInstance().getTile(3),
            100,
            500 - PlaceholderSpriteSheet.getInstance().getTileHeight());

    Block wall = new Block(
            PlaceholderSpriteSheet.getInstance().getTile(1),
            300,
            600 - 100 - 100,
            60,
            100);
    Block wall2 = new Block(
            PlaceholderSpriteSheet.getInstance().getTile(1),
            20,
            600 - 100 - 100,
            60,
            100);

    Block floatingBlock = new Block(
            PlaceholderSpriteSheet.getInstance().getTile(2),
            180,
            600 - 100 - 200 - 32,
            60,
            200);
    Block floatingBlock2 = new Block(
            PlaceholderSpriteSheet.getInstance().getTile(2),
            300,
            600 - 100 - 200 - 32,
            300,
            30);
    Block floatingBlock3 = new Block(
            PlaceholderSpriteSheet.getInstance().getTile(2),
            600,
            600 - 100 - 200 - 32,
            60,
            200);
    public MainLoop() {
        super(20);
        setSize(SCREEN_SIZE);
        // The player always has to be added to the game loop first before any other
        // object
        ground.getComponent(BoxCollider.class).setCollisionLayer(engine.collision.CollisionLayer.GROUND);
        addGameObject(player);
        addGameObject(ground);
        addGameObject(wall);
        addGameObject(wall2);
        addGameObject(floatingBlock);
        addGameObject(floatingBlock2);
        addGameObject(floatingBlock3);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void beforeRender(Graphics2D graphics) {
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void afterRender(Graphics2D graphics) {
        graphics.setColor(Color.CYAN);
        renderEngineMetrics(graphics);
    }
}
