package com.github.serivesmejia.engine.common.extension

/**
 * Converts colors in 0-255 range to
 * OpenGL's 0-1
 */
val Float.color
    get() = this / 255f

/**
 * Converts colors in 0-255 range to
 * OpenGL's 0-1
 */
val Double.color
    get() = this / 255

/**
 * Converts colors in 0-255 range to
 * OpenGL's 0-1
 */
val Int.color
    get() = this / 255

/**
 * Clips an int to 0 if its value is under 0
 */
val Int.clipUpperZero get() = if(this > 0) this else 0

/**
 * Converts an non-zero based Int index to zero based
 * (e.g a Array.size()), while assuring that the value
 * stays higher than zero to avoid out of bounds exceptions.
 */
val Int.zeroBased get() = (this - 1).clipUpperZero