package project;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.GameLoop;

public class MainLoop extends GameLoop {

    public static final int SCREEN_W = 900;
    public static final int SCREEN_H = 600;

    public MainLoop() {
        super(20);
        setSize(SCREEN_W, SCREEN_H);
    }

    @Override
    public void beforeRender(Graphics2D graphics) {
        graphics.setBackground(Color.gray);
        graphics.clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void afterRender(Graphics2D graphics) {
        graphics.setColor(Color.green);
        renderEngineMetrics(graphics);
        renderWASD(graphics);
    }
}
