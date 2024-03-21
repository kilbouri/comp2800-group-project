package project.gameObjects;

import engine.GameObject;
import engine.Keyboard;
import engine.Sprite;
import engine.collision.BoxCollider;
import engine.collision.CollisionEvent;
import engine.collision.CollisionLayer;
import engine.collision.CollisionType;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    private static final int MAX_MIDAIR_JUMPS = 1;
    private int midairJumps = 0;

    private CollisionType lastCollisionType = CollisionType.NONE;
    private boolean isLeftRightCollided = false;

    protected double vSpeed = 0;
    protected double hSpeed = 150;
    protected double hSprintingSpeed = 2;

    private boolean isGrounded = true;
    private boolean spaceReleasedSinceLastJump = true;

    protected BoxCollider boxCollider;
    protected Sprite sprite;

    public Player(BufferedImage sprite) {
        this.sprite = new Sprite(sprite);
    }

    public Player(BufferedImage sprite, double x, double y) {
        this.sprite = new Sprite(new Point2D.Double(x, y), sprite);
        boxCollider = new BoxCollider();
        boxCollider.setMoveable(true);
        boxCollider.setCollisionLayer(CollisionLayer.PLAYER);
        this.transform = new Rectangle2D.Double(x, y, this.sprite.getDisplayImage().getWidth(),
                this.sprite.getDisplayImage().getHeight());

        this.addComponent(boxCollider);
    }

    @Override
    public void update(double deltaTime) {
        if (isGrounded) {
            vSpeed = 0;
        } else {
            // fake increased gravity when falling so it looks right
            double gravityStrength = (vSpeed > 0) ? 1.5 : 1;
            vSpeed -= 98.1 * 10 * gravityStrength * deltaTime;
        }

        boolean canJump = isGrounded || midairJumps < MAX_MIDAIR_JUMPS;
        if (canJump && spaceReleasedSinceLastJump && Keyboard.held(KeyEvent.VK_SPACE)) {
            vSpeed = 500;
            if (!isGrounded) {
                midairJumps += 1;
            }

            isGrounded = false;
            spaceReleasedSinceLastJump = false;
        }

        if (!Keyboard.held(KeyEvent.VK_SPACE)) {
            spaceReleasedSinceLastJump = true;
        }

        // @formatter:off
        int dx = 0;
        if (Keyboard.held(KeyEvent.VK_A)) { dx -= 1; }
        if (Keyboard.held(KeyEvent.VK_D)) { dx += 1; }
        if (Keyboard.held(KeyEvent.VK_SHIFT)) { hSprintingSpeed = 2; }
        else { hSprintingSpeed = 1; }
        // @formatter:on

        this.transform.x += dx * hSpeed * hSprintingSpeed * deltaTime;
        this.transform.y -= vSpeed * deltaTime;

        sprite.setPosition(this.transform.x, this.transform.y);
    }

    @Override
    public void render(Graphics2D g) {
        sprite.render(g);
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
        if (isGrounded) {
            // Reset vertical movement to prevent falling
            vSpeed = 0;
        }
        midairJumps = lastCollisionType == CollisionType.TOP ? midairJumps : 0;
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        if (event.getOther() instanceof Block) {
            if (event.getCollisionType() != CollisionType.TOP) {
                setGrounded(true);
            }

            if (event.getCollisionType() == CollisionType.LEFT || event.getCollisionType() == CollisionType.RIGHT) {
            }
            lastCollisionType = event.getCollisionType();
        }
    }

    @Override
    public void onCollisionExit(CollisionEvent event) {
        if (event.getOther() instanceof Block) {
            if (event.getCollisionType() != CollisionType.BOTTOM) {

                setGrounded(false);
            }
        }
    }
}
