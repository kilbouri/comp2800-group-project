package project;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.GameLoop;

public class MainLoop extends GameLoop {

    private int layerSign = 1;
    private MovingSquare red = new MovingSquare(100, 800, 300.0 - 50, 100);
    private MovingSquare blue = new MovingSquare(100, 800, 300.0 - 50, 100);

    public MainLoop() {
        super(20);
        setSize(900, 600);

        addGameObject(red).setColor(Color.red);
        addGameObject(blue).setColor(Color.blue);
    }

    @Override
    public void update(double deltaTime) {
        layerSign *= -1;

        red.setLayer(-layerSign);
        blue.setLayer(layerSign);
    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, getWidth(), getHeight());

        if (layerSign == 1) {
            // blue should be on top
            graphics.setColor(Color.blue);
        } else {
            // red should be on top
            graphics.setColor(Color.red);
        }

        graphics.fillRect(10, 10, 25, 25);

        graphics.setColor(Color.green);
        renderEngineMetrics(graphics);
    }
}
