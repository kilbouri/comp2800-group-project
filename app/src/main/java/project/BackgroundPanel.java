package project;

import engine.sprites.SpriteUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
    private BufferedImage background;

    public BackgroundPanel() {
        setLayout(new BorderLayout());
        loadBackgroundImage();
    }

    private void loadBackgroundImage() {
        try {
            background = SpriteUtils.load("sprites/prod/customizedbackground.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
