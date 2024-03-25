package project;

import javax.swing.*;
import project.menus.startMenu;


public class ProjectWindow extends JFrame {

    MainLoop loop = new MainLoop();
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 600;

    public ProjectWindow() {
        super("COMP 2800 Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

        // add(loop);

        // pack();
        startMenu startMenu = new startMenu();
        add(startMenu);

        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Must be after the window is set visible
        // loop.loadLevel(Level.Developer2.getLoader());
        // loop.setAntialiased(true);
        // loop.start();
    }


}
