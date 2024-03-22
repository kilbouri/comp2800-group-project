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

public class Player extends GameObject {

    private Sprite sprite;

    protected double vSpeed = 0;
    protected double hSpeed = 150;

    private boolean grounded = true;

    public Player(BufferedImage sprite) {
        this.sprite = new Sprite(sprite);
    }

    public Player(BufferedImage sprite, double x, double y) {
        this.addComponent(new BoxCollider());
        this.addComponent(this.sprite = new Sprite(sprite));

        double w = sprite.getWidth();
        double h = sprite.getHeight();

        this.transform = new Rectangle2D.Double(x, y, w, h);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (grounded) {
            vSpeed = 0;
        } else {
            vSpeed -= PhysicsWorld.GRAVITY * deltaTime;
        }

        if (grounded && Keyboard.held(KeyEvent.VK_SPACE)) {
            grounded = false;
            vSpeed = 100;
        }

        getTransform().y -= vSpeed * deltaTime;
    }

    @Override
    public void render(Graphics2D g) {
        sprite.render(g);
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        BoxCollider otherCollider = event.getOtherCollider(this);

        if (otherCollider.getBox().getMinY() <= this.transform.getMaxY()) {
            grounded = true;
        }
    }
}
