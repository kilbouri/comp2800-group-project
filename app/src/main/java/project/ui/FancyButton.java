package project.ui;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FancyButton extends JButton {
    private Color hoverColor = new Color(176, 207, 203);
    private boolean isHovering = false;
    private int borderRadius = 0;

    public FancyButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isEnabled())
                    return; // Don't change color if button is disabled (greyed out)
                isHovering = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isEnabled())
                    return; // Don't change color if button is disabled (greyed out)
                isHovering = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), borderRadius,
                borderRadius);

        if (getModel().isPressed()) {
            g2.setColor(hoverColor.darker());
        } else if (isHovering) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(this.getBackground());
        }

        g2.fill(roundedRectangle);

        // Set the button's text and other components to be painted inside the rounded
        // rectangle
        super.paintComponent(g);
        g2.dispose();
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        repaint();
    }
}
