package project.menus;

import javax.swing.*;
import engine.sprites.SpriteUtils;
import project.ProjectWindow;
import project.levels.Level;
import project.ui.FancyButton;
import project.ui.FancyLabel;
import project.ui.UIConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenu extends JPanel {

    ProjectWindow projectWindow;
    private BufferedImage backgroundImage;
    private int levelsCompleted = 0;

    public StartMenu(ProjectWindow projectWindow) {
        this.projectWindow = projectWindow;
        loadBackgroundImage(); // Load the background image

        // Create buttons
        FancyButton startGameButton = new FancyButton("Start Game");
        FancyButton continueButton = new FancyButton("Continue");
        FancyButton quitButton = new FancyButton("Quit");
        FancyButton customizeButton = new FancyButton("Customize Character");

        // Set preferred size for buttons
        startGameButton.setPreferredSize(UIConstants.BUTTON_RECTANGLE_SIZE);
        continueButton.setPreferredSize(UIConstants.BUTTON_RECTANGLE_SIZE);
        quitButton.setPreferredSize(UIConstants.BUTTON_RECTANGLE_SIZE);
        customizeButton.setPreferredSize(UIConstants.BUTTON_RECTANGLE_SIZE);

        // fixing up button text size
        startGameButton.setFont(UIConstants.FONT_MEDIUM);
        continueButton.setFont(UIConstants.FONT_MEDIUM);
        quitButton.setFont(UIConstants.FONT_MEDIUM);
        customizeButton.setFont(UIConstants.FONT_MEDIUM);
        // button colours
        startGameButton.setBackground(UIConstants.PRIMARY_COLOR);
        continueButton.setBackground(UIConstants.PRIMARY_COLOR);
        quitButton.setBackground(UIConstants.PRIMARY_COLOR);
        customizeButton.setBackground(UIConstants.PRIMARY_COLOR);

        startGameButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
        continueButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
        quitButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
        customizeButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);

        // Set border radius for buttons
        startGameButton.setBorderRadius(UIConstants.BORDER_RADIUS);
        continueButton.setBorderRadius(UIConstants.BORDER_RADIUS);
        quitButton.setBorderRadius(UIConstants.BORDER_RADIUS);
        customizeButton.setBorderRadius(UIConstants.BORDER_RADIUS);

        // Set layout to center the buttons
        setLayout(new GridBagLayout());
        FancyLabel title = new FancyLabel("Fancy Pants");
        title.setFont(new Font(Font.DIALOG, Font.BOLD, 100));
        title.setForeground(UIConstants.SECONDARY_COLOR);
        title.setStrokeColor(Color.white);

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

        if (levelsCompleted == 0) {
            continueButton.setEnabled(false);
        }

        // creating the button listeners here
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectWindow.switchMenu(Menus.LEVELS_MENU);
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectWindow.startLoop(Level.values()[levelsCompleted]);
            }
        });

        customizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectWindow.switchMenu(Menus.CHARACTER_CUSTOMIZATION);
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
