package com.github.serivesmejia.engine.common.math.geometry

import com.github.serivesmejia.engine.common.math.Math.toRadians
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Represents a Vector in R^3
 * @property x the x component of this Vector i_hat
 * @property y the y component of this Vector j_hat
 * @property z the z component of this Vector k_hat
 */
data class Vector3(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f,
) {
    /**
     * Enum for the different axes of rotation for 3D space
     */
    enum class Axis {
        X, Y, Z
    }

    /**
     * Returns the magnitude of this Vector (uses Euclidean distance formula)
     */
    val mag get() = sqrt(x * x + y * y + z * z)

    /**
     * Rotate this Vector in R^3 by a given Axis
     *
     * @param angle angle in degrees to rotate the vector counter clockwise
     * @param axis  the axis by which the vector is to be rotated in relation towards
     */
    fun rotateBy(angle: Float, axis: Axis): Vector3 {
        val angleInRadians = angle.toRadians()

        val (cosA, sinA) = cos(angleInRadians).toFloat() to sin(angleInRadians).toFloat()

        return when(axis) {
            Axis.X -> Vector3(
                x = x,
                y = y * cosA - z * sinA,
                z = y * sinA + z * cosA,
            )
            Axis.Y -> Vector3(
                x = x * cosA + z * sinA,
                y = y,
                z = -x * sinA + z * cosA,
            )
            Axis.Z -> Vector3(
                x = x * cosA - y * sinA,
                y = x * sinA + y * cosA,
                z = z
            )
        }
    }

    /**
     * Scalar projection of this vector onto another vector
     * @param other Vector onto which to project this vector
     */
    fun scalarProject(other: Vector3) = this.dot(other) / other.dot(other)

    /**
     * Scales the values of this vector by a scalar
     *
     * @param scalar scalar value the vector is to be scaled by
     */
    fun scale(scalar: Float) = copy(x = x * scalar, y = y * scalar, z = z * scalar)

    /**
     * Projects this vector onto another vector
     * @param other the other vector
     */
    fun project(other: Vector3) = other.scale(this.dot(other) / other.dot(other))

    /**
     * Returns the dot product of this vector with argument
     * @param other Vector with which to perform the dot product
     */
    fun dot(other: Vector3) = x * other.x + y * other.y + z * other.z

    /**
     * Adds a vector to this vector and returns a copy
     * @param other vector to add
     * @return a copy of this vector with the result
     */
    operator fun plus(other: Vector3) = copy(x = x + other.x, y = y + other.y, z = z + other.z)

    /**
     * Subtracts a vector to this vector and returns a copy
     * @param other vector to subtract
     * @return a copy of this vector with the result
     */
    operator fun minus(other: Vector3) = copy(x = x - other.x, y = y - other.y, z = z - other.z)

    /**
     * Multiplies this vector by a vector and returns a copy
     * @param other vector to multiply by
     * @return a copy of this vector with the result
     */
    operator fun times(other: Vector3) = copy(x = x * other.x, y = y * other.y, z = z * other.z)

    /**
     * Divides this vector by another vector and returns a copy
     * @param other vector to divide by
     * @return a copy of this vector with the result
     */
    operator fun div(other: Vector3) = copy(x = x / other.x, y = y / other.y, z = z / other.z)

    /**
     * Modulus this vector by another vector and returns a copy
     * @param other vector to mod by
     * @return a copy of this vector with the result
     */
    operator fun rem(other: Vector3) = copy(x = x % other.x, y = y % other.y, z = z % other.z)

    /**
     * Infix
     * Exponentiates this vector by another vector and returns a copy
     * @param other vector to exponentiate by
     * @return a copy of this vector with the result
     */
    infix fun pow(other: Vector3) = copy(x = x.pow(other.x), y = y.pow(other.y))

    /**
     * Positives the values of this vector
     */
    operator fun unaryPlus() = copy(x = +x, y = +y, z = +z)

    /**
     * Negates the values of this vector
     */
    operator fun unaryMinus() = copy(x = -x, y = -y, z = -z)
}

/**
 * Since R^2 (2D space) is a subspace of R^3 (3D space) this function returns the R^2 Vector in R^3
 * @receiver a vector in R^2
 * @return a vector in R^3
 */
fun Vector2.to3D(): Vector3 = run {
    val (x, y) = this
    Vector3(x, y, 0f)
}