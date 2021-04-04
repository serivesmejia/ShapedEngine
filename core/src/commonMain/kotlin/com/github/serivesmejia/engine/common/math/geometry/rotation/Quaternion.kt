package com.github.serivesmejia.engine.common.math.geometry.rotation

import com.github.serivesmejia.engine.common.math.*
import com.github.serivesmejia.engine.common.math.geometry.position.Vector3
import kotlin.math.*

/**
 * A quaternion in the form of w + xi + yj + zk
 *                             w+x\hat{i}+y\hat{j}+z\hat{k}
 *
 * @param w is the real component
 * @param x is the i_hat component
 * @param y is the j_hat component
 * @param z is the z_hat component
 */
data class Quaternion(
    private var w: Float = 0f,
    private var x: Float = 0f,
    private var y: Float = 0f,
    private var z: Float = 0f,
) {

    /**
     * Creates this quaternion from the values
     * specified by an angle in degrees and a axis of rotation.
     *
     * @param angle the angle to rotate (in radians)
     * @param axis the axis of rotation
     *
     * q=e^{\frac{\Theta}{2}(x\hat{i}+y\hat{j}+z\hat{k})}=cos{\frac{\Theta}{2}}+(x\hat{i}+y\hat{j}+z\hat{k})sin{\frac{\Theta}{2}}
     */
    constructor(angle: Float, axis: Vector3) : this() {
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
    constructor(eulerAngles: EulerAngles) : this(pitch = eulerAngles.pitch, yaw = eulerAngles.yaw, roll = eulerAngles.roll)

    /**
     * Creates this Quaternion from euler angles in degrees
     * @param pitch the pitch (X) euler angle to create this quaternion from
     * @param yaw the yaw (Y) euler angle to create this quaternion from
     * @param roll the roll (Z) euler angle to create this quaternion from
     */
    constructor(pitch: Float, yaw: Float, roll: Float) : this() {
        fromAngles(pitch.toRadians(), yaw.toRadians(), roll.toRadians())
    }

    //set the values of this quaternion for euler angles in radians
    /**
     *  (cos{\frac{\phi}{2}}cos{\frac{\theta}{2}}cos{\frac{\psi}{2}}+sin{\frac{\phi}{2}}sin{\frac{\theta}{2}}sin{\frac{\psi}{2}})
     * +(sin{\frac{\phi}{2}}cos{\frac{\theta}{2}}cos{\frac{\psi}{2}}-cos{\frac{\phi}{2}}sin{\frac{\theta}{2}}sin{\frac{\psi}{2}})\hat{i}
     * +(cos{\frac{\phi}{2}}sin{\frac{\theta}{2}}cos{\frac{\psi}{2}}+sin{\frac{\phi}{2}}cos{\frac{\theta}{2}}sin{\frac{\psi}{2}})\hat{j}
     * +(cos{\frac{\phi}{2}}cos{\frac{\theta}{2}}sin{\frac{\psi}{2}}-sin{\frac{\phi}{2}}sin{\frac{\theta}{2}}cos{\frac{\psi}{2}})\hat{k}
     */
    private fun fromAngles(yaw: Float, pitch: Float, roll: Float) {
        val (halfYaw, halfPitch, halfRoll) = Triple(
            yaw * 0.5f,
            pitch * 0.5f,
            roll * 0.5f,
        )

        val (cosYaw, cosPitch, cosRoll) = Triple(
            cos(halfYaw),
            cos(halfPitch),
            cos(halfRoll),
        )

        val (sinYaw, sinPitch, sinRoll) = Triple(
            sin(halfYaw),
            sin(halfPitch),
            sin(halfRoll),
        )

        put(
            w = cosRoll * cosPitch * cosYaw + sinRoll * sinPitch * sinYaw,
            x = sinRoll * cosPitch * cosYaw - cosRoll * sinPitch * sinYaw,
            y = cosRoll * sinPitch * cosYaw + sinRoll * cosPitch * sinYaw,
            z = cosRoll * cosPitch * sinYaw - sinRoll * sinPitch * cosYaw,
        )

        normalizeLocal()
    }

    private fun put(w: Float, x: Float, y: Float, z: Float) {
        this.w = w
        this.x = x
        this.y = y
        this.z = z
    }

    private fun normalizeLocal() {
        val n = 1 / norm
        x *= n
        y *= n
        z *= n
        w *= n
    }

    private fun loadIdentity() = put(w=1f, x=0f, y=0f, z=0f)

    /**
     * This quaternion converted to Euler
     * rotation angles in degrees (pitch, yaw, roll)
     */
    val euler: EulerAngles by lazy { // utilize lazy initialization to perform calculation on first call and store for later use
        val sqw = w * w
        val sqx = x * x
        val sqy = y * y
        val sqz = z * z

        val unit = sqx + sqy + sqz + sqw
        val test = x * y + z * w

        when {
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
        }.toDegrees()
    }

    /**
     * The norm of this quaternion. The norm of the quaternion is the square root of the conjugate multiplied by itself
     *
     * n(q)=\sqrt{\overline{q}q}=\sqrt{w^2+x^2+y^2+z^2}
     *
     * where \overline{q} is the conjugate of the quaternion q
     */
    val norm by lazy { sqrt(w * w + x * x + y * y * z * z) }

    /**
     * The conjugate of this quaternion
     *
     * \overline{q}=w-x\hat{i}-y\hat{j}-z\hat{k}
     */
    val conjugate by lazy { copy(w = w, x = -x, y = -y, z = -z) }

    /**
     * Sums another quaternion to this quaternion
     */
    operator fun plus(q: Quaternion) = copy(
        w = w + q.w,
        x = x + q.x,
        y = y + q.y,
        z = z + q.z,
    )

    /**
     * Subtracts another quaternion from this quaternion
     */
    operator fun minus(q: Quaternion) = copy(
        w = w - q.w,
        x = x - q.x,
        y = y - q.y,
        z = z - q.z,
    )

    /**
     * Multiplies another quaternion to this quaternion
     */
    operator fun times(q: Quaternion) = run {
        val (a1, a2, a3, a4) = this
        val (b1, b2, b3, b4) = q

        copy(
            w = a1 * b1 - a2 * b2 - a3 * b3 - a4 * b4,
            x = a1 * b2 + a2 * b1 + a3 * b4 - a4 * b3,
            y = a1 * b3 - a2 * b4 + a3 * b1 + a4 * b2,
            z = a1 * b4 + a2 * b3 - a3 * b2 + a4 * b1,
        )
    }

    /**
     * Gives the inverse of this quaternion
     */
    val inverse by lazy {
        conjugate.scale(1 / (norm * norm))
    }

    /**
     * Scales every value of this quaternion
     */
    fun scale(scalar: Float) = copy(
        w = w * scalar,
        x = x * scalar,
        y = y * scalar,
        z = z * scalar,
    )

    /**
     * Positives this quaternion to a new quaternion
     */
    operator fun unaryPlus() = copy(w = +w, x = +x, y = +y, z = +z)

    /**
     * Negates this quaternion to a new quaternion
     */
    operator fun unaryMinus() = copy(w = -w, x = -x, y = -y, z = -z)

    override fun toString() = "Quaternion(w=$w, x=$x, y=$y, z=$z, euler=$euler)"

    companion object {
        val identity = Quaternion(w = 1f, x = 0f, y = 0f, z = 0f)
    }
}

/**
 * Enum for the different axes of rotation for 3D space
 */
enum class Axis {
    X, Y, Z;

    companion object {
        val I_HAT = X
        val J_HAT = Y
        val K_HAT = Z
    }
}