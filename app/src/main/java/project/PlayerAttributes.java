package project;

import project.levels.Level;
import project.sprites.PlayerSpriteSheet.PantColor;

public class PlayerAttributes {
    public static final int MAX_LIVES = 6;
    public static final int NO_LEVELS_COMPLETE = 0;
    public static final int ALL_LEVELS_COMPLETE = Level.values().length;
    public static final PantColor DEFAULT_PANT_COLOR = PantColor.Blue;

    /**
     * The number of levels completed by the player.
     *
     * @see #NO_LEVELS_COMPLETE
     * @see #ALL_LEVELS_COMPLETE
     */
    public static int levelsCompleted = 0;
    public static int lives = MAX_LIVES;
    public static PantColor pantColor = DEFAULT_PANT_COLOR;

    private PlayerAttributes() {
        // you shouldn't need an instance of this class.
    }
}
