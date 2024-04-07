package project;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.*;
import project.levels.Level;
import project.menus.LevelsMenu;
import project.menus.*;

public class ProjectWindow extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel container = new JPanel(cardLayout);

    private LevelsMenu levelsMenu = new LevelsMenu(this);
    private StartMenu startMenu = new StartMenu(this);
    private CustomizeMenu customizeMenu = new CustomizeMenu(this);
    private GamePanel gamePanel = new GamePanel(this);

    private Map<String, Menu> menus = Map.of(
            Menu.START, startMenu,
            Menu.LEVELS, levelsMenu,
            Menu.CUSTOMIZATION, customizeMenu,
            Menu.GAME, gamePanel);

    private Menu currentMenu = null;

    public ProjectWindow() {
        super("COMP 2800 Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(Level.SCREEN_WIDTH_PX, Level.SCREEN_HEIGHT_PX));
        setResizable(false);

        container.add(startMenu, Menu.START);
        container.add(levelsMenu, Menu.LEVELS);
        container.add(customizeMenu, Menu.CUSTOMIZATION);
        container.add(gamePanel, Menu.GAME);

        add(container);

        switchMenu(Menu.START);

        pack();
        setVisible(true);
    }

    public void switchMenu(String menuName) {
        if (currentMenu != null) {
            currentMenu.onHidden();
            currentMenu = null;
        }

        currentMenu = menus.get(menuName);
        cardLayout.show(container, menuName);
        currentMenu.onShown();
    }

    public void loadLevel(Level level) {
        gamePanel.getLoop().loadLevel(level.getLoader());
        switchMenu(Menu.GAME);
    }
}
