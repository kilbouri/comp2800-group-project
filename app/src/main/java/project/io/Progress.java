package project.io;

import com.fasterxml.jackson.databind.ObjectMapper;
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
                //if no file exists leave attributes as default and save default progress
                saveProgress();
                return;
            }
            ObjectMapper mapper = new ObjectMapper();

            // Read from file or string
            String json = new String(Files.readAllBytes(Paths.get("playerProgress.json")), StandardCharsets.UTF_8);

            // Deserialize from JSON
            PlayerProgress progress = mapper.readValue(json, PlayerProgress.class);
            // Set the player attributes from loaded progress
            PlayerAttributes.pantColor = progress.getPantColor();
            PlayerAttributes.levelsCompleted = progress.getLevelsCompleted();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
