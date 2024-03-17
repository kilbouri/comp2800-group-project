package project;

import engine.GameLoop;
import project.GameObjects.Block;
import project.GameObjects.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class MainLoop extends GameLoop {

    private static final Dimension SCREEN_SIZE = new Dimension(900, 600);

    Block ground = new Block(
        PlaceholderSpriteSheet.getInstance().getTile(0),
        0,
        600-100,
        900,
        60);

    Player player = new Player(
            PlaceholderSpriteSheet.getInstance().getTile(3),
            100,
            500 - PlaceholderSpriteSheet.getInstance().getTileHeight());

    Block wall = new Block(
            PlaceholderSpriteSheet.getInstance().getTile(1),
            300,
            600-100-60,
            60,
            60);
    public MainLoop() {
        super(20);
        setSize(SCREEN_SIZE);

        addGameObject(wall);
        addGameObject(ground);
        addGameObject(player);
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
