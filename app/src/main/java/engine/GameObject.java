package engine;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for all game objects in the game engine.
 * It provides common functionality and methods that can be
 * overridden by subclasses.
 */
public abstract class GameObject implements Comparable<GameObject> {

  private int layer;
  private GameLoop associatedLoop;
  private List<Component> components = new ArrayList<>();

  /**
   * Sets the layer of this GameObject. Higher layers
   * render later.
   *
   * @param layer the new layer
   */
  public final void setLayer(int layer) {
    this.layer = layer;
  }

  /**
   * Sets the GameLoop that this GameObject is a part of.
   *
   * @param loop the loop this object should move to
   * @throws UnsupportedOperationException if this object already belongs to
   *                                       another loop
   */
  protected final void setGameLoop(GameLoop loop)
    throws UnsupportedOperationException {
    boolean isLeavingLoop = loop == null;
    boolean isChangingLoops =
      (this.associatedLoop != null) && (this.associatedLoop != loop);

    if (!isLeavingLoop && isChangingLoops) {
      throw new UnsupportedOperationException(
        "GameObject already belongs to a GameLoop"
      );
    }

    this.associatedLoop = loop;
  }

  /**
   * Destroys this GameObject. It is removed from any loop it is
   * a part of. The caller should release any references to the object
   * to allow the object to be disposed of.
   */
  public final void destroy() {
    associatedLoop.removeGameObject(this);
  }

  /**
   * Allows a GameObject to access the loop it is
   * a part of.
   *
   * @return the game loop associated with this object
   */
  public final GameLoop getGameLoop() {
    return associatedLoop;
  }

  /**
   * Runs once per frame.
   *
   * @param deltaTime the time that has elapsed since the last update.
   */
  public abstract void update(final double deltaTime);

  /**
   * Draw the object. Runs once and only once after each `update`.
   *
   * @param graphics a graphics object to draw the object with
   */
  public abstract void render(Graphics2D graphics);

  /**
   * Called when this GameObject collides with another GameObject.
   *
   * @param other the other GameObject involved in the collision
   */
  public abstract void onCollision(GameObject other);

  /**
   * Adds a component to this GameObject.
   *
   * @param component the component to add
   */
  public void addComponent(Component component) {
    components.add(component);
    component.setGameObject(this);
  }

  /**
   * Returns the component of the specified type if it exists in the GameObject.
   *
   * @param <T> the type of the component to retrieve
   * @param componentClass the class object representing the type of the component
   * @return the component of the specified type if found, otherwise null
   */
  public <T extends Component> T getComponent(Class<T> componentClass) {
    for (Component component : components) {
      if (componentClass.isInstance(component)) {
        return componentClass.cast(component); // Cast the component to the correct type
      }
    }
    return null; // Return null if the component is not found
  }

  /**
   * Updates all the components attached to this GameObject.
   * Note: this method is called in the Game loop and should not be called manually.
   * @param deltaTime the time that has elapsed since the last update.
   */
  public void updateComponents(final double deltaTime) {
    for (Component component : components) {
      component.update(deltaTime);
    }
  }

  @Override
  public final int compareTo(GameObject o) {
    return Integer.compare(layer, o.layer);
  }
}
