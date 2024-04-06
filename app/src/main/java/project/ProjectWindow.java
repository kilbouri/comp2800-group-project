package project;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;
import project.levels.Level;
import project.menus.LevelsMenu;
import project.menus.*;

public class ProjectWindow extends JFrame {

    MainLoop loop = new MainLoop();
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    public ProjectWindow() {
        super("COMP 2800 Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(Level.SCREEN_WIDTH_PX, Level.SCREEN_HEIGHT_PX));
        setResizable(false);

        StartMenu startMenu = new StartMenu(this);
        LevelsMenu levelsMenu = new LevelsMenu(this);
        CharacterCustomization characterCustomization = new CharacterCustomization(this);

        container.add(startMenu, "startMenu");
        container.add(levelsMenu, "levelsMenu");
        container.add(characterCustomization, "characterCustomization");

        cardLayout.show(container, "startMenu");

        add(container);
        pack();
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
        // TODO: this will need to be reworked!
        remove(loop);
        add(container);
        cardLayout.show(container, "startMenu");
    }

    public void switchMenu(String menuName) {
        cardLayout.show(container, menuName);
    }
}
