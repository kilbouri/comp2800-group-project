package project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import engine.core.GameLoop;
import engine.core.Keyboard;
import project.menus.GamePanel;

public class MainLoop extends GameLoop {

    public static final int SCREEN_W = 900;
    public static final int SCREEN_H = 600;

    private boolean paused = false;
    private boolean escapePressedLastFrame = false;

    private GamePanel parentPanel;

    public MainLoop(GamePanel parentPanel) {
        super(20);
        setSize(SCREEN_W, SCREEN_H);
        this.parentPanel = parentPanel;
    }

    @Override
    public void update(double deltaTime) {
        if (Keyboard.held(KeyEvent.VK_ESCAPE)) {
            if (!escapePressedLastFrame) {
                paused = !paused;
            }

            escapePressedLastFrame = true;
        } else {
            escapePressedLastFrame = false;
        }
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
        parentPanel.switchMenu(menuName);
    }
}
