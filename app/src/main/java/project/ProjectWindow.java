package project;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.*;

import project.menus.CharacterCustomization;
import project.menus.startMenu;

public class ProjectWindow extends JFrame {

    MainLoop loop = new MainLoop();
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 600;

    public ProjectWindow() {
        super("COMP 2800 Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        startMenu startMenu = new startMenu(this);
        CharacterCustomization characterCustomization = new CharacterCustomization(this);

        container.add(startMenu, "startMenu");
        container.add(characterCustomization, "characterCustomization");

        cardLayout.show(container, "startMenu");
        add(container);
        setVisible(true);

        // add(loop);

        // Must be after the window is set visible
        // loop.loadLevel(Level.Developer2.getLoader());
        // loop.setAntialiased(true);
        // loop.start();
    }

    public void SwitchMenu(String menuName) {
        cardLayout.show(container, menuName);
    }
}
