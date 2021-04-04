package com.github.serivesmejia.engine.common.math.geometry.rotation

import com.github.serivesmejia.engine.common.math.*
import com.github.serivesmejia.engine.common.math.geometry.position.Vector3
import kotlin.math.*

class Quaternion {

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

    /**
     * Creates this quaternion from the values
     * specified by an angle in degrees and a axis of rotation.
     *
     * @param angle the angle to rotate (in radians)
     * @param axis the axis of rotation
     */
    constructor(angle: Float, axis: Vector3) {
        val normAxis = axis.normalized

        if(normAxis.x == 0f && normAxis.y == 0f && normAxis.z == 0f) {
            loadIdentity()
        } else {
            val halfAngle = angle.toRadians() * 0.5f
            val sin = sin(halfAngle)

            w = cos(halfAngle)
            x = sin * normAxis.x
            y = sin * normAxis.y
            z = sin * normAxis.z
        }
    }

    /**
     * Creates this Quaternion from EulerAngles, in degrees
     * @param eulerAngles the EulerAngles in radians to create this quaternion from
     */
    constructor(eulerAngles: EulerAngles) : this(eulerAngles.pitch, eulerAngles.yaw, eulerAngles.roll)

    /**
     * Creates this Quaternion from euler angles in degrees
     * @param pitch the pitch (X) euler angle to create this quaternion from
     * @param yaw the yaw (Y) euler angle to create this quaternion from
     * @param roll the roll (Z) euler angle to create this quaternion from
     */
    constructor(pitch: Float = 0f, yaw: Float = 0f, roll: Float = 0f) {
        fromAngles(pitch.toRadians(), yaw.toRadians(), roll.toRadians())
    }

    //set the values of this quaternion for euler angles in radians
    private fun fromAngles(xAngle: Float, yAngle: Float, zAngle: Float) {
        var angle = zAngle * 0.5f
        val sinZ = sin(angle)
        val cosZ = cos(angle)

        angle = yAngle * 0.5f
        val sinY = sin(angle)
        val cosY = cos(angle)

        angle = xAngle * 0.5f
        val sinX = sin(angle)
        val cosX = cos(angle)

        val cosYXcosZ = cosY * cosZ
        val sinYXsinZ = sinY * sinZ
        val cosYXsinZ = cosY * sinZ
        val sinYXcosZ = sinY * cosZ

        put(
            (cosYXcosZ * sinX + sinYXsinZ * cosX),
            (sinYXcosZ * cosX + cosYXsinZ * sinX),
            (cosYXsinZ * cosX - sinYXcosZ * sinX),
            (cosYXcosZ * cosX - sinYXsinZ * sinX)
        )

        normalizeLocal()
    }

    private fun put(x: Float, y: Float, z: Float, w: Float) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    private fun normalizeLocal() {
        val n = norm.invSqrt()
        x *= n
        y *= n
        z *= n
        w *= n
    }

    private fun loadIdentity() = put(0f, 0f, 0f, 1f)

    private var cachedEuler: EulerAngles? = null

    /**
     * This quaternion converted to Euler
     * rotation angles in degrees (pitch, yaw, roll)
     */
    val euler: EulerAngles
        get() {
            // if we already performed calculations before,
            // return them to improve performance
            if(cachedEuler != null) return cachedEuler!!.toDegrees()

            val sqw = w * w
            val sqx = x * x
            val sqy = y * y
            val sqz = z * z

            val unit = sqx + sqy + sqz + sqw
            val test = x * y + z * w

            //save the result in a cached variable so that we only need to perform this once
            cachedEuler = when {
                test > 0.499 * unit -> { // singularity at north pole
                    EulerAngles(
                        0f,
                        2f * atan2(x, w),
                        HALF_PI.toFloat(),
                    )
                }
                test < -0.499 * unit -> { //singularity at south pole
                    EulerAngles(
                        0f,
                        -2f * atan2(x, w),
                        -HALF_PI.toFloat(),
                    )
                }
                else -> {
                    EulerAngles(
                        atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw), // pitch
                        atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw), // yaw
                        asin(2 * test / unit) // roll
                    )
                }
            }

            return cachedEuler!!.toDegrees()
        }

    /**
     * The norm of this quaternion. This is the
     * dot product of this quaternion with itself
     */
    val norm get() = w * w + x * x + y * y + z * z

    /**
     * Sums another quaternion to this quaternion
     */
    operator fun plus(q: Quaternion) = Quaternion(
        x + q.x, y + q.y, z + q.z, w + q.w
    )

    /**
     * Subtracts another quaternion from this quaternion
     */
    operator fun minus(q: Quaternion) = Quaternion(
        x - q.x, y - q.y, z - q.z, w - q.w
    )

    /**
     * Positives this quaternion to a new quaternion
     */
    operator fun unaryPlus() = Quaternion(+x, +y, +z, +w)

    /**
     * Negates this quaternion to a new quaternion
     */
    operator fun unaryMinus() = Quaternion(-x, -y, -z, -w)

    /**
     * Returns a copy of this quaternion
     */
    fun clone() = +this

    override fun toString() = "Quaternion(x=$x, y=$y, z=$z, w=$w, euler=$euler)"

}

/**
 * Enum for the different axes of rotation for 3D space
 */
enum class Axis {
    X, Y, Z
}