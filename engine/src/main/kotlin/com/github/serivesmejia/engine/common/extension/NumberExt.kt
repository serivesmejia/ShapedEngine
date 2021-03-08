package com.github.serivesmejia.engine.common

/**
 * Converts colors in 0-255 range to
 * OpenGL's 0-1
 */
val Float.color
    get() = this / 255f