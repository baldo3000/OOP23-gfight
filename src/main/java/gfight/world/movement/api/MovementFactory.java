package gfight.world.movement.api;

import gfight.common.api.Vect;
import gfight.world.api.GameEntity;
import gfight.world.api.MovingEntity;
import gfight.world.movement.impl.BfsMovement;
import gfight.world.movement.impl.LinearMovement;
import gfight.world.movement.impl.RandomMovement;
import gfight.world.movement.impl.Fixed;

/**
 * Factory for the creation of movements.
 */
public interface MovementFactory {

    /**
     * Creates the AI BFS based movement.
     * 
     * @param target is the entity that you want to reach
     * @param aget is the entity with the movement
     * @return the AI BFS based movement object
     */
    BfsMovement createIabfsMovement(GameEntity target, MovingEntity agent);

    /**
     * Creates Linear movement.
     * 
     * @param direction
     * @return the Linear Movement Class object
     */
    LinearMovement createLinearMovement(Vect direction);

    /**
     * Creates Linear movement with an acceleration.
     * 
     * @param direction
     * @param accelleration
     * @return the Linear Movement Class object
     */
    LinearMovement createLinearMovement(double accelleration, Vect direction);

    /**
     * 
     * @return the random Movement class object
     */
    RandomMovement createRandomMovement();

    /**
     * 
     * @return the Fixed Movement class object
     */
    Fixed createFixed();

    /**
     * 
     * @return an InputBasedMovement
     */
    InputMovement createInput();
}
