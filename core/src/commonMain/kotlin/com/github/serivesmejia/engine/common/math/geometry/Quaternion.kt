package com.github.serivesmejia.engine.common.math.geometry

import com.github.serivesmejia.engine.common.math.Math.toRadians
import kotlin.math.*

class Quaternion {

    companion object {
        fun fromDegrees(yaw: Float = 0f, roll: Float = 0f, pitch: Float = 0f) =
            Quaternion(
                yaw.toRadians().toFloat(),
                roll.toRadians().toFloat(),
                pitch.toRadians().toFloat()
            )
    }

    var x = 0f
        private set
    var y = 0f
        private set
    var z = 0f
        private set
    var w = 0f
        private set

    constructor()

    constructor(x: Float, y: Float, z: Float, w: Float) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    constructor(axis: Vector3, angle: Float) {
        val s = sin(angle * 0.5f) / axis.mag

        put(
            axis.x * s,
            axis.y * s,
            axis.z * s,
            cos(angle * 0.5f)
        )
    }

    constructor(yaw: Float = 0f, roll: Float = 0f, pitch: Float = 0f) {
        var angle = yaw * 0.5f

        val sinY = sin(angle)
        val cosY = cos(angle)

        angle = roll * 0.5f
        val sinR = sin(angle)
        val cosR = cos(angle)

        angle = pitch * 0.5f
        val sinP = sin(angle)
        val cosP = cos(angle)

        val cosYR = cosY * cosR
        val sinYR = sinY * sinR
        val cosYsinR = cosY * sinR
        val sinYcosR = sinY * cosR

        put(
            cosYR * cosP - sinYR * sinP,
            cosYR * sinP + sinYR * cosP,
            sinYcosR * cosP + cosYsinR * sinP,
            cosYsinR * cosP - sinYcosR * sinP
        )
    }

    private fun put(x: Float, y: Float, z: Float, w: Float) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    val angle get() = 2f * acos(w)

    val axis: Vector3 get() {
        val sSquared = 1f - w * w
        if(sSquared < 10f * EPSILON)
            return Vector3(1f, 0f, 0f)

        val s = 1f / sqrt(sSquared)
        return Vector3(x * s, y * s, z * s)
    }

    infix fun dot(q: Quaternion) = x * q.x + y * q.y + z * q.z + w * q.w

    /**
     * The lenght squared of the quaternion
     */
    val length2 get() = dot(this)

    /**
     * The lenght of the quaternion
     */
    val length = sqrt(length2)

    /**
     * Normalizes this quaternion and returns the
     * result as a new quaternion, so that...
     * x^2 + y^2 + z^2 + w^2 = 1
     */
    val normalized: Quaternion get() {
        val s = 1f / length
        return Quaternion(x * s, y * s, z * s, w * s)
    }

    /**
     * Calculate the quaternion which is the result of Spherical
     * Linear Interpolation between this and other quaternion.
     *
     * Slerp interpolates assuming constant velocity
     *
     * @param q The other quaternion to interpolate with
     * @param t The ratio between this and q to interpolate. If t = 0 then the result is this, if t = 1 the result will be q
     * @return the resultant quaternion
     */
    fun slerp(q: Quaternion, t: Float): Quaternion {
        val mag = sqrt(length2 * q.length2)
        if(mag > 0f) {
            val product = dot(q) / mag
            val absproduct = abs(product)

            if(absproduct < (1f - EPSILON)) {
                val theta = acos(absproduct)
                val d = sin(theta)

                if(d > 0f) {
                    val sign = if(product < 0) -1f else 1f
                    val s0 = sin((1f - t) * theta) / d
                    val s1 = sin(sign * t * theta) / d

                    return Quaternion(
                        x * s0 + q.x * s1,
                        y * s0 + q.y * s1,
                        z * s0 + q.y * s1,
                        w * s0 + q.w * s1
                    )
                }
            }
        }

        return this
    }

    /**
     * The half angle between this quaternion and the other
     * @param q the other quaternion
     * @return the result of the half angle between this quaternion and other
     */
    fun angle(q: Quaternion) = acos(dot(q) / sqrt(length2 * q.length2))

    fun angleShortestPath(q: Quaternion) = acos(
        dot(if(dot(q) < 0) -q else q) / sqrt(length2 * q.length2)
    ) * 2f

    infix fun rotate(v: Vector3): Vector3 {
        val q = (this * v) * -this
        return Vector3(q.x, q.y, q.z)
    }

    operator fun plus(q: Quaternion) = Quaternion(
        x + q.x, y + q.y, z + q.z, w + q.w
    )

    operator fun minus(q: Quaternion) = Quaternion(
        x - q.x, y - q.y, z - q.z, w - q.w
    )

    operator fun times(q: Quaternion) = Quaternion(
        w * q.x + x * q.w + y * q.z - z * q.y,
        w * q.y + y * q.w + z * q.x - x * q.z,
        w * q.z + z * q.w + x * q.y - y * q.x,
        w * q.w - x * q.x - y * q.y - z * q.z
    )

    operator fun times(v: Vector3) = Quaternion(
        w * v.x + y * v.z - z * v.y,
        w * v.y + z * v.x - x * v.z,
        w * v.z + x * v.y - y * v.x,
        -x * v.x - y * v.y - z * v.z
    )

    operator fun times(by: Float) = Quaternion(
        x * by, y * by, z * by, w * by
    )

    operator fun div(by: Float) = this * (1f / by)

    operator fun unaryPlus() = Quaternion(+x, +y, +z, +w)

    operator fun unaryMinus() = Quaternion(-x, -y, -z, -w)

    fun clone() = +this

}

private val EPSILON = 1.192092896e-07F

private fun Float.cos() = cos(this)
private fun Float.sin() = sin(this)
private fun Float.half() = this * 0.5
