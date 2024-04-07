package project;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.GameLoop;

public class MainLoop extends GameLoop {

    public static final int SCREEN_W = 900;
    public static final int SCREEN_H = 600;

    private ProjectWindow window;

    public MainLoop(ProjectWindow window) {
        super(20);
        setSize(SCREEN_W, SCREEN_H);
        this.window = window;
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

    public void goToMenu(String menuName) {
        window.switchMenu(menuName);
    }
}
