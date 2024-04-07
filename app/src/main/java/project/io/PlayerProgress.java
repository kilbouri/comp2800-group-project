package project.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import project.sprites.PlayerSpriteSheet.PantColor;

// PlayerProgress class to store player progress
// used solely for serialization and deserialization because static fields cannot be serialized by Jackson
public class PlayerProgress {
    @JsonProperty("pantColor")
    private PantColor pantColor;

    @JsonProperty("levelsCompleted")
    private int levelsCompleted;

    // Default constructor is required for Jackson deserialization
    public PlayerProgress() {}

    public PlayerProgress(PantColor pantColor, int levelsCompleted) {
        this.pantColor = pantColor;
        this.levelsCompleted = levelsCompleted;
    }

    // Getters and setters
    public PantColor getPantColor() {
        return pantColor;
    }

    public void setPantColor(PantColor pantColor) {
        this.pantColor = pantColor;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    public void setLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }
}
