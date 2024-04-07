package project.gameobjects;

import engine.sprites.Animation;

public class AnimatedSprite extends StaticSprite {

    private Animation animation;

    public AnimatedSprite(Animation animation, int gridX, int gridY) {
        super(animation.getSprite(), gridX, gridY);
        this.animation = animation;
    }

    @Override
    public void update(double deltaTime) {
        animation.update(deltaTime);
        super.setSprite(animation.getSprite());
        super.update(deltaTime);
    }
}
