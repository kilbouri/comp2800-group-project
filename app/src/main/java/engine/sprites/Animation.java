package engine.sprites;

import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class Animation {
    private BufferedImage[] frames;
    private final double secondsPerFrame;

    private double frameTimer;
    private int frameIndex;

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
        this(fps, ((Supplier<BufferedImage[]>) () -> {
            BufferedImage[] frames = new BufferedImage[endIndex - startIndex + 1];
            for (int i = 0; i <= frames.length; i++) {
                frames[i] = sourceSheet.getTile(startIndex + i);
            }
            return frames;
        }).get());
    }

    public Animation(double fps, BufferedImage... frames) {
        this.frames = frames;
        this.frameIndex = 0;

        this.secondsPerFrame = 1.0 / fps;
        this.frameTimer = secondsPerFrame;
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
        return frames[frameIndex];
    }

    /**
     * Restarts the animation from frame 0
     */
    public void reset() {
        frameIndex = 0;
    }

    private void nextFrame() {
        frameIndex += 1;
        frameIndex %= frames.length;
    }
}
