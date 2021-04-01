package com.github.serivesmejia.engine.common.math

import kotlin.math.PI
import kotlin.math.sqrt

/**
 * Converts a Double from degrees to radians
 */
fun Double.toRadians() = this / 180.0 * PI

/**
 * Converts a Double from radians to degrees
 */
fun Double.toDegrees() = this * 180.0 / PI

/**
 * Converts a Float from degrees to radians
 */
fun Float.toRadians() = this.toDouble().toRadians().toFloat()

/**
 * Converts a Float from radians to degrees
 */
fun Float.toDegrees() = this.toDouble().toDegrees().toFloat()

fun Float.invSqrt() = 1.0f / sqrt(this)

val HALF_PI = PI * 0.5