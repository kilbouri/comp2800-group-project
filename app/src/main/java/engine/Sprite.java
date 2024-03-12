package engine;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Sprite extends GameObject {
    protected Point2D.Double position;
    protected BufferedImage displayImage;

    public Sprite(Point2D.Double position, BufferedImage sprite) {
        this.position = position;
        this.displayImage = sprite;
    }

    public Sprite(BufferedImage sprite) {
        this(new Point2D.Double(0, 0), sprite);
    }

    @Override
    public void update(double deltaTime) {
    }

    public void setPosition(double x, double y) {
        position.x = x;
        position.y = y;
    }

    @Override
    public void render(Graphics2D graphics) {
        int spriteX = (int) (position.x);
        int spriteY = (int) (position.y);

        graphics.drawImage(displayImage, spriteX, spriteY, null);
    }
}
