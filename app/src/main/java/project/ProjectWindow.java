package project;

import java.awt.CardLayout;
import javax.swing.*;
import project.levels.Level;
import project.menus.LevelsMenu;
import project.menus.*;

public class ProjectWindow extends JFrame {

    MainLoop loop = new MainLoop();
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 600;
    LevelsMenu levelsMenu;
    StartMenu startMenu;

    public ProjectWindow() {
        super("COMP 2800 Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);

        startMenu = new StartMenu(this);
        levelsMenu = new LevelsMenu(this);
        CharacterCustomization characterCustomization = new CharacterCustomization(this);

        container.add(startMenu, Menus.START_MENU);
        container.add(levelsMenu, Menus.LEVELS_MENU);
        container.add(characterCustomization, Menus.CHARACTER_CUSTOMIZATION);

        cardLayout.show(container, Menus.START_MENU);
        add(container);
        setVisible(true);
    }

    public void startLoop(Level level) {
        if (container.getParent() != null) {
            this.remove(container);
        }
        add(loop);
        loop.loadLevel(level.getLoader());
        loop.setAntialiased(false);
        loop.requestFocus();
        loop.start();
    }

    public void stopLoop() {
        remove(loop);
        add(container);
        cardLayout.show(container, Menus.START_MENU);
    }

    public void switchMenu(String menuName) {
        cardLayout.show(container, menuName);
        if (menuName.equals(Menus.LEVELS_MENU)) {
            levelsMenu.requestFocus();
        }
    }
}
