package project.GameObjects;

import engine.GameObject;
import engine.Keyboard;
import engine.components.BoxCollider;
import engine.components.Sprite;
import engine.components.Transform;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

  private static final int MAX_MIDAIR_JUMPS = 1;
  private int midairJumps = 0;

  protected double vSpeed = 0;
  protected double hSpeed = 150;

  private boolean isGrounded = true;
  private boolean spaceReleasedSinceLastJump = true;

  protected Transform transformComponent;
  protected BoxCollider boxCollider;
  protected Sprite sprite;

  public Player(BufferedImage sprite) {
    this.sprite = new Sprite(this, sprite);
  }

  public Player(BufferedImage sprite, double x, double y) {
    this.sprite = new Sprite(this, new Point2D.Double(x, y), sprite);
    boxCollider =
      new BoxCollider(
        this.sprite.getDisplayImage().getWidth(),
        this.sprite.getDisplayImage().getHeight()
      );
    transformComponent = new Transform(x, y);
    this.addComponent(boxCollider);
    this.addComponent(transformComponent);
    this.addComponent(this.sprite);
  }

  @Override
  public void update(double deltaTime) {
    if (isGrounded) {
      vSpeed = 0;
    } else {
      // fake increased gravity when falling so it looks right
      double gravityStrength = (vSpeed > 0) ? 0.8 : 1;
      vSpeed -= 98.1 * 10 * deltaTime * gravityStrength;
    }

    boolean canJump = isGrounded || midairJumps < MAX_MIDAIR_JUMPS;
    if (
      canJump && spaceReleasedSinceLastJump && Keyboard.held(KeyEvent.VK_SPACE)
    ) {
      vSpeed = 500;

      if (!isGrounded) {
        midairJumps += 1;
      }

      isGrounded = false;
      spaceReleasedSinceLastJump = false;
    }

    if (!Keyboard.held(KeyEvent.VK_SPACE)) {
      spaceReleasedSinceLastJump = true;
    }

    // @formatter:off
        int dx = 0;
    if (Keyboard.held(KeyEvent.VK_A)) { dx -= 1; }
    if (Keyboard.held(KeyEvent.VK_D)) { dx += 1; }
    // @formatter:on

    sprite.setPosition(transformComponent.x, transformComponent.y);
    transformComponent.x += dx * hSpeed * deltaTime;
    transformComponent.y -= vSpeed * deltaTime;
  }

  @Override
  public void render(Graphics2D g) {
    sprite.render(g);
  }

  public void setGrounded(boolean grounded) {
    isGrounded = grounded;
    if (isGrounded) {
      // Reset vertical movement to prevent falling
      vSpeed = 0;
    }
    midairJumps = 0;
  }

  @Override
  public void onCollision(GameObject other) {
    if (other instanceof Ground) {
      setGrounded(true);
    }
  }
}
