package gfight.world.movement.impl;

import java.util.Random;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * An implementation of Movement that gives a random movement.
 */
public final class RandomMovement extends BaseMovement {
    private int counter;

    /**
     * Random Movement constructor.
     */
    public RandomMovement() {
        updateCounter();
    }

    private void updateCounter() {
        final Random rand = new Random();
        counter = rand.nextInt(20) + 50;
        setDirection(new Vector2D(1.0 * (rand.nextInt(3) - 1), 1.0 * (rand.nextInt(3) - 1)));
    }

    @Override
    public void update() {
        if (counter > 0) {
            counter--;
        } else {
            updateCounter();
        }
    }

}
