package project.menus;

import project.BackgroundPanel;
import project.ProjectWindow;
import javax.swing.*;
import engine.sprites.Animation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import project.sprites.PlayerSpriteSheet;
import project.sprites.PlayerSpriteSheet.PantColor;

public class CharacterCustomization extends JPanel {

    private int currentSheetIndex;
    private JPanel leftPanel;
    ProjectWindow projectWindow;
    private PantColor currentSprite;
    private Animation blueIdle;
    private Animation goldIdle;
    private Animation greenIdle;
    private Animation maroonIdle;
    private Animation currentAnimation;

    public CharacterCustomization(ProjectWindow projectWindow) {

        this.projectWindow = projectWindow;
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel();

        // character sprite sheets using pantcolor enum
        // setting the animation variables here
        try {
            new PlayerSpriteSheet(PantColor.Blue);
            blueIdle = new Animation(new PlayerSpriteSheet(PantColor.Blue), 12, 0, 11);
            new PlayerSpriteSheet(PantColor.Gold);
            goldIdle = new Animation(new PlayerSpriteSheet(PantColor.Gold), 12, 0, 11);
            new PlayerSpriteSheet(PantColor.Green);
            greenIdle = new Animation(new PlayerSpriteSheet(PantColor.Green), 12, 0, 11);
            new PlayerSpriteSheet(PantColor.Maroon);
            maroonIdle = new Animation(new PlayerSpriteSheet(PantColor.Maroon), 12, 0, 11);
            currentSprite = PantColor.Blue;

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("files could not be read");
        }

        // change button initialized
        JButton changeButton = new JButton("Change Sprite");
        changeButton.setFont(new Font("Futura", Font.ITALIC, 15));
        changeButton.setBackground(new Color(206, 237, 233));

        // save button
        JButton saveButton = new JButton("Save Sprite");
        saveButton.setFont(new Font("Futura", Font.ITALIC, 15));
        saveButton.setForeground(Color.BLACK); // Set text color
        saveButton.setBackground(new Color(206, 237, 233));

        // back button
        JButton backButton = new JButton("Back to start menu");
        backButton.setFont(new Font("Futura", Font.ITALIC, 15));
        backButton.setBackground(new Color(206, 237, 233));

        // setting size of buttons
        Dimension buttonSize = new Dimension(200, 50); // Width: 200, Height: 50
        changeButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // creating panel for the character sprite to be placed in
        leftPanel = new LeftPanel();
        leftPanel.setLayout(new BorderLayout());

        // creating panel for the buttons
        // Set layout to center the buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 80);
        // adding buttons to panel
        rightPanel.add(changeButton, gbc);
        rightPanel.add(saveButton, gbc);
        // setting background to transparent
        rightPanel.add(backButton, gbc);
        rightPanel.setOpaque(false);
        // add right panel to left
        leftPanel.add(rightPanel, BorderLayout.EAST);
        // setting background to transparent
        leftPanel.setOpaque(false);
        // add left panel to the background panel
        backgroundPanel.add(leftPanel);
        // add background panel to the frame
        add(backgroundPanel);

        // Adding action listeners to buttons
        // what to do if change button is clicked
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // getting the ordinal value of the current sprite sheet
                currentSheetIndex = currentSprite.ordinal();
                // increment the index
                currentSheetIndex = (currentSheetIndex + 1) % PlayerSpriteSheet.PantColor.values().length;
                // updating the current sprite sheet
                currentSprite = PlayerSpriteSheet.PantColor.values()[currentSheetIndex];
                // calls leftPanel to be repainted, passing on the current spritesheet and the
                // animation that needs to be played
                // send currentanimation to leftpanel to repaint the screen
                if (currentSprite == PantColor.Blue) {
                    currentAnimation = blueIdle;
                } else if (currentSprite == PantColor.Gold) {
                    currentAnimation = goldIdle;
                } else if (currentSprite == PantColor.Green) {
                    currentAnimation = greenIdle;
                } else if (currentSprite == PantColor.Maroon) {
                    currentAnimation = maroonIdle;
                }
                leftPanel.repaint();
            }
        });

        // what to do if save button is clicked
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to save character spriteS
                JOptionPane.showMessageDialog(CharacterCustomization.this, "Sprite Saved");
            }
        });

        // what to do if back button is clicked
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to go back to start menu
                projectWindow.switchMenu("startMenu");
            }
        });
    }

    // creating the panel that can display the character images
    class LeftPanel extends JPanel {
        private long lastNanos;

        public LeftPanel() {
            lastNanos = System.nanoTime();
        }

        @Override
        protected void paintComponent(Graphics g) {
            currentAnimation.update(getDeltaTime());
            g.drawImage(currentAnimation.getSprite(), 300, 200, this);
            repaint();// causes a loop that repaints the component repeatedly
        }

        private double getDeltaTime() {
            long currentNanos = System.nanoTime();
            long nanosPassed = currentNanos - lastNanos;
            lastNanos = currentNanos;

            return (double) nanosPassed / 1.0e9;
        }

    }
}