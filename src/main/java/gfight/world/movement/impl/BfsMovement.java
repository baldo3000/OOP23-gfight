package gfight.world.movement.impl;

import gfight.common.api.Position2D;
import gfight.common.impl.VectorImpl;
import gfight.world.entity.api.Character;
import gfight.world.entity.api.GameEntity;
import gfight.world.entity.api.Character.CharacterType;
import gfight.world.map.api.GameMap;
import gfight.world.map.api.GameTile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultEdge;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.jgrapht.alg.shortestpath.BFSShortestPath;

/**
 * Class that represents the movement of the enemies.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "This class needs to access the actual agent and target")
public final class BfsMovement extends BaseMovement {
    private final GameEntity target;
    private final Character agent;
    private final GameMap map;
    private final double speed;
    private static final int RANGE_RUNNER = GameMap.TILE_DIM;
    private static final int TILES_SHOOTER = 4;

    private GameTile cachedTargetTile;
    private List<Position2D> cachedGraphPath;
    private boolean useCachedPath;
    private int pathPosition;

    /**
     * Constructor of bfs movement.
     * 
     * @param agent  the enemy
     * @param target that the enemy needs to reach (Chest of Player)
     * @param map    of the game
     * @param speed  of the agent
     */
    public BfsMovement(final Character agent, final GameEntity target, final GameMap map, final double speed) {
        this.target = target;
        this.agent = agent;
        this.map = map;
        this.speed = speed;

        this.cachedGraphPath = getPathFromBfs();
        this.cachedTargetTile = this.map.searchTile(this.target.getPosition());
    }

    @Override
    public void update() {
        this.agent.pointTo(this.target.getPosition());
        final List<Position2D> path = getPathFromBfsCached();
        if (!path.isEmpty()) {
            if (agent.getType() == CharacterType.SHOOTER) {
                handleShooterBehavior(path);
            } else if (agent.getType() == CharacterType.RUNNER) {
                handleRunnerBehavior(path);
            }
        } else {
            agent.setDirection(new VectorImpl(0, 0));
        }
    }

    private void handleShooterBehavior(final List<Position2D> path) {
        if (path.size() - this.pathPosition < TILES_SHOOTER) {
            stopAndAttack();
        } else {
            move(path);
        }
    }

    private void handleRunnerBehavior(final List<Position2D> path) {
        if (agent.getPosition().getDistance(target.getPosition()) <= RANGE_RUNNER) {
            stopAndAttack();
        } else {
            move(path);
        }
    }

    private void stopAndAttack() {
        agent.setDirection(new VectorImpl(0, 0));
        agent.makeDamage();
    }

    private void move(final List<Position2D> path) {
        if (this.map.searchTile(this.agent.getPosition()).equals(this.map.searchTile(path.get(this.pathPosition)))
                && this.pathPosition < path.size() - 1) {
            this.pathPosition++;
        }
        if (this.pathPosition == path.size() - 1) {
            agent.setDirection(new VectorImpl(agent.getPosition(), this.target.getPosition()).norm().scale(speed));
        } else {
            agent.setDirection(new VectorImpl(agent.getPosition(), path.get(this.pathPosition)).norm().scale(speed));
        }
    }

    private List<Position2D> getPathFromBfsCached() {
        final var targetTile = this.map.searchTile(this.target.getPosition());
        if (!this.cachedTargetTile.equals(targetTile)) {
            if (!this.useCachedPath) {
                this.cachedTargetTile = targetTile;
                this.cachedGraphPath = getPathFromBfs();
                this.pathPosition = 0;
            }
            this.useCachedPath = !this.useCachedPath;
        }
        return this.cachedGraphPath;
    }

    private List<Position2D> getPathFromBfs() {
        final Position2D startNode = agent.getPosition();
        final Position2D targetNode = target.getPosition();
        final BFSShortestPath<GameTile, DefaultEdge> bfs = new BFSShortestPath<>(map.getTileGraph());
        return Optional.ofNullable(bfs.getPath(map.searchTile(startNode), map.searchTile(targetNode)))
                .filter(path -> Objects.nonNull(path) && path.getLength() >= 1)
                .map(path -> path.getVertexList()
                        .stream()
                        .map(GameTile::getPosition)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
