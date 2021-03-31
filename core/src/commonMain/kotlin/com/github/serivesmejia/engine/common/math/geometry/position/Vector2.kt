package com.github.serivesmejia.engine.common.math.geometry.position

import com.github.serivesmejia.engine.common.math.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.pow

/**
 * Represents a vector in R^2
 * @property x the x component of this vector i_hat
 * @property y the y component of this vector j_hat
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
        val angleRad = angle.toRadians()

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
    fun scalarProject(other: Vector2) = this.dot(other) / other.dot(other)

    /**
     * Scales the values of this vector by a scalar
     *
     * @param scalar scalar value the vector is to be scaled by
     */
    fun scale(scalar: Float) = copy(x = x * scalar, y = y * scalar)

    /**
     * Projects this vector onto another vector
     * @param other the other vector
     */
    fun project(other: Vector2) = other.scale(this.dot(other) / other.dot(other))

    /**
     * Positives the values of this vector
     * @return a copy of this vector with the result
     */
    operator fun unaryPlus() = copy(x = +x, y = +y)

    /**
     * Negates the values of this vector
     * @return a copy of this vector with the result
     */
    operator fun unaryMinus() = copy(x = -x, y = -y)

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
     * @param other vector to divide by
     * @return a copy of this vector with the result
     */
    operator fun div(other: Vector2) = copy(x = x / other.x, y = y / other.y)

    /**
     * Modulus this vector by another vector and returns a copy
     * @param other vector to mod by
     * @return a copy of this vector with the result
     */
    operator fun rem(other: Vector2) = copy(x = x % other.x, y = y % other.y)

    /**
     * Infix
     * Exponentiates this vector by another vector and returns a copy
     * @param other vector to exponentiate by
     * @return a copy of this vector with the result
     */
    infix fun pow(other: Vector2) = copy(x = x.pow(other.x), y = y.pow(other.y))


    /**
     * Since R^2 (2D space) is a subspace of R^3 (3D space) this function returns the R^2 Vector in R^3
     * @receiver a vector in R^2
     * @return a vector in R^3
     */
    fun to3D(): Vector3 = run {
        val (x, y) = this
        Vector3(x, y, 0f)
    }

}