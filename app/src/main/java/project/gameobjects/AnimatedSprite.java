package project.gameobjects;

import engine.sprites.Animation;

public class AnimatedSprite extends StaticSprite {

    private Animation animation;

    public AnimatedSprite(Animation animation, double x, double y) {
        super(animation.getSprite(), x, y);
        this.animation = animation;
    }

    @Override
    public void update(double deltaTime) {
        animation.update(deltaTime);
        super.setSprite(animation.getSprite());
        super.update(deltaTime);
    }
}
