package com.github.serivesmejia.engine.common.math

import kotlin.math.PI
import kotlin.math.sqrt

/**
 * Converts a number from degrees to radians
 */
fun Number.toRadians() = toDouble() / 180.0 * PI

/**
 * Converts a number from radians to degrees
 */
fun Number.toDegrees() = toDouble() * 180.0 / PI

fun Float.invSqrt() = 1.0f / sqrt(this)

val HALF_PI = PI * 0.5