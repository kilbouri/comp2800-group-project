package project.menus;

import static project.levels.Level.SCREEN_WIDTH_PX;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import project.MainLoop;
import project.ProjectWindow;
import project.ui.FancyButton;

public class GamePanel extends JLayeredPane implements Menu {

    static class PauseMenu extends JPanel {
        public PauseMenu(GamePanel gamePanel, ProjectWindow parentWindow) {
            super();
            setOpaque(true);
            setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));

            FancyButton resume = new FancyButton("Resume");
            FancyButton mainMenu = new FancyButton("Main Menu");

            resume.addActionListener(e -> gamePanel.unpause());
            mainMenu.addActionListener(e -> parentWindow.switchMenu(Menu.START));

            add(resume);
            add(mainMenu);
        }
    }

    private ProjectWindow parentWindow;

    private MainLoop mainLoop;
    private PauseMenu pauseMenu;

    public GamePanel(ProjectWindow window) {
        parentWindow = window;
        setOpaque(true);

        add(mainLoop = new MainLoop(this), JLayeredPane.FRAME_CONTENT_LAYER);
        add(pauseMenu = new PauseMenu(this, window), JLayeredPane.MODAL_LAYER);

        pauseMenu.setBackground(Color.DARK_GRAY);

        Dimension pauseSize = pauseMenu.getPreferredSize();
        pauseMenu.setBounds(SCREEN_WIDTH_PX - pauseSize.width - 8, 8, pauseSize.width, pauseSize.height);
    }

    public MainLoop getLoop() {
        return mainLoop;
    }

    public void switchMenu(String menuName) {
        parentWindow.switchMenu(menuName);
    }

    public void pause() {
        pauseMenu.setVisible(true);
        mainLoop.suspend();
    }

    public void unpause() {
        pauseMenu.setVisible(false);
        mainLoop.resume();
    }

    @Override
    public void onShown() {
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
}
