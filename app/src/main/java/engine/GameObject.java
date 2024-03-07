package engine;

import java.awt.Graphics2D;

public abstract class GameObject implements Comparable<GameObject> {

    private int layer;

    /**
     * Sets the layer of this GameObject. Higher layers
     * render later.
     *
     * @param layer the new layer
     */
    public void setLayer(int layer) {
        this.layer = layer;
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
    public int compareTo(GameObject o) {
        return Integer.compare(layer, o.layer);
    }
}
