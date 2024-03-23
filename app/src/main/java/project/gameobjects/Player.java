package project.gameobjects;

import engine.GameObject;
import engine.Keyboard;
import engine.Sprite;
import engine.collision.BoxCollider;
import engine.collision.CollisionEvent;
import engine.collision.PhysicsWorld;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static engine.collision.BoxCollider.OverlapFlags;

public class Player extends GameObject {

    private static final double JUMP_STRENGTH = 400;
    private BoxCollider collider;
    private Sprite sprite;

    protected double vSpeed = 0;
    protected double walkSpeed = 150;
    protected double sprintSpeed = 225;

    private boolean grounded = true;

    public Player(BufferedImage sprite) {
        this.sprite = new Sprite(sprite);
    }

    public Player(BufferedImage sprite, double x, double y) {
        this.addComponent(collider = new BoxCollider());
        this.addComponent(this.sprite = new Sprite(sprite));

        double w = sprite.getWidth();
        double h = sprite.getHeight();

        this.transform = new Rectangle2D.Double(x, y, w, h);
    }

    @Override
    public void update(double deltaTime) {
        if (grounded) {
            vSpeed = 0;
        } else {
            // Remember, acceleration is per second per second, so we actually DO want
            // this "double application" of deltaTime.
            vSpeed -= PhysicsWorld.GRAVITY * deltaTime;
        }

        if (grounded && Keyboard.held(KeyEvent.VK_SPACE)) {
            grounded = false;
            vSpeed = JUMP_STRENGTH;
        }

        int dx = Keyboard.getAxis(KeyEvent.VK_A, KeyEvent.VK_D);
        double speed = (Keyboard.held(KeyEvent.VK_SHIFT)) ? sprintSpeed : walkSpeed;

        transform.x += dx * speed * deltaTime;
        transform.y -= vSpeed * deltaTime;

        super.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g) {
        sprite.render(g);
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        BoxCollider otherCollider = event.getOtherCollider(this);
        Rectangle2D overlap = event.getOverlap();

        int overlapFlags = this.collider.overlapWith(otherCollider);

        collider.resolveCollisionWith(otherCollider);

        boolean isWallCollision = overlap.getHeight() >= overlap.getWidth();
        boolean topInsideOther = OverlapFlags.checkEdge(overlapFlags, OverlapFlags.TOP_EDGE);
        boolean bottomInsideOther = OverlapFlags.checkEdge(overlapFlags, OverlapFlags.BOTTOM_EDGE);

        if (topInsideOther && !isWallCollision) {
            vSpeed = 0;
        }

        if (bottomInsideOther && !isWallCollision) {
            grounded = true;
        }
    }

    @Override
    public void onCollisionExit(CollisionEvent event) {
        // This probably is going to cause bugs, we might need to do checks on this
        grounded = false;
    }
}
