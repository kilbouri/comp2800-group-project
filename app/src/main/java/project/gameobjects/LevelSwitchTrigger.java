package project.gameobjects;

import engine.core.GameObject;
import engine.physics.CollisionEvent;
import engine.physics.Trigger;

public class LevelSwitchTrigger extends Trigger {

    private GameObject player;

    public LevelSwitchTrigger(GameObject player, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.player = player;
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        if (event.getOther(this) != player) {
            return;
        }
    }
}
