package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import engine.GameLoop;
import engine.GameObject;
import engine.TiledSprite;

public class MainLoop extends GameLoop {

    private static final Dimension SCREEN_SIZE = new Dimension(900, 600);

    GameObject ground = new TiledSprite(
            new Point2D.Double(0, 500),
            PlaceholderSpriteSheet.getInstance().getTile(0),
            200,
            (SCREEN_SIZE.height - 500) / PlaceholderSpriteSheet.getInstance().getTileHeight() + 1);

    Player player = new Player(
            PlaceholderSpriteSheet.getInstance().getTile(3),
            100,
            500 - PlaceholderSpriteSheet.getInstance().getTileHeight());

    public MainLoop() {
        super(20);
        setSize(SCREEN_SIZE);

        addGameObject(ground);
        addGameObject(player);
    }

    @Override
    public void update(double deltaTime) {
        if (player.getCollider().getBounds2D().getMaxY() >= 500) {
            player.setGrounded(true);
        }
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
