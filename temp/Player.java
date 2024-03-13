package project;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;


import engine.BoxCollider;
import engine.Keyboard;
import engine.components.Sprite;

public class Player extends Sprite {

    private static final int MAX_MIDAIR_JUMPS = 1;
    private int midairJumps = 0;

    protected double vSpeed = 0;
    protected double hSpeed = 150;

    private boolean isGrounded = true;
    private boolean spaceReleasedSinceLastJump = true;

    BoxCollider boxCollider = new BoxCollider(0, 0, 0, 0);

    public Player(BufferedImage sprite) {
        super(sprite);
        boxCollider = new BoxCollider((float) position.x, (float) position.y, displayImage.getWidth(), displayImage.getHeight());

    }

    public Player(BufferedImage sprite, double x, double y) {
        super(new Point2D.Double(x, y), sprite);
        boxCollider = new BoxCollider((float) x, (float) y, displayImage.getWidth(), displayImage.getHeight());
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (isGrounded) {
            vSpeed = 0;
        } else {
            // fake increased gravity when falling so it looks right
            double gravityStrength = (vSpeed > 0) ? 0.8 : 1;
            vSpeed -= 98.1 * 10 * deltaTime * gravityStrength;
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
        // @formatter:on

        position.x += hSpeed * deltaTime * dx;
        position.y -= vSpeed * deltaTime;
        // Update the collider position to match the player's position
        boxCollider.setPosition(position.x, position.y);
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
        midairJumps = 0;
    }

    public BoxCollider getCollider() {
        return boxCollider;
    }
}
