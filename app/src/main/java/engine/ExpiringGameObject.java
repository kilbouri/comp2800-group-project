package engine;

public abstract class ExpiringGameObject extends GameObject {

    private double lifetime;

    /**
     * Creates a new ExpiringGameObject with the specified duration
     * until death.
     *
     * @param lifetime the number of seconds until the object should be destroyed.
     */
    public ExpiringGameObject(double lifetime) {
        this.lifetime = lifetime;
    }

    /**
     * Updates the expiring game object. Subclasses MUST call
     * {@code super.update(deltaTime)}.
     */
    @Override
    public void update(double deltaTime) {
        lifetime -= deltaTime;

        if (lifetime <= 0) {
            onExpiration();
            destroy();
        }
    }

    /**
     * Called just before the object is destroyed on expiration.
     * Subclasses do NOT need to call {@code super.onExpiration()}.
     */
    protected void onExpiration() {
        // no-op
    }
}
