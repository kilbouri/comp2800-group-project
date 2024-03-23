package project.gameobjects;

import java.awt.image.BufferedImage;

public class MovingPlatform extends Block {

    private int startX, endX;
    private int startY, endY;
    private double period;
    private double timer;
    private double inversePeriod;

    public MovingPlatform(
            BufferedImage image,
            int startX, int startY,
            int endX, int endY,
            double tStart,
            double period,
            int width, int height) {
        super(
                image,
                (int) lerp(startX, endX, (tStart % period) / period),
                (int) lerp(startY, endY, (tStart % period) / period),
                width, height);

        this.startX = startX;
        this.startY = startY;

        this.endX = endX;
        this.endY = endY;

        this.inversePeriod = 1.0 / period;
        this.period = period;
        this.timer = tStart;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        timer += deltaTime;

        if (timer >= period) {
            int temp;

            temp = startX;
            startX = endX;
            endX = temp;

            temp = startY;
            startY = endY;
            endY = temp;

            timer -= period;
        }

        transform.x = lerp(startX, endX, timer * inversePeriod);
        transform.y = lerp(startY, endY, timer * inversePeriod);
    }

    private static double lerp(double a, double b, double t) {
        return ((1.0 - t) * a) + (t * b);
    }
}
