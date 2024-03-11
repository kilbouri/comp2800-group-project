package engine;

import java.awt.Graphics2D;

public abstract class GameObject implements Comparable<GameObject> {

    private int layer;
    private GameLoop associatedLoop;

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

    @Override
    public final int compareTo(GameObject o) {
        return Integer.compare(layer, o.layer);
    }
}
