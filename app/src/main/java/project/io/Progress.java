package project.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import project.PlayerAttributes;
import project.sprites.PlayerSpriteSheet.PantColor;

public class Progress {

    @JsonProperty("pantColor")
    private transient PantColor tempPantColor; // transient keyword is not necessary here, but it's used to indicate
                                               // these fields are not part of the object's persistent state

    @JsonProperty("levelsCompleted")
    private transient int tempLevelsCompleted;

    public static void saveProgress() {
        try {

            Progress progress = new Progress();
            // Transfer data from PlayerAttributes to temporary fields
            progress.tempPantColor = PlayerAttributes.pantColor;
            progress.tempLevelsCompleted = PlayerAttributes.levelsCompleted;
            ObjectMapper mapper = new ObjectMapper();

            // Serialize to JSON
            String json = mapper.writeValueAsString(progress);

            // Optionally, write to file
            Files.write(Paths.get("playerProgress.json"), json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadProgress() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Read JSON from file
            String json = new String(Files.readAllBytes(Paths.get("playerProgress.json")), StandardCharsets.UTF_8);

            // Deserialize JSON into temporary fields
            Progress progress = mapper.readValue(json, Progress.class);

            // Transfer data from temporary fields to PlayerAttributes
            PlayerAttributes.pantColor = progress.tempPantColor;
            PlayerAttributes.levelsCompleted = progress.tempLevelsCompleted;

            // Validation for levelsCompleted
            if (progress.tempLevelsCompleted < PlayerAttributes.NO_LEVELS_COMPLETE
                    || progress.tempLevelsCompleted > PlayerAttributes.ALL_LEVELS_COMPLETE) {
                PlayerAttributes.levelsCompleted = PlayerAttributes.NO_LEVELS_COMPLETE;
            }
        } catch (InvalidFormatException e) {
            // Handle invalid PantColor by resetting to default
            PlayerAttributes.pantColor = PlayerAttributes.DEFAULT_PANT_COLOR;
            PlayerAttributes.levelsCompleted = PlayerAttributes.NO_LEVELS_COMPLETE;
            saveProgress(); // Save the corrected default progress
        } catch (Exception e) {
            e.printStackTrace();
            // Reset to default values in case of other errors
            PlayerAttributes.pantColor = PlayerAttributes.DEFAULT_PANT_COLOR;
            PlayerAttributes.levelsCompleted = PlayerAttributes.NO_LEVELS_COMPLETE;
            saveProgress();
        }
    }
}
