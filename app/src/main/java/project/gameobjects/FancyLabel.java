package project.gameobjects;
import javax.swing.*;
import java.awt.*;

public class FancyLabel extends JLabel {
    private Color strokeColor = Color.BLACK; // Default stroke color
    private float strokeWidth = 2f; // Default stroke width

    public FancyLabel(String text) {
        super(text);
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        repaint(); // Repaint the label to reflect color changes
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        repaint(); // Repaint the label to reflect stroke width changes
    }

    @Override
    protected void paintComponent(Graphics g) {
        String text = getText();
        if (text == null) {
            return; // Do nothing if text is null
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setFont(getFont());

        // Calculate the label's text position to center it
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

        // Draw the outline (stroke) around the text
        g2.setColor(strokeColor);
        g2.setStroke(new BasicStroke(strokeWidth));
        Shape textShape = g2.getFont().createGlyphVector(g2.getFontRenderContext(), text).getOutline(x, y);
        g2.draw(textShape);

        // Fill the text
        g2.setColor(getForeground());
        g2.fill(textShape);

        g2.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FancyLabel Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            FancyLabel fancyLabel = new FancyLabel("Fancy Label");
            fancyLabel.setFont(new Font("Serif", Font.BOLD, 24));
            fancyLabel.setForeground(Color.CYAN);
            fancyLabel.setStrokeColor(Color.MAGENTA);
            fancyLabel.setStrokeWidth(1.5f);

            frame.add(fancyLabel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
