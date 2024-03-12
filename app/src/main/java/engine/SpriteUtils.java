package engine;

import java.awt.Graphics2D;
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

        System.err.println("Loading " + sourceUrl);

        return ImageIO.read(sourceUrl);
    }

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

    public static BufferedImage scale(BufferedImage source, int width, int height) {
        return compose(source.getType(), width, height, (g) -> {
            g.drawImage(source, 0, 0, width, height, null);
        });
    }
}
