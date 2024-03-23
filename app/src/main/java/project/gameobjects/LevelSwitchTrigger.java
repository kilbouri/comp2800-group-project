package project.gameobjects;

import engine.core.GameObject;
import engine.physics.CollisionEvent;
import engine.physics.Trigger;
import project.levels.Level;

public class LevelSwitchTrigger extends Trigger {

    private GameObject player;
    private Level targetLevel;

    public LevelSwitchTrigger(GameObject player, Level switchTo, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.player = player;
        this.targetLevel = switchTo;
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        if (event.getOther(this) != player) {
            return;
        }

        player.getGameLoop().loadLevel(targetLevel.getLoader());
    }
}
