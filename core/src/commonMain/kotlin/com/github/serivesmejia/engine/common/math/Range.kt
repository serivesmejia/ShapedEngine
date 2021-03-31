package com.github.serivesmejia.engine.common.math

import com.github.serivesmejia.engine.common.math.geometry.position.Vector2
import com.github.serivesmejia.engine.common.math.geometry.position.Vector3

/**
 * Utility functions for range-related
 * operations with numbers.
 */
object Range {

    /**
     * Clips a number between a min and max range
     *
     * @param value the value to clip
     * @param min the minimum value
     * @param max the maximum value
     *
     * @return returns the value, clipped inside the specified range
     */
    fun <T : Number> clip(value: T, min: T, max: T): T {
        if(value < min) return min
        if(value > max) return max

        return value
    }

    /**
     * Checks if a number is inside a range
     *
     * @param actual the actual number to check
     * @param min the minimum value
     * @param max the maximum value
     *
     * @return a boolean indicating if the actual value is inside the range
     */
    fun isInRange(actual: Number, min: Number, max: Number) = actual >= min && actual <= max

    /**
     * Checks if a number is inside a range, and throws an exception if it isn't
     *
     * @param actual the actual number to check
     * @param min the minimum value
     * @param max the maximum value
     *
     * @throws IllegalArgumentException if the value is not inside the range
     */
    fun assertInRange(actual: Number, min: Number, max: Number) {
        if(!isInRange(actual, min, max)) {
            throw IllegalArgumentException("Given $actual is out of the ($min, $max) range")
        }
    }

    private operator fun <T : Number> Number.compareTo(other: T): Int {
        val dThis = this.toDouble()
        val dOther = other.toDouble()

        return when {
            dThis < dOther -> -1
            dThis > dOther -> 1
            else -> 0
        }
    }

}

/**
 * Represents a 1D range
 *
 * @param T the number type of this range (can be inferred from min and max)
 * @param min the min value of this range
 * @param max the max value of this range
 */
data class Range1<T : Number>(val min: T, val max: T) {

    /**
     * Checks if a number is inside this range
     * @see Range.isInRange
     */
    fun isInRange(value: T) = Range.isInRange(value, min, max)

    /**
     * Clips a number between this range
     * @see Range.clip
     */
    fun clip(value: T) = Range.clip(value, min, max)

    /**
     * Checks if a number is inside this range, and throws an exception if it isn't
     * @see Range.assertInRange
     */
    fun assertInRange(actual: T) = Range.assertInRange(actual, min, max)

}


/**
 * Represents a 2D range
 *
 * @param T the number type of this range (can be inferred from min and max)
 *
 * @param minX the min X value of this range
 * @param maxX the max X value of this range
 * @param minY the min Y value of this range
 * @param maxY the max Y value of this range
 */
data class Range2<T : Number>(val minX: T, val maxX: T,
                              val minY: T, val maxY: T) {

    /**
     * Checks if two (X, Y) numbers are inside this range
     *
     * @param valueX the X value to check
     * @param valueY the Y value to check
     *
     * @see Range.isInRange
     */
    fun isInRange(valueX: T, valueY: T) =
        Range.isInRange(valueX, minX, maxX) && Range.isInRange(valueY, minY, maxY)

    /**
     * Clips two (X, Y) numbers between this range and returns a Vector2
     *
     * @param valueX the X value to check
     * @param valueY the Y value to check
     *
     * @return a Vector2 containing the clipped numbers
     * @see Range.clip
     */
    fun clip(valueX: T, valueY: T) = Vector2(
        Range.clip(valueX, minX, maxX).toFloat(),
        Range.clip(valueY, minY, maxY).toFloat(),
    )

    /**
     * Checks if two numbers are inside this range, and throws an exception if any of they aren't
     * @see Range.assertInRange
     */
    fun assertInRange(actualX: T, actualY: T) {
        Range.assertInRange(actualX, minX, maxX)
        Range.assertInRange(actualY, minY, maxY)
    }

}


/**
 * Represents a 3D range
 *
 * @param T the number type of this range (can be inferred from min and max)
 *
 * @param minX the min X value of this range
 * @param maxX the max X value of this range
 *
 * @param minY the min Y value of this range
 * @param maxY the max Y value of this range
 *
 * @param minZ the min Z value of this range
 * @param maxZ the max Z value of this range
 */
data class Range3<T : Number>(val minX: T, val maxX: T,
                              val minY: T, val maxY: T,
                              val minZ: T, val maxZ: T) {

    /**
     * Checks if three (X, Y, Z) numbers are inside this range
     *
     * @param valueX the X value to check
     * @param valueY the Y value to check
     * @param valueY the Z value to check
     *
     * @see Range.isInRange
     */
    fun isInRange(valueX: T, valueY: T, valueZ: T) =
        Range.isInRange(valueX, minX, maxX) && Range.isInRange(valueY, minY, maxY) && Range.isInRange(valueZ, minZ, maxZ)

    /**
     * Clips two (X, Y) numbers between this range and returns a Vector2
     *
     * @param valueX the X value to check
     * @param valueY the Y value to check
     * @param valueY the Z value to check
     *
     * @return a Vector2 containing the clipped numbers
     * @see Range.clip
     */
    fun clip(valueX: T, valueY: T, valueZ: T) = Vector3(
        Range.clip(valueX, minX, maxX).toFloat(),
        Range.clip(valueY, minY, maxY).toFloat(),
        Range.clip(valueZ, minZ, maxZ).toFloat(),
    )

    /**
     * Checks if three numbers are inside this range, and throws an exception if any of they aren't
     * @see Range.assertInRange
     */
    fun assertInRange(actualX: T, actualY: T, actualZ: T) {
        Range.assertInRange(actualX, minX, maxX)
        Range.assertInRange(actualY, minY, maxY)
        Range.assertInRange(actualZ, minZ, maxZ)
    }

}