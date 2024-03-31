package project.gameobjects;

import java.awt.image.BufferedImage;

import engine.core.MathExtensions;

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
                (int) MathExtensions.lerp(startX, endX, (tStart % period) / period),
                (int) MathExtensions.lerp(startY, endY, (tStart % period) / period),
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

        transform.x = MathExtensions.lerp(startX, endX, timer * inversePeriod);
        transform.y = MathExtensions.lerp(startY, endY, timer * inversePeriod);
    }
}
