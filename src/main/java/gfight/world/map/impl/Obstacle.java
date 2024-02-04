package gfight.world.map.impl;

import java.util.List;

import gfight.common.api.Position2D;
import gfight.engine.graphics.api.GraphicsComponent;
import gfight.world.impl.CachedGameEntityImpl;

public class Obstacle extends CachedGameEntityImpl {

    public Obstacle(List<Position2D> vertexes, Position2D position, GraphicsComponent graphicsComponent) {
        super(vertexes, position, graphicsComponent);
    }

    @Override
    public void reset() {
    }
}
