package project.menus;

import javax.swing.JLayeredPane;

import project.MainLoop;
import project.ProjectWindow;

public class GamePanel extends JLayeredPane implements Menu {

    private ProjectWindow parentWindow;
    private MainLoop mainLoop;

    public GamePanel(ProjectWindow window) {
        parentWindow = window;
        add(mainLoop = new MainLoop(this));
    }

    public MainLoop getLoop() {
        return mainLoop;
    }

    @Override
    public void onShown() {
        setVisible(true);

        // Ok now we definitely need the loop to be stopped, because we're about to
        // start it again.
        getLoop().stop(true);
        getLoop().start();
        getLoop().requestFocus();
    }

    @Override
    public void onHidden() {
        // request the loop stop, but don't wait for it
        getLoop().stop(false);
    }

    public void switchMenu(String menuName) {
        parentWindow.switchMenu(menuName);
    }
}
