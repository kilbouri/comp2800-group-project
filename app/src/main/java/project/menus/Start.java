package project.menus;

import javax.swing.*;
import engine.sprites.SpriteUtils;
import project.ProjectWindow;
import project.UI.FancyButton;
import project.UI.FancyLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Start extends JPanel {

    ProjectWindow projectWindow;
    private BufferedImage backgroundImage;
    private int levelsCompleted = 1;

    public Start(ProjectWindow projectWindow) {
        this.projectWindow = projectWindow;
        loadBackgroundImage(); // Load the background image

        // Create buttons
        FancyButton startGameButton = new FancyButton("Start Game");
        FancyButton continueButton = new FancyButton("Continue");
        FancyButton quitButton = new FancyButton("Quit");
        FancyButton customizeButton = new FancyButton("Customize Character");

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(200, 50); // Width: 200, Height: 50
        startGameButton.setPreferredSize(buttonSize);
        continueButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);
        customizeButton.setPreferredSize(buttonSize);

        // fixing up button text size
        startGameButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        continueButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        quitButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        customizeButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        // button colours
        startGameButton.setBackground(new Color(247, 183, 7));
        continueButton.setBackground(new Color(247, 183, 7));
        quitButton.setBackground(new Color(247, 183, 7));
        customizeButton.setBackground(new Color(247, 183, 7));

        startGameButton.setHoverColor(new Color(250, 211, 105));
        continueButton.setHoverColor(new Color(250, 211, 105));
        quitButton.setHoverColor(new Color(250, 211, 105));
        customizeButton.setHoverColor(new Color(250, 211, 105));

        // Set border radius for buttons
        startGameButton.setBorderRadius(10);
        continueButton.setBorderRadius(10);
        quitButton.setBorderRadius(10);
        customizeButton.setBorderRadius(10);

        // Set layout to center the buttons
        setLayout(new GridBagLayout());
        FancyLabel title = new FancyLabel("Fancy Pants");
        title.setFont(new Font(Font.DIALOG, Font.BOLD, 100));
        title.setForeground(new Color(95, 36, 219));
        title.setStrokeColor(new Color(255, 255, 255));

        // Adjust constraints for the buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER; // Align buttons to the right under the title
        gbc.insets = new Insets(10, 20, 10, 10);

        // Add components
        add(title, gbc); // Add the title to the panel
        add(startGameButton, gbc);
        add(continueButton, gbc);
        add(customizeButton, gbc);
        add(quitButton, gbc);

        // creating the button listeners here
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectWindow.switchMenu("levelsMenu");
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int tmp = levelsCompleted;
                projectWindow.startLoop(project.levels.Level.values()[tmp]);

            }
        });

        customizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectWindow.switchMenu("characterCustomization");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = SpriteUtils.load("sprites/prod/startmenubg.png");
        } catch (IOException e) {
            System.err.println("Failed to load background image for LevelsMenu");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

}
