package project.gameobjects;

import engine.core.GameObject;
import engine.core.Keyboard;
import engine.core.MathExtensions;
import engine.physics.BoxCollider;
import engine.physics.CollisionEvent;
import engine.physics.PhysicsWorld;
import engine.physics.Trigger;
import engine.sprites.Animation;
import engine.sprites.Sprite;
import engine.sprites.SpriteSheet;
import project.levels.Level;
import project.sprites.PlayerSpriteSheet;
import project.sprites.PlayerSpriteSheet.PantColor;

import static engine.physics.BoxCollider.OverlapFlags;
import static project.levels.Level.GRID_SIZE;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Player extends GameObject {
    private static final double JUMP_HEIGHT_M = 1.7;
    private static final double JUMP_HEIGHT_PIXELS = JUMP_HEIGHT_M * PhysicsWorld.PIXELS_PER_METER;
    private static final double JUMP_STRENGTH = Math.sqrt(2 * PhysicsWorld.GRAVITY * JUMP_HEIGHT_PIXELS);

    private static final double MOVE_SPEED = 200;
    private static final double ACCEL_RATE = 50;

    protected double vSpeed = 0;
    protected double hSpeed = 0;

    private final SpriteSheet sourceSheet;
    private final Animation idle;
    private final Animation moveRight;
    private final Animation jumpStart;
    private final Animation jumpIdle;
    private final Animation falling;
    private Animation currentAnimation;

    private Sprite spriteComponent;
    private BoxCollider colliderComponent;

    private GameObject ground = null;
    private Point2D.Double groundLastPos = null;

    private boolean grounded = false;

    // The player sprites are much larger than the visible player
    // itself, so we use this offset to shrink the collider appropriately.
    // These values are relative to the original image
    private static final double X_OFFSET = 32.0;
    private static final double Y_OFFSET = 64.0;

    public Player(PantColor pants, int gridX, int gridY) throws IOException {
        this(pants, (double) (GRID_SIZE * gridX), GRID_SIZE * gridY);
    }

    private Player(PantColor pants, double x, double y) throws IOException {
        // Load spritesheet and animations
        final double BASE_FPS = 12;
        sourceSheet = new PlayerSpriteSheet(pants);
        idle = new Animation(sourceSheet, BASE_FPS, 0, 11);
        moveRight = new Animation(sourceSheet, BASE_FPS, 12, 24);
        jumpStart = new Animation(sourceSheet, BASE_FPS, 25, 27);
        jumpIdle = new Animation(sourceSheet, BASE_FPS, 28, 33);
        falling = new Animation(sourceSheet, BASE_FPS * 0.75, 34, 35);
        currentAnimation = idle;

        jumpStart.setLooping(false);

        // this scale factor reduces the player down to be exactly 2 tiles tall
        final double scale = (2.0 * Level.GRID_SIZE) / sourceSheet.getTileHeight();

        this.transform.x = x;
        this.transform.y = y;
        this.transform.width = sourceSheet.getTileWidth() * scale;
        this.transform.height = sourceSheet.getTileHeight() * scale;

        this.addComponent(colliderComponent = new BoxCollider(
                X_OFFSET * scale,
                Y_OFFSET * scale,
                this.transform.width - (X_OFFSET * scale * 2),
                this.transform.height - (Y_OFFSET * scale)));

        this.addComponent(spriteComponent = new Sprite(currentAnimation.getSprite()));
        spriteComponent.setScale(scale);

        this.setLayer(10);

        System.err.printf("%f, %f\n", x, y);
        System.err.printf("%f, %f\n", x, y);
    }

    @Override
    public void update(double deltaTime) {
        // Remember, acceleration is per second per second, so we actually DO want
        // this "double application" of deltaTime.
        // ! Do not disable gravity when grounded. Otherwise the Player will stop
        // ! colliding with the ground, and we will do a TON more work for no reason!
        vSpeed -= PhysicsWorld.GRAVITY * deltaTime;

        // Whether or not the player jumped on THIS FRAME
        boolean jumped = false;

        if (grounded && Keyboard.held(KeyEvent.VK_SPACE)) {
            grounded = false;
            vSpeed = JUMP_STRENGTH;
            jumped = true;
        }

        int dx = Keyboard.getAxis(KeyEvent.VK_A, KeyEvent.VK_D);
        hSpeed = MathExtensions.lerp(hSpeed, MOVE_SPEED * dx, deltaTime * ACCEL_RATE);

        // Inherit parent speed
        if (ground != null) {
            Rectangle2D.Double parentTrans = ground.getTransform();

            transform.x += (parentTrans.x - groundLastPos.x);
            transform.y += (parentTrans.y - groundLastPos.y);

            groundLastPos.x = parentTrans.x;
            groundLastPos.y = parentTrans.y;
        }

        transform.x += hSpeed * deltaTime;
        transform.y -= vSpeed * deltaTime;

        constrainToScreen();

        if (dx > 0) {
            spriteComponent.setIsFlippedX(false);
        } else if (dx < 0) {
            spriteComponent.setIsFlippedX(true);
        }

        if (grounded) {
            if (dx == 0.0) {
                setAnimation(idle);
            } else if (dx > 0) {
                setAnimation(moveRight);
            } else if (dx < 0) {
                setAnimation(moveRight);
            }
        } else {
            if (vSpeed < 0) {
                setAnimation(falling);
            } else if (jumped) {
                setAnimation(jumpStart);
            }
        }

        currentAnimation.update(deltaTime);

        if (currentAnimation == jumpStart && jumpStart.ended()) {
            setAnimation(jumpIdle);
        }

        super.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g) {
        spriteComponent.setDisplayImage(currentAnimation.getSprite());
        spriteComponent.render(g);
        colliderComponent.drawDebug(g);
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        if (event.getOther(this) instanceof Trigger) {
            // ignore triggers
            return;
        }

        handleGroundCollision(event);
    }

    @Override
    public void onCollisionStay(CollisionEvent event) {
        if (event.getOther(this) instanceof Trigger) {
            // ignore triggers
            return;
        }

        handleGroundCollision(event);
    }

    @Override
    public void onCollisionExit(CollisionEvent event) {
        GameObject other = event.getOther(this);

        if (other instanceof Trigger) {
            // ignore triggers
            return;
        }

        // This probably is going to cause bugs, we might need to do checks on this
        if (other == ground) {
            grounded = false;
            setGround(null);
        }
    }

    private void handleGroundCollision(CollisionEvent event) {
        BoxCollider otherCollider = event.getOtherCollider(this);
        Rectangle2D overlap = event.getOverlap();

        int overlapFlags = this.colliderComponent.overlapWith(otherCollider);

        colliderComponent.resolveCollisionWith(otherCollider);

        boolean isWallCollision = overlap.getHeight() >= overlap.getWidth();
        boolean topInsideOther = OverlapFlags.checkEdge(overlapFlags, OverlapFlags.TOP_EDGE);
        boolean bottomInsideOther = OverlapFlags.checkEdge(overlapFlags, OverlapFlags.BOTTOM_EDGE);

        boolean isMovingUpwards = vSpeed > 0;

        if (topInsideOther && !isWallCollision && isMovingUpwards) {
            vSpeed = 0;
        }

        // if the player is moving upwards and clips a corner, we don't want to kill
        // their upward velocity (it looks and feels weird)
        if (bottomInsideOther && !isWallCollision && !isMovingUpwards) {
            grounded = true;
            vSpeed = 0;

            // landed on ground, need to parent up
            setGround(otherCollider.getParentObject());
        }
    }

    private void setGround(GameObject newGround) {
        if (newGround == null) {
            this.ground = null;
            this.groundLastPos = null;
            return;
        }

        if (ground == newGround) {
            return;
        }

        Rectangle2D.Double parentTrans = newGround.getTransform();
        this.ground = newGround;
        this.groundLastPos = new Point2D.Double(parentTrans.x, parentTrans.y);
    }

    private void setAnimation(Animation anim) {
        if (currentAnimation == anim) {
            // no change required
            return;
        }

        anim.reset();
        currentAnimation = anim;
    }

    private void constrainToScreen() {
        Rectangle2D box = colliderComponent.getBox();
        if (box.getMinX() < 0) {
            transform.x -= box.getMinX();
        } else if (box.getMaxX() > Level.SCREEN_WIDTH_PX) {
            transform.x -= box.getMaxX() - Level.SCREEN_WIDTH_PX;
        }
    }
}
