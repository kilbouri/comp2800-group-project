package project.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;

public class KeyboardExtraSheet extends SpriteSheet {

    public static enum Key {
        Tab(0),
        Escape(1),
        Print(2),
        Backspace(3),
        Shift(4),
        PageUp(5),
        PageDown(6),
        Enter(7),
        Ctrl(8),
        Alt(9),
        Space(10),
        Insert(11),
        Delete(12),
        End(13),
        Home(14),
        Pause(15);

        protected int index;

        Key(int index) {
            this.index = index;
        }
    }

    private static final int NUM_KEYS = Key.values().length;

    public KeyboardExtraSheet() throws IOException {
        super(SpriteUtils.load("sprites/prod/keys/extras.png"), 16, 16);
    }

    public BufferedImage getKey(Key key) {
        return this.getKey(key, false);
    }

    public BufferedImage getKey(Key key, boolean pressed) {
        int index = key.index;
        if (pressed) {
            // pressed variants are in the same order after the unpressed variants
            index += NUM_KEYS;
        }

        return getTile(index);
    }
}
