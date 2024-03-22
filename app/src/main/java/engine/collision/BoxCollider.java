package engine.collision;

import java.awt.geom.Rectangle2D;
import engine.Component;

/**
 * The BoxCollider class represents a collider component that can be attached to
 * a game object.
 */
public class BoxCollider extends Component {

    /**
     * @return the Rectangle2D representing the bounds of the box collider
     */
    public Rectangle2D getBox() {
        return parentObject.getTransform();
    }

    /**
     * Updates the BoxCollider component.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(double deltaTime) {
    }
}
