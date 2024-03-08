package project;

import javax.swing.*;

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
        loop.setAntialiased(true);
        loop.start();
    }
}
