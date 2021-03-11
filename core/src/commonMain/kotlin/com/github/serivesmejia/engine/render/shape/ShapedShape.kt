package com.github.serivesmejia.engine.render.shape

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.render.ShapedTexture

interface ShapedShape {
    var position: Vector2
    var size: Size2

    val color: Color4
    var texture: ShapedTexture

    fun draw()
    fun clear()
}