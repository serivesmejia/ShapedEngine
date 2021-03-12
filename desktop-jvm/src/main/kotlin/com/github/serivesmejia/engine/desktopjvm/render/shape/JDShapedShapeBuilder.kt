package com.github.serivesmejia.engine.desktopjvm.render.shape

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.common.math.geometry.Triangle2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.render.ShapedTexture
import com.github.serivesmejia.engine.render.shape.ShapedShape
import com.github.serivesmejia.engine.render.shape.ShapedShapeBuilder

object JDShapedShapeBuilder : ShapedShapeBuilder() {

    override fun triangle(triangle: Triangle2, color: Color4): ShapedShape {
        TODO("Not yet implemented")
    }

    override fun texturedTriangle(triangle: Triangle2, texture: ShapedTexture, color: Color4): ShapedShape {
        TODO("Not yet implemented")
    }

    override fun rectangle(rect: Rectangle2, color: Color4): ShapedShape {
        TODO("Not yet implemented")
    }

    override fun texturedRectangle(rect: Rectangle2, texture: ShapedTexture, color: Color4): ShapedShape {
        TODO("Not yet implemented")
    }

    override fun circle(position: Vector2, radius: Float, color: Color4): ShapedShape {
        TODO("Not yet implemented")
    }

    override fun line(from: Vector2, to: Vector2, thickness: Float, color: Color4): ShapedShape {
        TODO("Not yet implemented")
    }

}