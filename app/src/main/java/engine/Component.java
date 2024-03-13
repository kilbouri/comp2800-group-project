package engine;

/**
 * The Component class is an abstract class that represents a component of a game object.
 * It provides a base class for all components in the game engine.
 */
public abstract class Component {
    protected GameObject ParentObject;

    /**
     * Sets the game object that this component is attached to.
     * Note: no need to call this method directly, as it is called automatically when adding a component to a game object.
     * @param gameObject The game object to set as the parent object.
     */
    public void setGameObject(GameObject gameObject) {
        this.ParentObject = gameObject;
    }

    /**
     * Updates the component's state based on the elapsed time since the last update.
     * Note: all components need to implement this method to define their behavior.
     * However, it doesn't need to be called afterwards, as it is called automatically by the game loop.
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    public abstract void update(final double deltaTime);
}
