package engine.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.lang.Thread.State;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JOptionPane;

import engine.physics.PhysicsWorld;

public abstract class GameLoop extends Canvas implements Runnable {

    private static final Font DEBUG_FONT = new Font(Font.MONOSPACED, Font.BOLD, 14);
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

    private Vector<GameObject> gameObjects;
    private PhysicsWorld physicsWorld;
    private LevelLoader currentLevelLoader;

    private AtomicBoolean terminated = new AtomicBoolean(false);
    private AtomicBoolean suspended = new AtomicBoolean(false);

    protected GameLoop(int gameObjectCapacity) {
        // Make sure this canvas can actually hecking have focus
        // (its definitely not 01:47 right now and I haven't been fighting
        // Swing for focus for the past hour... NOPE!)
        addKeyListener(Keyboard.getKeyAdapter());
        addFocusListener(Keyboard.getFocusAdapter());
        setFocusable(true);

        gameObjects = new Vector<>(gameObjectCapacity);
        physicsWorld = new PhysicsWorld();
    }

    /**
     * Begins executing the canvas. It is an exception to call this method while the
     * canvas is not displayable.
     *
     * If this method is called more than once, then a new thread will
     * only be started if the previous game thread has COMPLETELY stopped.
     *
     * @return true if the a new game thread was started, otherwise false
     */
    public boolean start() {
        if (gameThread != null && gameThread.getState() != State.TERMINATED) {
            return false;
        }

        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();

        gameThread = new Thread(this, "Game Thread");
        terminated.set(false);

        gameThread.start();
        return true;
    }

    public void suspend() {
        suspended.set(true);
    }

    public void resume() {
        suspended.set(false);

        if (gameThread != null) {
            gameThread.interrupt(); // have the thread wake up asap
        }
    }

    /**
     * Signals to the game thread that it should exit *as soon as possible*.
     *
     * @param block if true, this method blocks until the thread exits. The caller
     *              must take care to not cause deadlock.
     */
    public void stop(boolean block) {
        terminated.set(true);
        resume();

        // Wait for thread to exit
        while (block && gameThread != null) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
            }
        }
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

        while (!terminated.get()) {
            while (suspended.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // carry on, this is deliberate!
                }

                lastNanos = System.nanoTime();
            }

            // Did we break out of suspension due to termination?
            if (terminated.get()) {
                break;
            }

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

    /**
     * Add a GameObject to the loop. All GameObjects will be automatically
     * updated and rendered each frame.
     *
     * @param <T>    the type of GameObject being added
     * @param object the object to add
     * @return the same object passed in, for chaining
     */
    public <T extends GameObject> T addGameObject(T object) {
        object.setGameLoop(this);
        gameObjects.add(object);
        return object;
    }

    /**
     * Remove the specified object from the loop. The object will no longer receive
     * updates or be rendered. Note that, in general, the application should release
     * any further references to the removed object to allow it to be garbage
     * collected.
     *
     * @param object the object to remove
     */
    public void removeGameObject(GameObject object) {
        object.setGameLoop(null);
        gameObjects.remove(object);
    }

    public void loadLevel(LevelLoader loader) {
        this.currentLevelLoader = loader;

        // Reset input so the player has to re-press any held keys
        Keyboard.clear();

        gameObjects.clear();
        physicsWorld = new PhysicsWorld();

        try {
            loader.load(this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "The engine encountered an unhandled exception while loading a level:\n\n" + e.toString(),
                    "Level Loading Error", JOptionPane.ERROR_MESSAGE);
            terminated.set(true);
            return;
        }
    }

    public LevelLoader getCurrentLevelLoader() {
        return currentLevelLoader;
    }

    private void doUpdate(final double deltaTime) {
        double startNanos = System.nanoTime();

        // We create an array as we need to sort on the game object's layer. This
        // also protects us from the application's possibility of destroying a
        // game object within the update.
        GameObject[] currentObjects = gameObjects.toArray(new GameObject[gameObjects.size()]);
        Arrays.sort(currentObjects);

        // Run the concrete update implementation first, then objects
        update(deltaTime);

        for (int i = 0; i < currentObjects.length; i++) {
            currentObjects[i].update(deltaTime);
        }

        physicsWorld.update(currentObjects);

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
            beforeRender(drawGraphics);

            // We create an array as we need to sort on the game object's layer. This
            // also protects us from the application's possibility of destroying a
            // game object within the render (please, don't do that!).
            GameObject[] currentObjects = gameObjects.toArray(new GameObject[gameObjects.size()]);
            Arrays.sort(currentObjects);

            for (int i = 0; i < currentObjects.length; i++) {
                currentObjects[i].render(drawGraphics);
            }

            afterRender(drawGraphics);
        } finally {
            drawGraphics.dispose();
            bufferStrategy.show();
        }

        lastRenderTime = (System.nanoTime() - startNanos) / SECONDS_TO_NANOS;
    }

    /**
     * Draws some engine metrics on the screen in the top right corner.
     *
     * @param graphics
     *                 the graphics object to draw the metrics with.
     */
    protected void renderEngineMetrics(Graphics2D graphics) {
        double averageFps = lastFpsCounts.stream().reduce(0.0, Double::sum) / lastFpsCounts.size();

        final String[] debugLines = {
                String.format("Update time: %f ms", lastUpdateTime * SECONDS_TO_MILLIS),
                String.format("Render time: %f ms", lastRenderTime * SECONDS_TO_MILLIS),
                String.format("Framerate: %d fps (%d avg.)", (int) fps, (int) averageFps),
        };

        Font original = graphics.getFont();
        graphics.setFont(DEBUG_FONT);

        int lineHeight = graphics.getFontMetrics().getHeight();
        for (int i = 0; i < debugLines.length; i++) {
            graphics.drawString(debugLines[i], lineHeight, (i + 1) * lineHeight);
        }

        graphics.setFont(original);
    }

    protected void renderWASD(Graphics2D graphics) {
        char[][] keyDisplayActive = {
                new char[] { ' ', ' ', ' ', ' ', ' ' },
                new char[] { ' ', ' ', ' ', ' ', ' ' },
                new char[] { ' ', ' ', ' ', ' ', ' ' }
        };

        char[][] keyDisplayInactive = {
                new char[] { ' ', ' ', ' ', ' ', ' ' },
                new char[] { ' ', ' ', ' ', ' ', ' ' },
                new char[] { ' ', ' ', ' ', ' ', ' ' }
        };

        if (Keyboard.held(KeyEvent.VK_W)) {
            keyDisplayActive[0][2] = 'W';
        } else {
            keyDisplayInactive[0][2] = 'W';
        }

        if (Keyboard.held(KeyEvent.VK_S)) {
            keyDisplayActive[1][2] = 'S';
        } else {
            keyDisplayInactive[1][2] = 'S';
        }

        if (Keyboard.held(KeyEvent.VK_A)) {
            keyDisplayActive[1][0] = 'A';
        } else {
            keyDisplayInactive[1][0] = 'A';
        }

        if (Keyboard.held(KeyEvent.VK_D)) {
            keyDisplayActive[1][4] = 'D';
        } else {
            keyDisplayInactive[1][4] = 'D';
        }

        if (Keyboard.held(KeyEvent.VK_SPACE)) {
            keyDisplayActive[2] = "SPACE".toCharArray();
        } else {
            keyDisplayInactive[2] = "SPACE".toCharArray();
        }

        Font originalFont = graphics.getFont();
        graphics.setFont(DEBUG_FONT);

        Color originalColor = graphics.getColor();
        graphics.setColor(Color.GRAY);

        int lineHeight = graphics.getFontMetrics().getHeight();
        for (int i = 0; i < keyDisplayInactive.length; i++) {
            graphics.drawString(String.valueOf(keyDisplayInactive[i]), lineHeight, (i + 4) * lineHeight);
        }

        graphics.setColor(originalColor);

        for (int i = 0; i < keyDisplayActive.length; i++) {
            graphics.drawString(String.valueOf(keyDisplayActive[i]), lineHeight, (i + 4) * lineHeight);
        }

        graphics.setFont(originalFont);
    }

    /**
     * Runs once per frame. Game objects are updated from the lowest (back-most)
     * layer to the highest (front-most) layer. No intra-layer order is guaranteed.
     *
     * @param deltaTime the time that has elapsed since the last update.
     */
    public void update(final double deltaTime) {
    }

    /**
     * Runs after after each `update`, but before the game objects
     * are rendered.
     *
     * Game objects are then rendered from the lowest (back-most) layer
     * to the highest (front-most layer). No intra-layer order is
     * guaranteed.
     *
     * @param graphics a graphics object to draw the game with
     */
    public void beforeRender(Graphics2D graphics) {
    }

    /**
     * Runs after `update`, `beforeRender`, and all game objects
     * have been rendered.
     *
     * @param graphics
     */
    public void afterRender(Graphics2D graphics) {
    }

    public void setAntialiased(boolean aa) {
        this.aaEnabled = aa;
    }
}
