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