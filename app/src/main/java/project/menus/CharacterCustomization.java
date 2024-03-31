package project.menus;

import engine.sprites.SpriteUtils;
import project.BackgroundPanel;
import project.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CharacterCustomization extends JPanel {

    private BufferedImage[] spriteSheets;
    private int currentSheetIndex;
    private JPanel leftPanel;
    @SuppressWarnings("unused")
    private BufferedImage currentSpriteSheet;
    ProjectWindow projectWindow;

    public CharacterCustomization(ProjectWindow projectWindow) {

        this.projectWindow = projectWindow;
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel();

        // character sprite sheets
        spriteSheets = new BufferedImage[4];
        try {
            spriteSheets[0] = SpriteUtils.load("sprites/dev/customizedSprites/orangepants.png");
            spriteSheets[1] = SpriteUtils.load("sprites/dev/customizedSprites/bluepants.png");
            spriteSheets[2] = SpriteUtils.load("sprites/dev/customizedSprites/greenpants.png");
            spriteSheets[3] = SpriteUtils.load("sprites/dev/customizedSprites/redpants.png");
            currentSpriteSheet = spriteSheets[0];
            currentSheetIndex = 0;
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
                // Code to change character sprite
                /*
                 * if the current sheet index is less than the length of sprite sheets -1 then
                 * we increment it by 1. The % spritesheets.length causes it to wrap around to 0
                 * which moves
                 * to the first index of the array...this creates a loop through the indices of
                 * the spritesheets
                 */
                currentSheetIndex = (currentSheetIndex + 1) % spriteSheets.length;
                currentSpriteSheet = spriteSheets[currentSheetIndex];
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
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (spriteSheets[currentSheetIndex] != null) {
                int x = 200;
                int y = (getHeight() - spriteSheets[currentSheetIndex].getHeight()) / 2;
                g.drawImage(spriteSheets[currentSheetIndex], x, y, this);
            }
        }
    }

}
