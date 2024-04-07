package project.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.sprites.SpriteSheet;
import engine.sprites.SpriteUtils;

public class DecorationSpriteSheet extends SpriteSheet {
    public static enum Decoration {
        Bush1(0, 2),
        Stump(2, 5),
        Bush2(5, 7),
        ArrowSign(7, 8),
        Crate(8, 9),
        PinkMushroom(9, 10),
        SmallBush1(10, 11),
        SmallBush2(11, 12),
        Boulder(12, 14),
        OrangeMushroom(14, 15);

        protected int start, end;
        protected BufferedImage image = null;

        Decoration(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private static DecorationSpriteSheet instance;

    public static DecorationSpriteSheet getInstance() {
        if (instance == null) {
            try {
                instance = new DecorationSpriteSheet();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static final int tileWidth = 32;
    private static final int tileHeight = 32;

    private DecorationSpriteSheet() throws IOException {
        super(SpriteUtils.load("sprites/prod/world/decor32.png"), tileWidth, tileHeight);
    }

    public BufferedImage getDecoration(Decoration decoration) {
        if (decoration.image == null) {
            int numTiles = decoration.end - decoration.start;

            decoration.image = SpriteUtils.compose(
                    getTile(0).getType(),
                    tileWidth * numTiles,
                    tileHeight,
                    (g) -> {
                        for (int i = 0; i < numTiles; i++) {
                            g.drawImage(getTile(decoration.start + i), i * tileWidth, 0, null);
                        }
                    });
        }

        return decoration.image;
    }
}
