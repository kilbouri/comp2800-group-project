package project.levels;

import engine.core.LevelLoader;

public enum Level {
    Tutorial1(Tutorial1Loader.class),
    Tutorial2(Tutorial2Loader.class),
    Main1(Main1Loader.class),
    Main2(Main2Loader.class);

    private LevelLoader loader;

    public static final int SCREEN_WIDTH_PX = 900;
    public static final int SCREEN_HEIGHT_PX = 600;

    public static final int GRID_SIZE = 32;

    public static final int MAX_GRID_X = (int) Math.ceil((double) SCREEN_WIDTH_PX / GRID_SIZE);
    public static final int MAX_GRID_Y = (int) Math.ceil((double) SCREEN_HEIGHT_PX / GRID_SIZE);

    <T extends LevelLoader> Level(Class<T> l) {
        try {
            this.loader = l.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LevelLoader getLoader() {
        return loader;
    }
}
