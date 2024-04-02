package project.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;

public class KeyboardMainSheet extends SpriteSheet {
    public static enum Key {
        UpArrow(0),
        DownArrow(1),
        LeftArrow(2),
        RightArrow(3),
        F1(4),
        F2(5),
        F3(6),
        F4(7),
        F5(8),
        F6(9),
        F7(10),
        F8(11),
        F9(12),
        F10(13),
        F11(14),
        F12(15),
        A(16),
        B(17),
        C(18),
        D(19),
        E(20),
        F(21),
        G(22),
        H(23),
        I(24),
        J(25),
        K(26),
        L(27),
        M(28),
        N(29),
        O(30),
        P(31),
        Q(32),
        R(33),
        S(34),
        T(35),
        U(36),
        V(37),
        W(38),
        X(39),
        Y(40),
        Z(41),
        Period(42),
        Comma(43),
        QuestionMark(44),
        ForwardSlash(45),
        BackwardSlash(46),
        Semicolon(47),
        SingleQuote(48),
        OpenSquareBracket(49),
        CloseSquareBracket(50),
        PlusEqual(51),
        Dash(52),
        Tilde(53),
        Reserved1(54),
        Reserved2(55);

        protected int index;

        Key(int index) {
            this.index = index;
        }
    }

    private static final int NUM_KEYS = Key.values().length;

    public KeyboardMainSheet() throws IOException {
        super(SpriteUtils.load("sprites/prod/keys/main.png"), 32, 16);
    }

    public BufferedImage getKey(Key key) {
        return getKey(key, false);
    }

    public BufferedImage getKey(Key key, boolean pressed) {
        int index = key.index;
        if (pressed) {
            index += NUM_KEYS;
        }

        return getTile(index);
    }
}
