package com.github.serivesmejia.engine.common.math.geometry.rotation

import com.github.serivesmejia.engine.common.math.geometry.position.Vector2
import com.github.serivesmejia.engine.common.math.toRadians
import com.github.serivesmejia.engine.common.math.toDegrees
import kotlin.math.*

class Rotation2(radians: Float = 0f) {

    companion object {
        val zero = Rotation2()

        /**
         * Creates a new Rot2d from degrees
         * @param degrees degrees to set to the new Rot2d
         * @return new Rot2d from degrees
         */
        fun degrees(degrees: Float) = Rotation2(degrees.toRadians())

        fun vector(x: Float, y: Float): Rotation2 {
            val hy = hypot(x, y)

            val (sin, cos) = if (hy > 1e-6) {
                Pair(y / hy, x / hy)
            } else {
                Pair(0.0, 1.0)
            }

            return Rotation2(atan2(sin.toDouble(), cos.toDouble()).toFloat())
        }

        fun vector(vec: Vector2) = vector(vec.x, vec.y)
    }

    var radians = radians
        private set

    val cos by lazy { cos(radians) }
    val sin by lazy { sin(radians) }

    val degrees by lazy { radians.toDegrees() }

    /**
     * the calculated tan
     */
    val tan by lazy { sin / cos }

    init {
        val fPI = PI.toFloat()

        while (this.radians > fPI) this.radians -= 2 * fPI
        while (this.radians < -fPI) this.radians += 2 * fPI
    }

    /**
     * Rotate by another Rot2d and returns a new one
     * @param o the Rot2d to rotate by
     * @return Result Rot2d
     */
    fun rotate(o: Rotation2) = vector(
        cos * o.cos - o.sin * o.cos,
        cos * o.sin + o.sin * o.cos
    )

    operator fun plus(o: Rotation2) = rotate(o)

    operator fun minus(o: Rotation2) = rotate(o.invert())

    operator fun div(o: Rotation2) = Rotation2(radians / o.radians)

    operator fun times(o: Rotation2) = Rotation2(radians * o.radians)

    /**
     * Inverts the radians and returns a new Rot2d
     * @return Result Rot2d
     */
    fun invert() = Rotation2(-radians)

    override fun toString() = "Rotation2(rad ${radians}, deg ${degrees})"

}