package project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import engine.ExpiringGameObject;

public class MovingSquare extends ExpiringGameObject {

    Rectangle2D.Double self;
    private double sideLength;
    private double xMin;
    private double xMax;

    private Color color = Color.white;

    public MovingSquare(double xMin, double xMax, double y, double sideLength) {
        super(5);

        self = new Rectangle2D.Double(xMin, y, sideLength, sideLength);
        this.sideLength = sideLength;
        this.xMax = xMax;
        this.xMin = xMin;
    }

    public void setColor(Color newColor) {
        this.color = newColor;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        self.x += 100 * deltaTime;
        self.x = self.x % (xMax + sideLength - xMin) - sideLength + xMin;
    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fill(self);
    }
}
