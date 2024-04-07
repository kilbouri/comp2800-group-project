package project.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import project.PlayerAttributes;

public class Progress {

    public static void saveProgress() {
        try {

            ObjectMapper mapper = new ObjectMapper();
            PlayerProgress progress = new PlayerProgress(PlayerAttributes.pantColor, PlayerAttributes.levelsCompleted);
            if (!Files.exists(Paths.get("playerProgress.json"))) {
                Files.createFile(Paths.get("playerProgress.json"));
            }

            // Serialize to JSON
            String json = mapper.writeValueAsString(progress);
            System.out.println("Serialized JSON:\n" + json);

            // Optionally, write to file
            Files.write(Paths.get("playerProgress.json"), json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadProgress() {
        try {
            if (!Files.exists(Paths.get("playerProgress.json"))) {
                // if no file exists leave attributes as default and save default progress
                saveProgress();
                return;
            }
            ObjectMapper mapper = new ObjectMapper();

            // Read from file or string
            String json = new String(Files.readAllBytes(Paths.get("playerProgress.json")), StandardCharsets.UTF_8);

            // Deserialize from JSON
            PlayerProgress progress;
            try {
                progress = mapper.readValue(json, PlayerProgress.class);
            } catch (InvalidFormatException e) {
                // Handle invalid PantColor
                progress = new PlayerProgress(PlayerAttributes.DEFAULT_PANT_COLOR, 0); // Use default values
                saveProgress(); // Consider saving the corrected default progress
            }
            // Set the player attributes from loaded progress

            PlayerAttributes.pantColor = progress.getPantColor();
            if (progress.getLevelsCompleted() < 0 || progress.getLevelsCompleted() > PlayerAttributes.ALL_LEVELS_COMPLETE) {
                PlayerAttributes.levelsCompleted = 0;
            } else {
                PlayerAttributes.levelsCompleted = progress.getLevelsCompleted();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Consider setting default values in case of other errors
            PlayerAttributes.pantColor = PlayerAttributes.DEFAULT_PANT_COLOR;
            PlayerAttributes.levelsCompleted = 0;
            saveProgress(); // Optionally save the default progress to recover from the error
        }

    }
}
