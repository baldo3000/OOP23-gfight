package gfight.world.entity.impl;

import java.util.List;
import java.util.Set;
import gfight.common.api.Position2D;
import gfight.common.api.Vect;
import gfight.common.impl.Position2DImpl;
import gfight.common.impl.VectorImpl;
import gfight.engine.graphics.api.GraphicsComponent;
import gfight.world.entity.api.GameEntity;
import gfight.world.hitbox.api.Hitbox;
import gfight.world.hitbox.api.Hitboxes;
import gfight.world.hitbox.impl.HitboxImpl;
import gfight.world.hitbox.impl.HitboxesImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Implementation of Game Entity.
 */
public final class GameEntityImpl implements GameEntity {
    private List<Position2D> vertexes = new ArrayList<>();
    private Position2D position;
    private Set<GraphicsComponent> graphicsComponents;
    private final Set<GameEntity> ignoredEntities = new HashSet<>();
    private final Hitboxes hitboxes = new HitboxesImpl();

    /**
     * Game Entity constructor that creates gameEntity with vertexes position and
     * graphic component.
     * 
     * @param vertexes          the vertexes of the polygon.
     * @param position          the starting position of the polygon.
     * @param graphicsComponent the graphic of this entity.
     */
    public GameEntityImpl(final List<Position2D> vertexes, final Position2D position,
            final GraphicsComponent graphicsComponent) {
        this.graphicsComponents = Set.of(graphicsComponent);
        this.position = position;
        this.vertexes.addAll(vertexes);
    }

    @Override
    public Hitbox getHitBox() {
        return new HitboxImpl(vertexes);
    }

    @Override
    public void setPosition(final Position2D position) {
        final Vect distance = new VectorImpl(this.position, position);
        this.vertexes = this.vertexes.stream().map(vertex -> vertex.sum(distance)).toList();
        this.position = new Position2DImpl(position);
    }

    @Override
    public Position2D getPosition() {
        return this.position;
    }

    @Override
    public Set<GameEntity> getAllCollided(final Set<? extends GameEntity> gameObjects) {
        final Hitbox boundingBox = this.getHitBox();
        final Set<GameEntity> collidedObjects = new HashSet<>();
        for (final var object : gameObjects) {
            if (!object.getPosition().equals(this.getPosition())
                    && !ignoredEntities.contains(object)
                    && this.hitboxes.isColliding(boundingBox, object.getHitBox())) {
                collidedObjects.add(object);
            }
        }
        return collidedObjects;
    }

    @Override
    public void setIgnoredEntities(final Set<GameEntity> ignoredEntities) {
        this.ignoredEntities.clear();
        this.ignoredEntities.addAll(ignoredEntities);
    }

    @Override
    public Set<GraphicsComponent> getGraphics() {
        return Collections.unmodifiableSet(this.graphicsComponents);
    }

    @Override
    public List<Position2D> getPosition2Ds() {
        return Collections.unmodifiableList(this.vertexes);
    }

    @Override
    public void setCoordinates(final List<Position2D> vertexes) {
        this.vertexes = new ArrayList<>(vertexes);
    }

    @Override
    public void setGraphics(final Set<GraphicsComponent> graphics) {
        this.graphicsComponents = new HashSet<>(graphics);
    }
}
