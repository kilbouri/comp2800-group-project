package project.sprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import engine.core.GameObject;
import engine.sprites.SpriteUtils;

public class BackgroundImage extends GameObject {
    private BufferedImage image;
    private BufferedImage scaledImage;

    private double offset = 0.0;

    private int lastFrameHeight = 0;

    public BackgroundImage(String resourceId) {
        try {
            image = SpriteUtils.load(resourceId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // By default, use a background layer
        scaledImage = image;
        setLayer(-1);
    }

    @Override
    public void update(double deltaTime) {
    }

    public void move(double amount) {
        offset -= amount;

        // We want offset such that -scaledImageWidth < offset <= 0, since
        // this range of values ensures that we do not attempt to draw the
        // image more times than necessary. We can get very close with this:
        offset = ((offset + scaledImage.getWidth()) % scaledImage.getWidth()) - scaledImage.getWidth();
    }

    @Override
    public void render(Graphics2D graphics) {
        if (getGameLoop().getHeight() != lastFrameHeight) {
            scaleImage();
        }

        lastFrameHeight = getGameLoop().getHeight();

        // Instance the image sufficient times to cover [0, width]
        for (double x = offset; x <= getGameLoop().getWidth(); x += scaledImage.getWidth()) {
            graphics.drawImage(scaledImage, (int) x, 0, null);
        }
    }

    private void scaleImage() {
        double rescaling = 1.0 * getGameLoop().getHeight() / image.getHeight();
        int scaledImageWidth = (int) (image.getWidth() * rescaling);
        int scaledImageHeight = (int) (image.getHeight() * rescaling);

        scaledImage = SpriteUtils.scale(image, scaledImageWidth, scaledImageHeight);
    }
}
