package gfight.world.entity.impl;

import java.util.List;
import java.util.Optional;

import gfight.common.api.Position2D;
import gfight.common.api.Vect;
import gfight.engine.graphics.api.GraphicsComponent;
import gfight.engine.graphics.api.GraphicsComponentsFactory;
import gfight.engine.graphics.api.EngineColor;
import gfight.engine.graphics.api.GraphicsComponent.GraphicType;
import gfight.engine.graphics.api.PolygonGraphicsComponent;
import gfight.engine.graphics.impl.GraphicsComponentsFactoryImpl;
import gfight.world.entity.api.ActiveEntity;
import gfight.world.entity.api.CachedGameEntity;
import gfight.world.entity.api.Character;
import gfight.world.entity.api.EntityFactory;
import gfight.world.entity.api.GameEntity;
import gfight.world.entity.api.VertexCalculator;
import gfight.world.entity.api.Character.CharacterType;
import gfight.world.map.api.GameMap;
import gfight.world.map.impl.Chest;
import gfight.world.map.impl.Obstacle;
import gfight.world.movement.api.InputMovement;
import gfight.world.movement.api.Movement;
import gfight.world.movement.impl.BfsMovement;
import gfight.world.movement.impl.MovementFactoryImpl;
import gfight.world.weapon.api.Projectile;
import gfight.world.weapon.impl.ProjectileImpl;

/**
 * This class is a factory of entities.
 */
public class EntityFactoryImpl implements EntityFactory {
        private final VertexCalculator vertexCalculator = new VertexCalculatorImpl();
        private final GraphicsComponentsFactory graphicsComponentsFactory = new GraphicsComponentsFactoryImpl();
        private static final double SPEED_SHOOTERS = 0.75;
        private static final double SPEED_RUNNERS = 1.75;

        @Override
        public final Character createPlayer(final double sideLength, final Position2D position,
                        final int health, final InputMovement movement) {
                final List<Position2D> vertexes = vertexCalculator.triangle(sideLength, position);
                final PolygonGraphicsComponent graphicsComponent = graphicsComponentsFactory.polygon(EngineColor.RED,
                                vertexes,
                                GraphicType.WORLD);
                final Character player = new CharacterImpl(vertexes, position, graphicsComponent, health,
                                CharacterType.PLAYER);
                player.setMovement(Optional.of(movement));
                return player;
        }

        @Override
        public final Character createShooter(final GameEntity target, final double sideLength,
                        final Position2D position,
                        final int health, final GameMap map) {
                final List<Position2D> vertexes = vertexCalculator.triangle(sideLength, position);
                final PolygonGraphicsComponent graphicsComponent = graphicsComponentsFactory.polygon(EngineColor.BLUE,
                                vertexes,
                                GraphicType.WORLD);
                final Character enemy = new CharacterImpl(vertexes, position, graphicsComponent, health,
                                CharacterType.SHOOTER);
                final Optional<Movement> movement = Optional
                                .ofNullable(new BfsMovement(enemy, target, map, SPEED_SHOOTERS));
                enemy.setMovement(movement);
                return enemy;
        }

        @Override
        public final Character createRunner(final GameEntity target, final double sideLength, final Position2D position,
                        final int health,
                        final GameMap map) {
                final List<Position2D> vertexes = vertexCalculator.triangle(sideLength, position);
                final PolygonGraphicsComponent graphicsComponent = graphicsComponentsFactory.polygon(EngineColor.GREEN,
                                vertexes,
                                GraphicType.WORLD);
                final Character enemy = new CharacterImpl(vertexes, position, graphicsComponent, health,
                                CharacterType.RUNNER);
                final Optional<Movement> movement = Optional
                                .ofNullable(new BfsMovement(enemy, target, map, SPEED_RUNNERS));
                enemy.setMovement(movement);
                return enemy;
        }

        @Override
        public final CachedGameEntity createObstacle(final double sideLength, final Position2D position) {
                final List<Position2D> vertexes = vertexCalculator.square(sideLength, position);
                final PolygonGraphicsComponent graphicsComponent = graphicsComponentsFactory.polygon(EngineColor.BLACK,
                                vertexes,
                                GraphicType.WORLD);
                return new Obstacle(vertexes, position, graphicsComponent);
        }

        @Override
        public final ActiveEntity createChest(final double sideLength, final Position2D position, final int health) {
                final List<Position2D> vertexes = vertexCalculator.square(sideLength, position);
                final PolygonGraphicsComponent graphicsComponent = graphicsComponentsFactory.polygon(EngineColor.YELLOW,
                                vertexes, GraphicType.WORLD);
                return new Chest(vertexes, position, graphicsComponent, health);
        }

        @Override
        public final Projectile createProjectile(
                        final Character.CharacterType team,
                        final Position2D position,
                        final Vect direction,
                        final double projectileSize,
                        final int damage) {
                final List<Position2D> vertexes = vertexCalculator.square(projectileSize, position);
                final GraphicsComponent gComp = new GraphicsComponentsFactoryImpl().polygon(
                                team == Character.CharacterType.PLAYER ? EngineColor.RED : EngineColor.BLUE,
                                vertexes,
                                GraphicType.WORLD);
                final Movement movement = new MovementFactoryImpl().createLinearMovement(direction);
                return new ProjectileImpl(vertexes, position, gComp, team, movement, damage);
        }

}
