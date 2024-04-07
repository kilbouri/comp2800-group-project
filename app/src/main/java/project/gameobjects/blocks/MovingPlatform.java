package project.gameobjects.blocks;

import static project.levels.Level.GRID_SIZE;

import engine.core.MathExtensions;

public class MovingPlatform extends FloatingGround {

    private double startX, endX;
    private double startY, endY;
    private double period;
    private double timer;
    private double inversePeriod;

    public MovingPlatform(
            double gridStartX, double gridStartY,
            double gridEndX, double gridEndY,
            int gridWidth,
            double tStart,
            double period) {
        super(gridStartX, gridStartY, gridWidth);

        this.startX = gridStartX * GRID_SIZE;
        this.startY = gridStartY * GRID_SIZE;

        this.endX = gridEndX * GRID_SIZE;
        this.endY = gridEndY * GRID_SIZE;

        transform.x = (int) MathExtensions.lerp(startX, endX, (tStart % period) / period);
        transform.y = (int) MathExtensions.lerp(startY, endY, (tStart % period) / period);

        this.inversePeriod = 1.0 / period;
        this.period = period;
        this.timer = tStart;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        timer += deltaTime;

        if (timer >= period) {
            double temp;

            temp = startX;
            startX = endX;
            endX = temp;

            temp = startY;
            startY = endY;
            endY = temp;

            timer -= period;
        }

        if (startX != endX) {
            transform.x = MathExtensions.lerp(startX, endX, timer * inversePeriod);
        }

        if (startY != endY) {
            transform.y = MathExtensions.lerp(startY, endY, timer * inversePeriod);
        }
    }
}
