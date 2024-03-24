package project;

import javax.swing.*;

import project.levels.Level;

public class ProjectWindow extends JFrame {

    MainLoop loop = new MainLoop();

    public ProjectWindow() {
        super("COMP 2800 Project");

        add(loop);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Must be after the window is set visible
        loop.loadLevel(Level.Developer2.getLoader());
        loop.setAntialiased(true);
        loop.start();
    }
}
