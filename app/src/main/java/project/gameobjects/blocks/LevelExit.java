package project.gameobjects.blocks;

import static project.levels.Level.GRID_SIZE;

import engine.physics.CollisionEvent;
import engine.physics.Trigger;
import engine.sprites.Sprite;
import engine.sprites.SpriteSheet;
import project.PlaceholderSpriteSheet;
import project.gameobjects.Player;
import project.levels.Level;

public class LevelExit extends Trigger {
    private static final SpriteSheet sourceSheet = PlaceholderSpriteSheet.getInstance();

    private Player player;
    private Level nextLevel;

    public LevelExit(Level nextLevel, Player player, int gridX, int gridY) {
        super(gridX * GRID_SIZE, gridY * GRID_SIZE, GRID_SIZE, 2 * GRID_SIZE);

        this.player = player;
        this.nextLevel = nextLevel;

        addComponent(new Sprite(sourceSheet.getTile(1)));
    }

    @Override
    public void onCollisionEnter(CollisionEvent event) {
        if (event.getOther(this) != player) {
            return;
        }

        System.err.println("Player " + player + " completed the level, transitioning to " + nextLevel);
        getGameLoop().loadLevel(nextLevel.getLoader());
    }
}
