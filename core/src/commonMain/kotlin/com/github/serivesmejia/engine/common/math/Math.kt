package com.github.serivesmejia.engine.common.math

import kotlin.math.PI

object Math {

    /**
     * Converts a number from degrees to radians
     */
    fun Number.toRadians() = toDouble() / 180.0 * PI

    /**
     * Converts a number from radians to degrees
     */
    fun Number.toDegrees() = toDouble() * 180.0 / PI

}