package engine.physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import engine.core.GameObject;

public abstract class Trigger extends GameObject {

    private static final Color DEBUG_COLOR = new Color(0.0f, 1.0f, 0.0f, 0.25f);

    protected BoxCollider collider;
    private boolean debug = false;

    /**
     * A trigger is a BoxCollider which is not intended to interact with objects.
     * Uses a default BoxCollider (that is, one which uses the object's transform
     * as its hitbox)
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width of the trigger
     * @param height the height of the trigger
     */
    public Trigger(double x, double y, double width, double height) {
        this(x, y, width, height, new BoxCollider());
    }

    /**
     * A trigger is a BoxCollider which is not intended to interact with objects.
     *
     * @param x        the x position
     * @param y        the y position
     * @param width    the width of the trigger
     * @param height   the height of the trigger
     * @param collider a custom collider component
     */
    public Trigger(double x, double y, double width, double height, BoxCollider collider) {
        this.transform = new Rectangle2D.Double(x, y, width, height);
        addComponent(this.collider = collider);
    }

    /**
     * When in debug mode, the trigger draws its outline
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void render(Graphics2D graphics) {
        if (debug) {
            graphics.setColor(DEBUG_COLOR);
            graphics.draw(collider.getBox());
        }
    }
}
