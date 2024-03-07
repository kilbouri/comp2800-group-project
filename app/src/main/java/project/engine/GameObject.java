package assignment2.engine;

import java.awt.Graphics2D;

public interface GameObject {
    /**
     * Runs once per frame.
     * 
     * @param deltaTime the time that has elapsed since the last update.
     */
    void update(final double deltaTime);

    /**
     * Draw the object. Runs once and only once after each `update`.
     * 
     * @param graphics a graphics object to draw the object with
     */
    void render(Graphics2D graphics);
}
