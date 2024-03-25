package project.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class startMenu extends JPanel {

    //todo: add a way to attach/deattach this start menu, and all submenus from the JFrame instead of setting visibility off and on
    //todo: I already was going to do that but I need some sleep so later aligator!
    JFrame parentFrame;

    public startMenu() {

        // fixing up the title
        JLabel title;
        title = new JLabel("Fancy Pants");
        title.setFont(new Font("Futura", Font.BOLD, 100));
        title.setForeground(new Color(205, 252, 76));

        // Create buttons
        JButton startGameButton = new JButton("Start Game");
        JButton continueButton = new JButton("Continue");
        JButton settingsButton = new JButton("Settings");
        JButton quitButton = new JButton("Quit");
        JButton customizeButton = new JButton("Customize Character");

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(200, 50); // Width: 200, Height: 50
        startGameButton.setPreferredSize(buttonSize);
        continueButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);
        customizeButton.setPreferredSize(buttonSize);

        // fixing up button text size
        startGameButton.setFont(new Font("Futura", Font.ITALIC, 15));
        continueButton.setFont(new Font("Futura", Font.ITALIC, 15));
        settingsButton.setFont(new Font("Futura", Font.ITALIC, 15));
        quitButton.setFont(new Font("Futura", Font.ITALIC, 15));
        customizeButton.setFont(new Font("Futura", Font.ITALIC, 15));

        // button colours
        startGameButton.setBackground(new Color(206, 237, 233));
        continueButton.setBackground(new Color(206, 237, 233));
        settingsButton.setBackground(new Color(206, 237, 233));
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
        add(settingsButton, gbc);
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
                setVisible(false);
                new continueScreen();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new settingsScreen();
            }
        });

        customizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    new characterCustomizerScreen();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
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

    public void setParentFrame(JFrame frame) {
        if (parentFrame == null) {
            parentFrame = frame;
        }
    }
}