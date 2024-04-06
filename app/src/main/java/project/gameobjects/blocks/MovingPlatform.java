package project.gameobjects.blocks;

import static project.levels.Level.GRID_SIZE;

import engine.core.MathExtensions;

public class MovingPlatform extends FloatingGround {

    private int startX, endX;
    private int startY, endY;
    private double period;
    private double timer;
    private double inversePeriod;

    public MovingPlatform(
            int gridStartX, int gridStartY,
            int gridEndX, int gridEndY,
            int gridWidth,
            double tStart,
            double period) {
        super(gridStartX, gridStartY, gridWidth);

        transform.x = (int) MathExtensions.lerp(gridStartX, gridEndX, (tStart % period) / period);
        transform.y = (int) MathExtensions.lerp(gridStartY, gridEndY, (tStart % period) / period);

        this.startX = gridStartX * GRID_SIZE;
        this.startY = gridStartY * GRID_SIZE;

        this.endX = gridEndX * GRID_SIZE;
        this.endY = gridEndY * GRID_SIZE;

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
