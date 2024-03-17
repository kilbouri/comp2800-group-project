package engine;

import java.awt.image.BufferedImage;

public class Animation {
    private final SpriteSheet sourceSheet;
    private final int startIndex;
    private final int numTiles;
    private final double secondsPerFrame;

    private double frameTimer;
    private int currentTileOffset;

    /**
     * Creates an animation with the specified framerate using the sprites with
     * indices [startIndex, endIndex] in the given SpriteSheet. All SpriteSheets
     * provide a helper method to compute the index of the sprite in tile
     * (X, Y) in the sheet.
     *
     * @param sourceSheet the sprite sheet from which the animation should read
     * @param fps         the playback framerate of the animation
     * @param startIndex  the index of the first frame in the animation
     * @param endIndex    the index of the last frame in the animation (inclusive)
     *
     * @see SpriteSheet#getTileIndex(int, int)
     */
    public Animation(SpriteSheet sourceSheet, double fps, int startIndex, int endIndex) {
        this.sourceSheet = sourceSheet;
        this.startIndex = startIndex;
        this.numTiles = endIndex - startIndex + 1; // endIndex is inclusive

        this.secondsPerFrame = 1.0 / fps;
        this.frameTimer = secondsPerFrame;

        this.currentTileOffset = 0;
    }

    /**
     * Advances the Animation by the specified number of seconds.
     *
     * @param deltaTime the number of seconds
     */
    public void update(double deltaTime) {
        frameTimer -= deltaTime;

        while (frameTimer <= 0) {
            frameTimer += secondsPerFrame;
            nextFrame();
        }
    }

    /**
     * Retrieves the current sprite being shown in the animation
     *
     * @return the current animation frame
     */
    public BufferedImage getSprite() {
        return sourceSheet.getTile(startIndex + currentTileOffset);
    }

    /**
     * Restarts the animation from frame 0
     */
    public void reset() {
        currentTileOffset = 0;
    }

    private void nextFrame() {
        currentTileOffset += 1;
        currentTileOffset %= numTiles;
    }
}
