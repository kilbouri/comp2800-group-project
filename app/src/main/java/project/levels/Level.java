package project.levels;

import engine.core.LevelLoader;

public enum Level {
    Developer1(DeveloperLevel1Loader.class),
    Developer2(DeveloperLevel2Loader.class),
    ScaledSpriteLevel(ScaledSpriteLevel.class);

    private LevelLoader loader;

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
