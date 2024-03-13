package project.GameObjects;

import engine.GameObject;
import engine.components.BoxCollider;
import engine.components.TiledSprite;
import engine.components.Transform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * The Ground class represents a tiled sprite that acts as a ground in a game.
 * It extends the TiledSprite class and provides a box collider for collision detection.
 */
public class Ground extends GameObject {

  BoxCollider boxCollider;
  TiledSprite TiledSprite;
  Transform transformComponent;

  /**
   * Constructs a Ground object with the specified position, image, and tile dimensions.
   *
   * @param x       The x-coordinate of the ground's position.
   * @param y       The y-coordinate of the ground's position.
   * @param image   The image used for rendering the ground.
   * @param tilesX  The number of tiles in the x-direction.
   * @param tilesY  The number of tiles in the y-direction.
   */
  public Ground(int x, int y, BufferedImage image, int tilesX, int tilesY) {
    TiledSprite =
      new TiledSprite(new Point2D.Double(x, y), image, tilesX, tilesY);
    boxCollider =
      new BoxCollider(image.getWidth() * tilesX, image.getHeight() * tilesY);
    transformComponent = new Transform(x, y);
    this.addComponent(transformComponent);
    this.addComponent(boxCollider);
    this.addComponent(TiledSprite);
  }

  @Override
  public void update(double deltaTime) {
    updateComponents(deltaTime);
  }

  @Override
  public void render(java.awt.Graphics2D graphics) {
    TiledSprite.render(graphics);
  }

  @Override
  public void onCollision(GameObject other) {}
}
