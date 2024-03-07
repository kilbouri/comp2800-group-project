package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SpriteUtils {

    public static BufferedImage tile(BufferedImage source, int numTilesX, int numTilesY) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        int width = sourceWidth * numTilesX;
        int height = sourceHeight * numTilesY;

        BufferedImage result = new BufferedImage(width, height, source.getType());
        Graphics2D resultGraphics = result.createGraphics();

        try {
            for (int y = 0; y < height; y += sourceHeight) {
                for (int x = 0; x < width; x += sourceWidth) {
                    resultGraphics.drawImage(source, x, y, null);
                }
            }
        } finally {
            resultGraphics.dispose();
        }

        return result;
    }
}
