package project.gameobjects;

import engine.core.GameObject;
import engine.core.Keyboard;
import engine.core.MathExtensions;
import engine.physics.BoxCollider;
import engine.physics.CollisionEvent;
import engine.physics.PhysicsWorld;
import engine.physics.Trigger;
import engine.sprites.Sprite;

import static engine.physics.BoxCollider.OverlapFlags;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private static final double JUMP_HEIGHT_M = 1.7;
    private static final double JUMP_HEIGHT_PIXELS = JUMP_HEIGHT_M * PhysicsWorld.PIXELS_PER_METER;
    private static final double JUMP_STRENGTH = Math.sqrt(2 * PhysicsWorld.GRAVITY * JUMP_HEIGHT_PIXELS);

    private static final double MOVE_SPEED = 200;
    private static final double ACCEL_RATE = 50;

    private BoxCollider collider;
    private Sprite sprite;

    protected double vSpeed = 0;
    protected double hSpeed = 0;

    private boolean grounded = true;

    private GameObject ground = null;
    private Point2D.Double groundLastPos = null;

    public Player(BufferedImage sprite) {
        this(sprite, 0, 0);
    }

    public Player(BufferedImage sprite, double x, double y) {
        this.addComponent(collider = new BoxCollider());
        this.addComponent(this.sprite = new Sprite(sprite));

        double w = sprite.getWidth();
        double h = sprite.getHeight();

        this.transform = new Rectangle2D.Double(x, y, w, h);
        this.setLayer(10);
    }

    @Override
    public void update(double deltaTime) {
        // Remember, acceleration is per second per second, so we actually DO want
        // this "double application" of deltaTime.
        // ! Do not disable gravity when grounded. Otherwise the Player will stop
        // ! colliding with the ground, and we will do a TON more work for no reason!
        vSpeed -= PhysicsWorld.GRAVITY * deltaTime;

        if (grounded && Keyboard.held(KeyEvent.VK_SPACE)) {
            grounded = false;
            vSpeed = JUMP_STRENGTH;
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

        super.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g) {
        sprite.render(g);
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
        if (event.getOther(this) instanceof Trigger) {
            // ignore triggers
            return;
        }

        // This probably is going to cause bugs, we might need to do checks on this
        grounded = false;
        setGround(null);
    }

    private void handleGroundCollision(CollisionEvent event) {
        BoxCollider otherCollider = event.getOtherCollider(this);
        Rectangle2D overlap = event.getOverlap();

        int overlapFlags = this.collider.overlapWith(otherCollider);

        collider.resolveCollisionWith(otherCollider);

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
}
