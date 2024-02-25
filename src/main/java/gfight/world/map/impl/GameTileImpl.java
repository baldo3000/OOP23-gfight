package gfight.world.map.impl;

import java.util.Objects;

import gfight.common.api.Position2D;
import gfight.world.map.api.GameTile;

/**
 * Standard implementation of a GameTile.
 */
public final class GameTileImpl implements GameTile {

    private GameTile.TileType type;
    private final Position2D centerPosition;
    private final double dimension;

    /**
     * Creates a new GameTile of the given type.
     * 
     * @param type           the type of the tile, see
     *                       {@link gfight.world.map.api.GameTile.TileType}
     * @param centerPosition the position of the center of the tile
     * @param dimension      the dimension of the side of the tile
     */
    public GameTileImpl(final GameTile.TileType type, final Position2D centerPosition, final double dimension) {
        this.type = type;
        this.centerPosition = centerPosition;
        this.dimension = dimension;
    }

    @Override
    public double getDimension() {
        return this.dimension;
    }

    @Override
    public TileType getType() {
        return this.type;
    }

    @Override
    public void setType(final TileType type) {
        this.type = type;
    }

    @Override
    public Position2D getPosition() {
        return this.centerPosition;
    }

    @Override
    public boolean contains(final Position2D position) {
        final var centerOffset = this.dimension / 2;
        return position.getX() <= this.centerPosition.getX() + centerOffset
                && position.getX() >= this.centerPosition.getX() - centerOffset
                && position.getY() <= this.centerPosition.getY() + centerOffset
                && position.getY() >= this.centerPosition.getY() - centerOffset;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameTile)) {
            return false;
        }
        final GameTile tile = (GameTile) obj;
        return this.centerPosition.equals(tile.getPosition())
                && Double.compare(this.dimension, tile.getDimension()) == 0
                && this.type == tile.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getPosition(), getDimension());
    }

    @Override
    public String toString() {
        return "(" + this.centerPosition.getX() + ", " + this.centerPosition.getY() + ")";
    }
}
