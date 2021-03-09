package com.github.serivesmejia.engine.common.geometry

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot

/**
 * Represents a vector in a 2d space
 * @property x the x value of this vector
 * @property y the y value of this vector
 */
data class Vector2(var x: Float = 0f,
                   var y: Float = 0f) {

    /**
     * Returns the magnitude/hypot of this vector
     */
    val mag get() = hypot(x, y)

    /**
     * The angle of this vector
     */
    val angle get() = atan2(y.toDouble(), x.toDouble())

    /**
     * Rotate the vector in Cartesian space
     *
     * @param angle angle in degrees to rotate vector counter-clockwise
     */
    fun rotateBy(angle: Float): Vector2 {
        val angleRad = Math.toRadians(angle.toDouble())

        val cosA = cos(angleRad)
        val sinA = cos(angleRad)

        return Vector2(
            (x * cosA - y * sinA).toFloat(),
            (x * sinA + y * cosA).toFloat()
        )
    }

    /**
     * Returns the dot product of this vector with argument
     * @param other Vector with which to perform the dot product
     */
    fun dot(other: Vector2) = x * other.x + y * other.y

    /**
     * Scalar projection of this vector onto another vector
     * @param other Vector onto which to project this vector
     */
    fun scalarProject(other: Vector2) = dot(other) / other.mag

    /**
     * Scales the values of this vector by a scalar
     */
    fun scale(scalar: Float) = Vector2(x * scalar, y * scalar)

    /**
     * Projects this vector onto another vector
     * @param other the other vector
     */
    fun project(other: Vector2) = other.scale(dot(other) / (mag * other.mag))

    /**
     * Negates the values of this vector
     */
    fun unaryPlus() {
        x *= 1.0f
        y *= 1.0f
    }

    /**
     * Negates the values of this vector
     */
    fun unaryMinus() {
        x *= -1.0f
        y *= -1.0f
    }

    /**
     * Adds a vector to this vector and returns a copy
     * @param other vector to add
     * @return a copy of this vector with the result
     */
    operator fun plus(other: Vector2) = copy(x = x + other.x, y = y + other.y)

    /**
     * Subtracts a vector to this vector and returns a copy
     * @param other vector to subtract
     * @return a copy of this vector with the result
     */
    operator fun minus(other: Vector2) = copy(x = x - other.x, y = y - other.y)

    /**
     * Multiplies this vector by a vector and returns a copy
     * @param other vector to multiply by
     * @return a copy of this vector with the result
     */
    operator fun times(other: Vector2) = copy(x = x * other.x, y = y * other.y)

    /**
     * Divides this vector by another vector and returns a copy
     * @param other vector to multiply by
     * @return a copy of this vector with the result
     */
    operator fun div(other: Vector2) = copy(x = x / other.x, y = y / other.y)

    /**
     * Adds a vector to this vector
     * @param other vector to add
     * @return a copy of this vector with the result
     */
    operator fun plusAssign(other: Vector2) {
        x += other.x
        y += other.y
    }

    /**
     * Subtracts a vector to this vector
     * @param other vector to subtract
     * @return a copy of this vector with the result
     */
    operator fun minusAssign(other: Vector2) {
        x -= other.x
        y -= other.y
    }

}