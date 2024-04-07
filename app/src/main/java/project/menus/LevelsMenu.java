package project.menus;

import javax.swing.*;
import engine.sprites.SpriteUtils;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import project.PlayerAttributes;
import project.ProjectWindow;
import project.levels.Level;
import project.ui.FancyButton;
import project.ui.UIConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LevelsMenu extends JPanel {
    private static final Level[] allLevels = Level.values();

    private BufferedImage backgroundImage;

    private JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    private ArrayList<FancyButton> levelButtons = new ArrayList<>();

    public LevelsMenu(ProjectWindow projectWindow) {
        super(new BorderLayout());

        this.loadBackgroundImage();

        FancyButton backButton = new FancyButton("<");
        backButton.setPreferredSize(UIConstants.BUTTON_SQUARE);
        backButton.setBackground(UIConstants.PRIMARY_COLOR);
        backButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
        backButton.setBorderRadius(UIConstants.BORDER_RADIUS);
        backButton.setFont(UIConstants.FONT_MEDIUM);
        backButton.addActionListener((e) -> projectWindow.switchMenu(Menus.START));

        backPanel.add(backButton);
        backPanel.setOpaque(false);
        add(backPanel, BorderLayout.NORTH);

        // Central panel for the title and level buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;

        // Create title label
        JLabel titleLabel = new JLabel("Choose a Level");
        titleLabel.setFont(UIConstants.FONT_LARGE);
        centerPanel.add(titleLabel, constraints);

        // Reset for level buttons
        constraints.gridwidth = 1;
        constraints.gridy = 1;

        for (int i = 0; i < allLevels.length; i++) {
            constraints.gridy += (i % 3 == 0 && i != 0) ? 1 : 0;
            constraints.gridx = i % 3;

            FancyButton levelButton = new FancyButton("Level " + (i + 1));
            levelButton.setPreferredSize(UIConstants.BUTTON_RECTANGLE_SIZE);
            levelButton.setBackground(UIConstants.PRIMARY_COLOR);
            levelButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
            levelButton.setBorderRadius(10);
            levelButton.setFont(UIConstants.FONT_MEDIUM);

            final int levelindex = i; // stupid Java, why aren't primitives effectively final?
            levelButton.addActionListener(e -> {
                projectWindow.loadLevel(allLevels[levelindex]);
            });

            levelButtons.add(levelButton);
            centerPanel.add(levelButton, constraints);
        }

        refreshButtonLocks();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean fPressed = e.getKeyCode() == KeyEvent.VK_F;
                boolean shiftHeld = (e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0;

                if (shiftHeld && fPressed) {
                    unlockAllLevels();
                }
            }
        });

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

    private void unlockAllLevels() {
        System.err.println("Unlocking all levels!");
        PlayerAttributes.levelsCompleted = PlayerAttributes.ALL_LEVELS_COMPLETE;
        refreshButtonLocks();
    }

    public void refreshButtonLocks() {
        for (int i = 0; i < levelButtons.size(); i++) {
            // Lock the level if the level is not unlocked yet
            levelButtons.get(i).setEnabled(i <= PlayerAttributes.levelsCompleted);
        }
    }
}
