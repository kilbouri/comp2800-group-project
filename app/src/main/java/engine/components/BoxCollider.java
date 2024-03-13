package engine.components;

import engine.Component;
import engine.GameObject;
import java.util.List;

/**
 * The BoxCollider class represents a component that defines a rectangular bounding box for collision detection.
 * It extends the Component class and provides methods for checking collisions with other BoxColliders.
 * Note: for this to work, the GameObject must have a Transform component.
 * @see Transform
 */
public class BoxCollider extends Component {

  public double width, height; // Size of the bounding box

  /**
   * Constructs a BoxCollider with the specified width and height.
   *
   * @param width  the width of the bounding box
   * @param height the height of the bounding box
   */
  public BoxCollider(double width, double height) {
    this.width = width;
    this.height = height;
    this.setGameObject(ParentObject);
  }

  /**
   * Checks collision with another BoxCollider.
   *
   * @param other the BoxCollider to check collision with
   * @return true if a collision occurs, false otherwise
   */
  public boolean collidesWith(BoxCollider other) {
    Transform transform = ParentObject.getComponent(Transform.class);
    Transform otherTransform = other.ParentObject.getComponent(Transform.class);
    if (transform == null || otherTransform == null) {
      return false;
    }
    double x = transform.x;
    double y = transform.y;
    double otherX = otherTransform.x;
    double otherY = otherTransform.y;

    return (
      x < otherX + other.width &&
      x + width > otherX &&
      y < otherY + other.height &&
      y + height > otherY
    );
  }

  /**
   * Updates the BoxCollider component.
   *
   * @param deltaTime the time elapsed since the last update
   */
  @Override
  public void update(double deltaTime) {
    // Collision detection logic can be placed here, if needed
  }

  /**
   * Checks collisions with a list of GameObjects and responds to collisions if they occur.
   *
   * @param gameObjects the list of GameObjects to check collisions with
   */
  public void checkCollisions(List<GameObject> gameObjects) {
    for (GameObject otherObject : gameObjects) {
      if (otherObject != ParentObject) { // Avoid self-collision
        BoxCollider otherCollision = otherObject.getComponent(
          BoxCollider.class
        );
        if (otherCollision != null && this.collidesWith(otherCollision)) {
          respondToCollision(otherCollision);
        }
      }
    }
  }

  private void respondToCollision(BoxCollider other) {
    ParentObject.onCollision(other.ParentObject);
  }
}
