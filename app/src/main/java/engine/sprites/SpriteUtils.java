package engine.sprites;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

public class SpriteUtils {

    public static BufferedImage load(String resourceName) throws IOException {
        final URL sourceUrl = SpriteUtils.class
                .getClassLoader()
                .getResource(resourceName);
        return ImageIO.read(sourceUrl);
    }

    public static BufferedImage tile(BufferedImage source, int numTilesX, int numTilesY) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        int width = sourceWidth * numTilesX;
        int height = sourceHeight * numTilesY;

        return compose(source.getType(), width, height, (graphics) -> {
            for (int y = 0; y < height; y += sourceHeight) {
                for (int x = 0; x < width; x += sourceWidth) {
                    graphics.drawImage(source, x, y, null);
                }
            }
        });
    }

    /**
     * Resizes a given BufferedImage to the specified output width and height by
     * tiling the image. If the output dimensions are not evenly divisible by the
     * source image dimensions, the source image will be clipped to fit the output
     * dimensions.
     *
     * @param source       The source BufferedImage to be resized.
     * @param outputWidth  The desired width of the output image.
     * @param outputHeight The desired height of the output image.
     * @return the resized BufferedImage.
     */
    public static BufferedImage tileToSize(BufferedImage source, int outputWidth, int outputHeight) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        return compose(source.getType(), outputWidth, outputHeight, (graphics) -> {
            for (int y = 0; y < outputHeight; y += sourceHeight) {
                for (int x = 0; x < outputWidth; x += sourceWidth) {
                    int tileWidth = sourceWidth;
                    int tileHeight = sourceHeight;

                    // Adjust the width for the last column tiles
                    if (x + tileWidth > outputWidth) {
                        tileWidth = outputWidth - x;
                    }

                    // Adjust the height for the last row tiles
                    if (y + tileHeight > outputHeight) {
                        tileHeight = outputHeight - y;
                    }

                    // Draw the (possibly clipped) tile
                    graphics.drawImage(
                            source,
                            x, y,
                            x + tileWidth, y + tileHeight,
                            0, 0,
                            tileWidth, tileHeight,
                            null);
                }
            }
        });
    }

    /**
     * Scales a sprite to the given width and height. This method is equivalent to
     * drawing the sprite with `graphics.drawImage(source, 0, 0, width, height,
     * null)` into a new bitmap.
     *
     * @param source the source image
     * @param width  the desired width (may be less than the source size)
     * @param height the desired height (may be less than the source size)
     * @return source, scaled up or down to fill (not fit) width and height
     */
    public static BufferedImage scale(BufferedImage source, int width, int height) {
        return compose(source.getType(), width, height, (g) -> {
            g.drawImage(source, 0, 0, width, height, null);
        });
    }

    /**
     * Scales a 9-sliced sprite whose center is defined by centerArea. Sprites are
     * sliced as in the Unity engine. The border regions are tiled to fill their
     * single axis (eg, the top center region is tiled horizontally and the left
     * center region vertically). The center is tiled in both directions. The tiling
     * begins in the top-left-most area and fills towards the bottom-right (as
     * opposed to from the center).
     *
     * @see https://docs.unity3d.com/Manual/9SliceSprites.html
     *
     * @param source     the source image
     * @param centerArea the rectangle defining the center area in the sprite
     * @param width      the width of the scaled image
     * @param height     the height of the scaled image
     * @return the 9-sliced sprite, scaled to fill width and height
     */
    public static BufferedImage scaleSliced(BufferedImage source, Rectangle centerArea, int width, int height) {
        if (centerArea.width <= 0 || centerArea.height <= 0) {
            throw new IllegalArgumentException("centerArea must have non-zero dimensions");
        }

        BufferedImage topLeft, topCenter, topRight;
        BufferedImage left, center, right;
        BufferedImage bottomLeft, bottomCenter, bottomRight;

        final int leftColWidth = centerArea.x;
        final int centerColWidth = centerArea.width;
        final int rightColWidth = source.getWidth() - leftColWidth - centerArea.width;

        final int topRowHeight = centerArea.y;
        final int centerRowHeight = centerArea.height;
        final int bottomRowHeight = source.getHeight() - topRowHeight - centerArea.height;

        topLeft = source.getSubimage(0, 0, leftColWidth, topRowHeight);
        topCenter = source.getSubimage(leftColWidth, 0, centerColWidth, topRowHeight);
        topRight = source.getSubimage(leftColWidth + centerColWidth, 0, rightColWidth, topRowHeight);

        left = source.getSubimage(0, topRowHeight, leftColWidth, centerRowHeight);
        center = source.getSubimage(leftColWidth, topRowHeight, centerColWidth, centerRowHeight);
        right = source.getSubimage(leftColWidth + centerColWidth, topRowHeight, rightColWidth, centerRowHeight);

        bottomLeft = source.getSubimage(0, topRowHeight + centerRowHeight, leftColWidth, bottomRowHeight);
        bottomCenter = source.getSubimage(leftColWidth, topRowHeight + centerRowHeight, centerColWidth,
                bottomRowHeight);
        bottomRight = source.getSubimage(leftColWidth + centerColWidth, topRowHeight + centerRowHeight, rightColWidth,
                bottomRowHeight);

        final int scaledLeftStartX = 0;
        final int scaledLeftEndX = topLeft.getWidth();
        final int scaledCenterStartX = scaledLeftEndX;
        final int scaledCenterEndX = width - topRight.getWidth();
        final int scaledRightStartX = scaledCenterEndX;

        final int scaledTopStartY = 0;
        final int scaledTopEndY = topLeft.getHeight();
        final int scaledCenterStartY = scaledTopEndY;
        final int scaledCenterEndY = height - bottomLeft.getHeight();
        final int scaledBottomStartY = scaledCenterEndY;

        return compose(source.getType(), width, height, (graphics) -> {
            // Top row
            graphics.drawImage(topLeft, scaledLeftStartX, scaledTopStartY, null);

            for (int x = scaledCenterStartX; x < scaledCenterEndX; x += topCenter.getWidth()) {
                graphics.drawImage(topCenter, x, scaledTopStartY, null);
            }

            graphics.drawImage(topRight, scaledRightStartX, scaledTopStartY, null);

            // Middle row
            for (int y = scaledCenterStartY; y < scaledCenterEndY; y += left.getHeight()) {
                graphics.drawImage(left, scaledLeftStartX, y, null);

                for (int x = scaledCenterStartX; x < scaledCenterEndX; x += center.getWidth()) {
                    graphics.drawImage(center, x, y, null);
                }

                graphics.drawImage(right, scaledRightStartX, y, null);
            }

            // Bottom row
            graphics.drawImage(bottomLeft, scaledLeftStartX, scaledBottomStartY, null);

            for (int x = scaledCenterStartX; x < scaledCenterEndX; x += bottomCenter.getWidth()) {
                graphics.drawImage(bottomCenter, x, scaledBottomStartY, null);
            }

            graphics.drawImage(bottomRight, scaledRightStartX, scaledBottomStartY, null);
        });
    }

    public static BufferedImage compose(int width, int height, Consumer<Graphics2D> render) {
        return compose(BufferedImage.TYPE_4BYTE_ABGR, height, render);
    }

    public static BufferedImage compose(int imageType, int width, int height, Consumer<Graphics2D> render) {
        BufferedImage result = new BufferedImage(width, height, imageType);
        Graphics2D resultGraphics = result.createGraphics();

        try {
            render.accept(resultGraphics);
        } finally {
            resultGraphics.dispose();
        }

        return result;
    }
}
