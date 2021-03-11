package com.github.serivesmejia.engine.render.shape

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.common.math.geometry.Triangle2
import com.github.serivesmejia.engine.render.ShapedTexture

private val defColor = Color4(255f, 255f, 255f, 255f)

interface ShapedShapeBuilder {

    fun triangle(triangle: Triangle2, color: Color4 = defColor): ShapedShape

    fun texturedTriangle(triangle: Triangle2, texture: ShapedTexture, color: Color4 = defColor): ShapedShape

    fun rectangle(rect: Rectangle2, color: Color4 = defColor): ShapedShape

    fun texturedRectangle(rect: Rectangle2, texture: ShapedTexture, color: Color4 = defColor): ShapedShape

    fun circle(rect: Rectangle2, radius: Float, color4: Color4 = defColor): ShapedShape

}