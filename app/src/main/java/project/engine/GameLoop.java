package assignment2.engine;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

public abstract class GameLoop extends Canvas implements Runnable {

    private static final Font debugFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private static final double AVERAGE_FPS_SAMPLE_WINDOW = 100;
    private static final double SECONDS_TO_NANOS = 1e9;
    private static final double SECONDS_TO_MILLIS = 1e3;

    private BufferStrategy bufferStrategy;
    private Thread gameThread;
    private boolean aaEnabled;

    private double lastUpdateTime = 0;
    private double lastRenderTime = 0;

    private double fps = 0;
    private long frameNumber = 0;
    private long lastFpsUpdateFrameNumber = 0;
    private LinkedList<Double> lastFpsCounts = new LinkedList<Double>();

    protected GameLoop() {
        // Make sure this canvas can actually hecking have focus
        // (its definitely not 01:47 right now and I haven't been fighting
        // Swing for focus for the past hour... NOPE!)
        addKeyListener(Keyboard.getKeyAdapter());
        addFocusListener(Keyboard.getFocusAdapter());
        setFocusable(true);
    }

    /**
     * Begins executing the canvas. It is an exception to call this method twice,
     * or while the canvas is not displayable.
     */
    public void start() {
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();

        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    /**
     * The core loop of the game loop. Applications should NEVER call this
     * function, as it does not return!
     */
    @Override
    public void run() {
        final double secondsPerFpsUpdate = 0.125;
        final double nanosPerFpsUpdate = secondsPerFpsUpdate * SECONDS_TO_NANOS;

        double lastNanos = System.nanoTime();
        double fpsSamplingNanoTimer = 0.0;

        while (true) {
            double nowNanos = System.nanoTime();
            double deltaNanos = nowNanos - lastNanos;
            lastNanos = nowNanos;

            fpsSamplingNanoTimer += deltaNanos;

            // Is it time to update the FPS metric?
            if (fpsSamplingNanoTimer >= nanosPerFpsUpdate) {
                fpsSamplingNanoTimer = 0;

                long framesSinceLastFpsSample = frameNumber - lastFpsUpdateFrameNumber;
                lastFpsUpdateFrameNumber = frameNumber;

                fps = (double) framesSinceLastFpsSample / secondsPerFpsUpdate;

                if (lastFpsCounts.size() >= AVERAGE_FPS_SAMPLE_WINDOW) {
                    lastFpsCounts.removeFirst();
                }

                lastFpsCounts.add(fps);
            }

            doUpdate(deltaNanos / SECONDS_TO_NANOS);
            doRender();
        }
    }

    private void doUpdate(final double deltaTime) {
        double startNanos = System.nanoTime();

        // Run the concrete update implementation
        update(deltaTime);

        lastUpdateTime = (System.nanoTime() - startNanos) / SECONDS_TO_NANOS;
    }

    private void doRender() {
        double startNanos = System.nanoTime();

        frameNumber++;

        // Acquire graphics and clear the background
        Graphics2D drawGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();

        if (aaEnabled) {
            drawGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            drawGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }

        // Run the concrete render implementation, then show results
        try {
            render(drawGraphics);
        } finally {
            drawGraphics.dispose();
            bufferStrategy.show();
        }

        lastRenderTime = (System.nanoTime() - startNanos) / SECONDS_TO_NANOS;
    }

    /**
     * Draws some engine metrics on the screen in the top right corner.
     * 
     * @param graphics the graphics object to draw the metrics with.
     */
    protected void renderEngineMetrics(Graphics2D graphics) {
        double averageFps = lastFpsCounts.stream().reduce(0.0, Double::sum) / lastFpsCounts.size();

        final String[] debugLines = {
                String.format("Update time: %f ms", lastUpdateTime * SECONDS_TO_MILLIS),
                String.format("Render time: %f ms", lastRenderTime * SECONDS_TO_MILLIS),
                String.format("Framerate: %d fps (%d avg.)", (int) fps, (int) averageFps),
        };

        Font original = graphics.getFont();
        graphics.setFont(debugFont);

        int lineHeight = graphics.getFontMetrics().getHeight();
        for (int i = 0; i < debugLines.length; i++) {
            graphics.drawString(debugLines[i], lineHeight, (i + 1) * lineHeight);
        }

        graphics.setFont(original);
    }

    /**
     * Runs once per frame.
     * 
     * @param deltaTime the time that has elapsed since the last update.
     */
    public abstract void update(final double deltaTime);

    /**
     * Draw the game. Runs once and only once after each `update`.
     * 
     * @param graphics a graphics object to draw the game with
     */
    public abstract void render(Graphics2D graphics);

    public void setAntialiased(boolean aa) {
        this.aaEnabled = aa;
    }
}
