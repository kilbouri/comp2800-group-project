package engine.core;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import engine.physics.CollisionEvent;

/**
 * The base class for all game objects in the game engine.
 * It provides common functionality and methods that can be
 * overridden by subclasses.
 */
public abstract class GameObject implements Comparable<GameObject> {

    private int layer;
    private GameLoop associatedLoop;
    private List<Component> components = new ArrayList<>();
    protected Rectangle2D.Double transform;

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
    protected final void setGameLoop(GameLoop loop) throws UnsupportedOperationException {
        boolean isLeavingLoop = loop == null;
        boolean isChangingLoops = (this.associatedLoop != null) && (this.associatedLoop != loop);

        if (!isLeavingLoop && isChangingLoops) {
            throw new UnsupportedOperationException("GameObject already belongs to a GameLoop");
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
     * Runs once per frame. Implementors must call super.update().
     *
     * @param deltaTime the time that has elapsed since the last update.
     */
    public void update(final double deltaTime) {
        updateComponents(deltaTime);
    }

    /**
     * Draw the object. Runs once and only once after each `update`.
     *
     * @param graphics a graphics object to draw the object with
     */
    public abstract void render(Graphics2D graphics);

    /**
     * Called when this GameObject collides with another GameObject.
     *
     * @param other the collision event containing information about the collision.
     */
    public void onCollisionEnter(CollisionEvent event) {
    }

    /**
     * Called when this GameObject continues colliding with another GameObject.
     * It is guaranteed that this method is only called from the second frame
     * and onward of a collision. Specifically,
     * {@link #onCollisionEnter(CollisionEvent)} is called on the frame the
     * collision starts, {@link #onCollisionExit(CollisionEvent)} is called on the
     * frame the collision ends, and this method is called on every frame in
     * between.
     *
     * @param event the collision event containing information about the collision.
     */
    public void onCollisionStay(CollisionEvent event) {
    }

    /**
     * Called when a collision between this game object and another game object
     * ends.
     *
     * @param event the collision event containing information about the collision.
     */
    public void onCollisionExit(CollisionEvent event) {
    }

    public void setPosition(double x, double y) {
        transform.x = x;
        transform.y = y;
    }

    /**
     * Adds a component to this GameObject.
     *
     * @param component the component to add
     */
    public void addComponent(Component component) {
        if (component.getParentObject() != null) {
            throw new UnsupportedOperationException("Component already belongs to another GameObject");
        }
        if (components.contains(component)) {
            throw new UnsupportedOperationException("Component already added to GameObject");
        }
        components.add(component);
        component.setGameObject(this);
    }

    /**
     * Returns the component of the specified type if it exists in the GameObject.
     *
     * @param <T>            the type of the component to retrieve
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
     * Note: this method is called in the Game loop and should not be called
     * manually.
     *
     * @param deltaTime the time that has elapsed since the last update.
     */
    public void updateComponents(final double deltaTime) {
        for (Component component : components) {
            component.update(deltaTime);
        }
    }

    /**
     * Returns the transform of this GameObject.
     *
     * @return the transform of this GameObject
     */
    public Rectangle2D.Double getTransform() {
        return transform;
    }

    @Override
    public final int compareTo(GameObject o) {
        return Integer.compare(layer, o.layer);
    }
}
