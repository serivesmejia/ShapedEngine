package com.github.serivesmejia.engine.common.math

import com.github.serivesmejia.engine.common.extension.color

data class Color4(val r: Float, val g: Float, val b: Float, val a: Float = 255f) {
    fun toDecimal() = Color4(r.color, g.color, b.color, a.color)
}