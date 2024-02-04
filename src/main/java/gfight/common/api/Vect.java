package gfight.common.api;

import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;

/**
 * This class represents a 2D vector with x and y components.
 */
public interface Vect extends Vector<Euclidean2D> {

    /**
     * Returns a new VectorImpl representing the normalization of the current
     * vector.
     *
     * @return The normalized vector.
     */
    Vect norm();

    /**
     * Adds the vector to the current vector and returns a new
     * VectorImpl representing the result.
     *
     * @param vector The vector to add.
     * @return A new VectorImpl representing the sum of the vectors.
     * @throws IllegalArgumentException if the provided vector is not of type
     *                                  VectorImpl.
     */
    Vect sum(Vect vector);

    /**
     * Scales the vector by the specified value and returns a new VectorImpl
     * representing the result.
     *
     * @param value The scaling factor.
     * @return A new VectorImpl representing the scaled vector.
     */
    Vect scale(double value);

    /**
     * Returns a new VectorImpl representing the negation of the current vector.
     *
     * @return The negated vector.
     */
    Vect revert();

    /**
     * 
     * @return the lenght of the x of the vector.
     */
    double getX();

    /**
     * 
     * @return the lenght of the y of the vector.
     */
    double getY();
}
