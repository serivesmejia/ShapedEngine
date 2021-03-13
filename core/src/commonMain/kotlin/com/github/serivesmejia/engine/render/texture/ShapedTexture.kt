package com.github.serivesmejia.engine.render.texture

import com.github.serivesmejia.engine.common.math.geometry.Size2

abstract class ShapedTexture(val id: Int, val size: Size2) {
    abstract fun bind()
    abstract fun unbind()
}