package project.menus;

import javax.swing.*;

import project.ProjectWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class startMenu extends JPanel {

    ProjectWindow parentFrame;

    public startMenu(ProjectWindow projectWindow) {
        this.parentFrame = projectWindow;
        // fixing up the title
        JLabel title;
        title = new JLabel("Fancy Pants");
        title.setFont(new Font("Futura", Font.BOLD, 100));
        title.setForeground(new Color(205, 252, 76));

        // Create buttons
        JButton startGameButton = new JButton("Start Game");
        JButton continueButton = new JButton("Continue");
        JButton quitButton = new JButton("Quit");
        JButton customizeButton = new JButton("Customize Character");

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(200, 50); // Width: 200, Height: 50
        startGameButton.setPreferredSize(buttonSize);
        continueButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);
        customizeButton.setPreferredSize(buttonSize);

        // fixing up button text size
        startGameButton.setFont(new Font("Futura", Font.ITALIC, 15));
        continueButton.setFont(new Font("Futura", Font.ITALIC, 15));
        quitButton.setFont(new Font("Futura", Font.ITALIC, 15));
        customizeButton.setFont(new Font("Futura", Font.ITALIC, 15));

        // button colours
        startGameButton.setBackground(new Color(206, 237, 233));
        continueButton.setBackground(new Color(206, 237, 233));
        quitButton.setBackground(new Color(206, 237, 233));
        customizeButton.setBackground(new Color(206, 237, 233));

        // Set layout to center the buttons
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Add buttons to the frame
        add(title, gbc);
        add(startGameButton, gbc);
        add(continueButton, gbc);
        add(customizeButton, gbc);
        add(quitButton, gbc);

        // creating the button listeners here
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        customizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectWindow.SwitchMenu("characterCustomization");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // start game action listener will call the gameloop

        // character customization will call caracterCusotmization

        // quit will close window

        // settings will open the settings class

        // continue will open the continue class

    }
}