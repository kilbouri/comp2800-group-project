package engine;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Keyboard {
    // I think this is an OK assumption? Sorry to the people who have
    // lost one or more limbs that contain fingers, and those with
    // birth defects that give them more or less than 5 fingers on a
    // single hand.
    private static final int NUM_FINGERS_PER_HAND = 5;

    // most people have 2 hands, and its very difficult to press
    // more than 3 keys at once with every finger
    private static final HashSet<Integer> heldKeys = new HashSet<>(NUM_FINGERS_PER_HAND * 2 * 3);

    /**
     * Get a KeyAdapter which should be added to a (usually focused)
     * GUI element. Key presses will be tracked and key state can be queried
     * with other static methods of this class.
     *
     * @return a KeyAdapter to add to a GUI element
     *
     * @see Keyboard#held(int)
     */
    public static KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public synchronized void keyPressed(KeyEvent e) {
                heldKeys.add(e.getKeyCode());
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                heldKeys.remove(e.getKeyCode());
            }
        };
    }

    /**
     * Get a FocusAdapter which should be added to the same GUI element
     * as the key adapter from {@link Keyboard#getKeyAdapter()}. The
     * adapter clears the list of pressed keys when focus is lost in order
     * to prevent strange movement.
     *
     * @return a FocusAdapter to add to a GUI element
     */
    public static FocusAdapter getFocusAdapter() {
        return new FocusAdapter() {
            @Override
            public synchronized void focusLost(FocusEvent e) {
                heldKeys.clear();
            }
        };
    }

    /**
     * Determines whether or not the given key code, as outlined in
     * KeyEvent, is pressed or not.
     *
     * @param keyCode the key code, as specified in KeyEvent
     * @return true if the key is pressed, otherwise false
     *
     * @see KeyEvent
     */
    public synchronized static boolean held(int keyCode) {
        return heldKeys.contains(keyCode);
    }
}
