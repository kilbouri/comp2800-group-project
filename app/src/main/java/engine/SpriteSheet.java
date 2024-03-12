package engine;

import java.awt.image.BufferedImage;

public abstract class SpriteSheet {

    class TileIdOutOfBoundsException extends IndexOutOfBoundsException {
        public TileIdOutOfBoundsException(int tileId) {
            super(String.valueOf(tileId));
        }
    }

    private final BufferedImage image;

    private final int tileWidth;
    private final int tileHeight;
    private final int numTilesX;
    private final int numTilesY;
    private final int numTiles;

    // Tiles are cached the first time they are fetched. Thus no cost is
    // incurred for tiles that are never used. If an application wishes to
    // pre-cache some set of tiles, then the application may simply fetch
    // and throw away the returned sprite.
    private BufferedImage[] tileCache;

    protected SpriteSheet(BufferedImage image, int tileWidth, int tileHeight) {
        this.image = image;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.numTilesX = image.getWidth() / tileWidth;
        this.numTilesY = image.getHeight() / tileHeight;

        this.numTiles = numTilesY * numTilesX;
        this.tileCache = new BufferedImage[numTiles];
    }

    /**
     * Retrieve a given tile from this sprite sheet.
     *
     * The caller is warned that the BufferedImage that is returned
     * is cached and shared. Hence, it should not make modifications.
     *
     * @param tileX the X index of the sprite tile
     * @param tileY the Y index of the sprite tile
     * @return a shared BufferedImage reference representing the tile.
     */
    public BufferedImage getTile(int tileX, int tileY) {
        int tileIndex = getTileIndex(tileX, tileY);
        return getTile(tileIndex);
    }

    /**
     * Get a tile by its index. This method is provided to allow applications to
     * easily create their own classes which require sequential access to the tiles.
     * However, in most other cases, {@link SpriteSheet#getTile(int, int)} is
     * preferred.
     *
     * The caller is warned that the BufferedImage that is returned
     * is cached and shared. Hence, it should not make modifications.
     *
     * @param tileIndex the index of the tile to fetch
     * @return a shared BufferedImage reference representing the tile.
     */
    public BufferedImage getTile(int tileIndex) {
        if (tileIndex >= numTiles) {
            throw new TileIdOutOfBoundsException(tileIndex);
        }

        if (tileCache[tileIndex] != null) {
            return tileCache[tileIndex];
        }

        int tileX = getTileX(tileIndex);
        int tileY = getTileY(tileIndex);

        int tilePosX = tileX * tileWidth;
        int tilePosY = tileY * tileHeight;

        BufferedImage subimage = image.getSubimage(tilePosX, tilePosY, tileWidth, tileHeight);
        tileCache[tileIndex] = subimage;
        return subimage;
    }

    /**
     * Computes the index of the tile at (X, Y) in the sprite sheet.
     *
     * @param tileX the X index of the tile
     * @param tileY the Y index of the tile
     * @return the index of the tile in the sprite sheet
     */
    public int getTileIndex(int tileX, int tileY) {
        return (tileY * numTilesX) + tileX;
    }

    /**
     * Computes the X index of the tile at the given index.
     *
     * @param tileIndex the index of the tile
     * @return the corresponding X index of the tile
     */
    public int getTileX(int tileIndex) {
        return tileIndex % numTilesX;
    }

    /**
     * Computes the Y index of the tile at the given index.
     *
     * @param tileIndex the index of the tile
     * @return the corresponding Y index of the tile
     */
    public int getTileY(int tileIndex) {
        return tileIndex / numTilesX;
    }

    /**
     * Get the number of tiles in the horizontal
     * direction of the sprite sheet.
     *
     * @return the number of tiles in the X direction
     */
    public int getNumTilesX() {
        return numTilesX;
    }

    /**
     * Get the number of tiles in the vertical
     * direction of the sprite sheet.
     *
     * @return the number of tiles in the Y direction
     */
    public int getNumTilesY() {
        return numTilesY;
    }

    /**
     * Get the width in pixels of a single tile in the sprite sheet.
     *
     * @return the width of a single tile in pixels
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Get the height in pixels of a single tile in the sprite sheet.
     *
     * @return the height of a single tile in pixels
     */
    public int getTileHeight() {
        return tileHeight;
    }
}
