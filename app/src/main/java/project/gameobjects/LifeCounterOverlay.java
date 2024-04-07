package project.gameobjects;

import java.awt.image.BufferedImage;

import engine.sprites.SpriteUtils;
import project.PlayerAttributes;
import project.sprites.HeartSpriteSheet;
import project.sprites.HeartSpriteSheet.Heart;

public class LifeCounterOverlay extends StaticSprite {
    private static final HeartSpriteSheet hearts = HeartSpriteSheet.getInstance();

    public LifeCounterOverlay() {
        this(0.25, 0.25);
    }

    public LifeCounterOverlay(double gridX, double gridY) {
        super(generateOverlay(), gridX, gridY);
    }

    private static BufferedImage generateOverlay() {
        // (N + 1) / 2 computes ceil(0.5 * N) in integer operations :]
        final int numHearts = (PlayerAttributes.MAX_LIVES + 1) / 2;

        return SpriteUtils.compose(
                hearts.getTile(0).getType(),
                hearts.getTileWidth() * numHearts,
                hearts.getTileHeight(),
                (g) -> {
                    int livesToDisplay = PlayerAttributes.lives;
                    Heart toDraw;

                    for (int i = 0; i < numHearts; i++) {

                        if (livesToDisplay >= 2) {
                            toDraw = Heart.Full;
                            livesToDisplay -= 2;
                        } else if (livesToDisplay == 1) {
                            toDraw = Heart.Half;
                            livesToDisplay -= 1;
                        } else {
                            toDraw = Heart.Empty;
                        }

                        g.drawImage(hearts.getHeart(toDraw), i * hearts.getTileWidth(), 0, null);
                    }
                });
    }
}
