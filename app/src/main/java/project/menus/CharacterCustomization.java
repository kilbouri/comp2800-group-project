package project.menus;

import project.PlayerAttributes;
import project.ProjectWindow;

import javax.swing.*;

import engine.sprites.Animation;
import engine.sprites.SpriteUtils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

import project.sprites.PlayerSpriteSheet;
import project.sprites.PlayerSpriteSheet.PantColor;
import project.ui.FancyButton;
import project.ui.UIConstants;

public class CharacterCustomization extends JPanel {

    private BufferedImage backgroundImage;

    private PlayerPreview preview;

    public CharacterCustomization(ProjectWindow projectWindow) {
        super(new BorderLayout());
        setOpaque(false);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        FancyButton backButton = new FancyButton("<");
        backButton.setPreferredSize(UIConstants.BUTTON_SQUARE);
        backButton.setBackground(UIConstants.PRIMARY_COLOR);
        backButton.setHoverColor(UIConstants.PRIMARY_VARIANT_COLOR);
        backButton.setBorderRadius(10);
        backButton.setFont(UIConstants.FONT_MEDIUM);
        backButton.addActionListener(e -> {
            projectWindow.switchMenu(Menus.START);
        });
        backPanel.add(backButton);
        backPanel.setOpaque(false); // Make the panel transparent
        // Adding backPanel to the top
        add(backPanel, BorderLayout.NORTH);

        try {
            backgroundImage = SpriteUtils.load("sprites/prod/playerpreviewbg.png");
        } catch (IOException e) {
            System.err.println("Error loading CharacterCustomizer background image");
            e.printStackTrace();
        }

        try {
            add(preview = new PlayerPreview(PlayerAttributes.pantColor), BorderLayout.CENTER);
        } catch (IOException e) {
            System.err.println("Error creating PlayerPreview");
            e.printStackTrace();
        }

        try {
            add(new PantColorSelector(PlayerAttributes.pantColor, this), BorderLayout.SOUTH);
        } catch (IOException e) {
            System.err.println("Error creating PantColorSelector");
            e.printStackTrace();
        }

    }

    protected void updatePantsSelection(PantColor color) {
        preview.setColor(color);
        PlayerAttributes.pantColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        double scale = (double) getHeight() / backgroundImage.getHeight();

        int imgWidth = (int) Math.ceil(scale * backgroundImage.getWidth());
        int imgHeight = (int) Math.ceil(scale * backgroundImage.getHeight());

        g.drawImage(backgroundImage, 0, 0, imgWidth, imgHeight, null);
        super.paintComponent(g);
    }

    static class PantColorSelector extends JPanel {
        private static final PantColor[] pantColors = PantColor.values();
        private static final int PANT_COLOR_SAMPLE_X = 69;
        private static final int PANT_COLOR_SAMPLE_Y = 169;
        private static final int NUM_COLS = 10;

        public PantColorSelector(PantColor selected, CharacterCustomization rootMenu) throws IOException {
            super(new GridBagLayout());
            setOpaque(false);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.NONE;
            constraints.insets = new Insets(8, 8, 8, 8);

            for (int i = 0; i < pantColors.length; i++) {
                constraints.gridx = i % NUM_COLS;
                constraints.gridy = i / NUM_COLS;

                PlayerSpriteSheet playerSheet = new PlayerSpriteSheet(pantColors[i]);
                BufferedImage topLeftTile = playerSheet.getTile(0);
                Color color = new Color(topLeftTile.getRGB(PANT_COLOR_SAMPLE_X, PANT_COLOR_SAMPLE_Y));

                FancyButton button = new FancyButton("");
                button.setBorderRadius(8);
                button.setMinimumSize(new Dimension(64, 64));
                button.setPreferredSize(new Dimension(64, 64));
                button.setSize(new Dimension(64, 64));
                button.setBackground(color);
                button.setHoverColor(color.darker());

                // You'd think Java could copy primitives into lambdas... nope!
                final int[] intptr = { i };
                button.addActionListener((e) -> {
                    rootMenu.updatePantsSelection(pantColors[intptr[0]]);
                });

                add(button, constraints);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            final int radius = 16;
            final RoundRectangle2D.Double rect = new RoundRectangle2D.Double(
                    0, 0,
                    getWidth() + radius, getHeight() + radius,
                    radius, radius);
            super.paintComponent(g);

            Graphics2D graphics = (Graphics2D) g.create();
            try {
                graphics.setColor(new Color(0, 0, 0, 100));
                graphics.fill(rect);
            } finally {
                graphics.dispose();
            }
        }
    }

    static class PlayerPreview extends JPanel {
        private static final EnumMap<PantColor, Animation> IDLE_ANIMATIONS = new EnumMap<>(PantColor.class);
        private static final double NANOS_TO_SECONDS = 1.0 / 1e9;

        private long lastNanos;
        private Animation currentAnimation;

        public PlayerPreview(PantColor color) throws IOException {
            this.lastNanos = System.nanoTime();
            setOpaque(false);
            setColor(color);
        }

        public void setColor(PantColor newColor) {
            this.currentAnimation = getAnimationFor(newColor);
        }

        @Override
        protected void paintComponent(Graphics g) {
            final double deltaTime = getDeltaTime();

            currentAnimation.update(deltaTime);

            // How I got this particular number is MAGIC!
            // The magic:
            // - 416 is the pixel at which the ground starts in the background
            // - the other number is a magic number I found by trial and error
            // to account for the north section of the container
            final int playerFeetY = 416 - 60;
            final int playerFeetX = getWidth() / 2;

            final BufferedImage frame = currentAnimation.getSprite();
            final int imageX = playerFeetX - (frame.getWidth() / 2);
            final int imageY = playerFeetY - frame.getHeight();

            g.drawImage(currentAnimation.getSprite(), imageX, imageY, null);

            repaint(); // redraw as soon as possible (basically, use the event loop to create an
                       // infinite loop)
        }

        private static Animation getAnimationFor(PantColor pantColor) {
            if (!IDLE_ANIMATIONS.containsKey(pantColor)) {
                try {
                    IDLE_ANIMATIONS.put(pantColor, new Animation(new PlayerSpriteSheet(pantColor), 12, 0, 11));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return IDLE_ANIMATIONS.get(pantColor);
        }

        private double getDeltaTime() {
            long currentNanos = System.nanoTime();
            long nanosPassed = currentNanos - lastNanos;
            lastNanos = currentNanos;

            return NANOS_TO_SECONDS * nanosPassed;
        }
    }
}
