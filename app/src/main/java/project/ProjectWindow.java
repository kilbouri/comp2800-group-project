package project;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ProjectWindow extends JFrame {
    public ProjectWindow() {
        super("COMP 2800 Project");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("<html><b>TODO: implement this window<b>"));

        JButton quit = new JButton("Exit");
        quit.addActionListener((e) -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        add(quit);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
