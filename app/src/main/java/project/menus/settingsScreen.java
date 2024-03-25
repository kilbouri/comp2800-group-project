package project.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class settingsScreen extends JFrame {
    public settingsScreen(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,600);
        setTitle("Settings Screen");
        setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());


        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

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

        //save settings button
        JButton saveButton = new JButton("Save Settings");
        saveButton.setFont(new Font("Futura", Font.ITALIC, 15));
        saveButton.setForeground(Color.BLACK); // Set text color
        saveButton.setBackground(new Color(206, 237, 233));
        saveButton.setPreferredSize(buttonSize);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to save character sprite
                // You can implement this functionality as needed
            }
        });

        panel.add(backButton, BorderLayout.SOUTH);
        panel.add(saveButton, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);

    }
}
