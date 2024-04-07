package project;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;
import project.levels.Level;
import project.menus.LevelsMenu;
import project.menus.*;

public class ProjectWindow extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel container = new JPanel(cardLayout);

    private MainLoop loop = new MainLoop(this);
    private LevelsMenu levelsMenu = new LevelsMenu(this);
    private StartMenu startMenu = new StartMenu(this);
    private CharacterCustomization customizationMenu = new CharacterCustomization(this);

    public ProjectWindow() {
        super("COMP 2800 Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(Level.SCREEN_WIDTH_PX, Level.SCREEN_HEIGHT_PX));
        setResizable(false);

        container.add(startMenu, Menus.START);
        container.add(levelsMenu, Menus.LEVELS);
        container.add(customizationMenu, Menus.CUSTOMIZATION);
        container.add(loop, Menus.GAME);
        add(container);

        switchMenu(Menus.START);

        pack();
        setVisible(true);
    }

    public void switchMenu(String menuName) {
        loop.stop(false);
        cardLayout.show(container, menuName);

        switch (menuName) {
            case Menus.START:
                startMenu.requestFocus();
                break;

            case Menus.LEVELS:
                levelsMenu.refreshButtonLocks();
                levelsMenu.requestFocus();
                break;

            case Menus.GAME:
                loop.stop(true); // ensure loop is ded
                loop.start();
                loop.requestFocus();
                break;
        }

        repaint();
    }

    public void loadLevel(Level level) {
        loop.loadLevel(level.getLoader());
        switchMenu(Menus.GAME);
    }
}
