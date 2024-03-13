package engine.components;

import engine.Component;

/**
 * The TransformComponent class represents the position of a GameObject in 2D space.
 * It is a component that can be attached to a GameObject to define its position.
 */
public class Transform extends Component {

  public double x, y; // Position of the GameObject

  /**
   * Constructs a TransformComponent with the specified initial position.
   *
   * @param ParentObject The parent GameObject to which this TransformComponent belongs.
   * @param x            The initial x-coordinate of the position.
   * @param y            The initial y-coordinate of the position.
   */
  public Transform(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void update(double deltaTime) {
    // No update logic for TransformComponent

  }
}
