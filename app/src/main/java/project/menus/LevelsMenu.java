package project.menus;

import javax.swing.*;
import engine.sprites.SpriteUtils;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import project.ProjectWindow;
import project.user_interface.FancyButton;
import project.user_interface.UIConstants;

public class LevelsMenu extends JPanel {
    private int totalLevels;
    private int levelsCompleted = 1;
    ProjectWindow projectWindow;
    private BufferedImage backgroundImage;

    public LevelsMenu(ProjectWindow projectWindow) {
        this.projectWindow = projectWindow;
        totalLevels = project.levels.Level.values().length;
        this.setLayout(new GridBagLayout());
        this.loadBackgroundImage(); // Load the background image

        setLayout(new BorderLayout()); // Set the main layout to BorderLayout

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        FancyButton backButton = new FancyButton("<");
        backButton.setPreferredSize(UIConstants.BUTTON_SQUARE);
        backButton.setBackground(UIConstants.PRIMARY_COLOR);
        backButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
        backButton.setBorderRadius(10);
        backButton.setFont(UIConstants.FONT_MEDIUM);
        backButton.addActionListener(e -> {
            projectWindow.switchMenu("startMenu");
        });
        backPanel.add(backButton);
        backPanel.setOpaque(false); // Make the panel transparent
        // Adding backPanel to the top
        add(backPanel, BorderLayout.NORTH);

        // Central panel for the title and level buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Make the panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER; // This makes components span across the row
        gbc.anchor = GridBagConstraints.CENTER;

        // Create title label
        JLabel titleLabel = new JLabel("Choose a Level");
        titleLabel.setFont(UIConstants.FONT_LARGE);
        centerPanel.add(titleLabel, gbc);

        // Reset for level buttons
        gbc.gridwidth = 1;
        int row = 0; // Reset row for the level buttons
        for (int i = 0; i < totalLevels; i++) {
            if (i % 3 == 0 && i > 0) {
                row++;
            }
            gbc.gridx = i % 3; // Positions the button in the correct column
            gbc.gridy = row + 1; // Positions the button in the correct row, below the title
            FancyButton levelButton = new FancyButton("Level " + (i + 1));
            levelButton.setPreferredSize(UIConstants.BUTTON_RECTANGLE_SIZE);
            levelButton.setBackground(UIConstants.PRIMARY_COLOR);
            levelButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
            levelButton.setBorderRadius(10);
            levelButton.setFont(UIConstants.FONT_MEDIUM);
            if (i >= levelsCompleted) {
                levelButton.setEnabled(false);
            }
            final int tmp = i;
            levelButton.addActionListener(e -> {
                projectWindow.startLoop(project.levels.Level.values()[tmp]);
            });
            centerPanel.add(levelButton, gbc);
        }

        // Add the centerPanel to the main panel
        add(centerPanel, BorderLayout.CENTER);

    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = SpriteUtils.load("sprites/prod/levelsmenubg.png");
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
