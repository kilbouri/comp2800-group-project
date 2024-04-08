package project.menus;

import javax.swing.*;

import com.fasterxml.jackson.databind.introspect.ClassIntrospector;

import engine.sprites.SpriteUtils;
import project.PlayerAttributes;
import project.ProjectWindow;
import project.levels.Level;
import project.ui.FancyButton;
import project.ui.FancyLabel;
import project.ui.UIConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenu extends JPanel implements Menu {
    private static final Level[] allLevels = Level.values();

    private FancyButton defaultFocusButton;
    FancyButton continueButton;

    private BufferedImage backgroundImage;

    public StartMenu(ProjectWindow projectWindow) {
        super(new GridBagLayout());

        loadBackgroundImage(); // Load the background image

        // Create buttons
        FancyButton startGameButton = new FancyButton("Start Game");
        FancyButton quitButton = new FancyButton("Quit");
        FancyButton customizeButton = new FancyButton("Customize Character");
        continueButton = new FancyButton("Continue");

        defaultFocusButton = startGameButton;

        for (FancyButton b : new FancyButton[] {
                startGameButton, continueButton,
                quitButton, customizeButton }) {

            b.setPreferredSize(UIConstants.BUTTON_RECTANGLE_SIZE);
            b.setFont(UIConstants.FONT_MEDIUM);
            b.setBackground(UIConstants.PRIMARY_COLOR);
            b.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
            b.setBorderRadius(UIConstants.BORDER_RADIUS);
        }

        // Set layout to center the buttons
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

        if (PlayerAttributes.levelsCompleted == 0) {
            continueButton.setEnabled(false);
        }

        startGameButton.addActionListener((e) -> projectWindow.switchMenu(Menu.LEVELS));
        continueButton.addActionListener((e) -> projectWindow.loadLevel(allLevels[PlayerAttributes.levelsCompleted]));
        customizeButton.addActionListener((e) -> projectWindow.switchMenu(Menu.CUSTOMIZATION));
        quitButton.addActionListener((e) -> System.exit(0));
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

    @Override
    public void onShown() {
        if (PlayerAttributes.levelsCompleted != PlayerAttributes.NO_LEVELS_COMPLETE && continueButton != null
                && !continueButton.isEnabled()) {
            continueButton.setEnabled(true);
        }
        defaultFocusButton.requestFocus();
    }

    @Override
    public void onHidden() {
    }
}
