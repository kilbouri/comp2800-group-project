package project.menus;

import javax.swing.*;
import engine.sprites.SpriteUtils;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import project.ProjectWindow;
import project.gameobjects.FancyButton;

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
        this.InitializeUI();
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

    public void InitializeUI() {
        setLayout(new BorderLayout()); // Set the main layout to BorderLayout

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        FancyButton backButton = new FancyButton("<");
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.setNormalColor(new Color(247, 183, 7));
        backButton.setHoverColor(new Color(250, 211, 105));
        backButton.setBorderRadius(10);
        backButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
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
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
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
            levelButton.setPreferredSize(new Dimension(200, 50));
            levelButton.setNormalColor(new Color(247, 183, 7));
            levelButton.setHoverColor(new Color(250, 211, 105));
            levelButton.setBorderRadius(10);
            levelButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
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

}
