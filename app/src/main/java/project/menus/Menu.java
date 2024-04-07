package project.menus;

public interface Menu {
    public static final String START = "startMenu";
    public static final String LEVELS = "levelsMenu";
    public static final String CUSTOMIZATION = "customizationMenu";
    public static final String GAME = "gameMenu";

    void onShown();

    void onHidden();
}
