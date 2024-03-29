package project.user_interface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
public class UIConstants {
    // Color palette
    public static final Color PRIMARY_COLOR = new Color(247, 183, 7);
    public static final Color PRIMARY_VARIANT_COLOR = new Color(250, 211, 105);
    public static final Color SECONDARY_COLOR = new Color(95, 36, 219);
    // Typography standards
    public static final int FONT_SIZE_SMALL = 12;
    public static final int FONT_SIZE_MEDIUM = 16;
    public static final int FONT_SIZE_LARGE = 20;
    public static final Font FONT_SMALL = new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE_SMALL);
    public static final Font FONT_MEDIUM = new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE_MEDIUM);
    public static final Font FONT_LARGE = new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE_LARGE);
    public static final Font FONT_TITLE = new Font(Font.DIALOG, Font.BOLD, 100);

    // Other UI constants
    public static final int BORDER_RADIUS = 10;
    public static final Dimension BUTTON_RECTANGLE_SIZE = new Dimension(200, 40);
    public static final Dimension BUTTON_SQUARE = new Dimension(50, 50);

    // Prevent instantiation
    private UIConstants() {}
}
