package engine;
import java.util.Objects;

/**
 * The base class for all components in the game engine.
 * A component represents a behavior or functionality that can be attached to a game object.
 */
public abstract class Component {
    protected GameObject parentObject;

    /**
     * Sets the game object that this component is attached to.
     * Note: no need to call this method directly, as it is called automatically
     * when adding a component to a game object.
     *
     * @param gameObject The game object to set as the parent object.
     */
    protected void setGameObject(GameObject gameObject) {
        this.parentObject = gameObject;
    }

    /**
     * Updates the component's state based on the elapsed time since the last
     * update.
     * Note: all components need to implement this method to define their behavior.
     * However, it doesn't need to be called afterwards, as it is called
     * automatically by the game loop.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    public abstract void update(double deltaTime);

    /**
     * Gets the game object that this component is attached to.
     *
     * @return The parent game object.
     */
    public GameObject getParentObject() {
        return parentObject;
    }

    /**
     * Generates a hash code for this component.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(parentObject);
    }
}
