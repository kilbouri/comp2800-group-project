package project;

import engine.GameLoop;
import project.GameObjects.Ground;
import project.GameObjects.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class MainLoop extends GameLoop {

  private static final Dimension SCREEN_SIZE = new Dimension(900, 600);

  Ground ground = new Ground(
    0,
    500,
    PlaceholderSpriteSheet.getInstance().getTile(0),
    29,
    1
  );

  Player player = new Player(
    PlaceholderSpriteSheet.getInstance().getTile(3),
    100,
    500 - PlaceholderSpriteSheet.getInstance().getTileHeight()
  );

  public MainLoop() {
    super(20);
    setSize(SCREEN_SIZE);

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
