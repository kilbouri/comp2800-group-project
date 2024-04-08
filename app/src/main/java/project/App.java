package project;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import project.io.Progress;

public class App {
    public static void main(String[] args) {
        // Use the native system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set native look and feel, exiting");
            e.printStackTrace();
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Progress.saveProgress();
        }));
        // Load progress before starting the frame
        Progress.loadProgress();
        // Invoke the main Swing application
        SwingUtilities.invokeLater(ProjectWindow::new);
    }
}
