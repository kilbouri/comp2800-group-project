package project.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class continueScreen extends JFrame {
    public continueScreen(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,600);
        setTitle("Settings Screen");
        setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());


        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //back button takes you to the start menu
        JButton backButton = new JButton("Go back to Start Screen");
        backButton.setFont(new Font("Futura", Font.ITALIC, 15));
        backButton.setBackground(new Color(206, 237, 233));
        Dimension buttonSize = new Dimension(200, 50);
        backButton.setPreferredSize(buttonSize);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new startMenu();
                setVisible(false);
            }
        });

        JButton go = new JButton("Click to play Selected level!");
        go.setFont(new Font("Futura", Font.ITALIC, 15));
        go.setBackground(new Color(206, 237, 233));
        go.setPreferredSize(buttonSize);

        //goes to sleected level, must call that level here
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to go back to start menu
                //JOptionPane.showMessageDialog(characterCustomizerScreen.this, "Back to Start Menu");
                //call level here
                setVisible(false);
            }
        });

        panel.add(backButton,BorderLayout.SOUTH);
        panel.add(go,BorderLayout.EAST);
        add(panel);
        setVisible(true);

    }
}
