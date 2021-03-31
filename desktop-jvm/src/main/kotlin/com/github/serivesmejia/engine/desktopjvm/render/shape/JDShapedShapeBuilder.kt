package com.github.serivesmejia.engine.desktopjvm.render.shape

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.common.math.geometry.Triangle2
import com.github.serivesmejia.engine.common.math.geometry.position.Vector2
import com.github.serivesmejia.engine.render.opengl.shader.ShapedShader
import com.github.serivesmejia.engine.render.texture.ShapedTexture
import com.github.serivesmejia.engine.render.shape.ShapedShape2
import com.github.serivesmejia.engine.render.shape.ShapedShapeBuilder

object JDShapedShapeBuilder : ShapedShapeBuilder() {

    override fun triangle(triangle: Triangle2, color: Color4, shader: ShapedShader): ShapedShape2 {
        val shape = JDShapedTriangleShape2()

        shape.position = triangle.position
        shape.size     = triangle.size
        shape.color    = color
        shape.shader   = shader

        return shape
    }

    override fun texturedTriangle(triangle: Triangle2, texture: ShapedTexture, color: Color4, shader: ShapedShader): ShapedShape2 {
        TODO("Not yet implemented")
    }

    override fun rectangle(rect: Rectangle2, color: Color4, shader: ShapedShader): ShapedShape2 {
        val shape = JDShapedRectangleShape2()

        shape.position = rect.position
        shape.size     = rect.size
        shape.color    = color
        shape.shader   = shader

        return shape
    }

    override fun texturedRectangle(rect: Rectangle2, texture: ShapedTexture, color: Color4, shader: ShapedShader): ShapedShape2 {
        val rect = rectangle(rect, color, shader)
        rect.texture = texture

        return rect
    }

    override fun circle(position: Vector2, radius: Float, color: Color4): ShapedShape2 {
        TODO("Not yet implemented")
    }

    override fun line(from: Vector2, to: Vector2, thickness: Float, color: Color4): ShapedShape2 {
        TODO("Not yet implemented")
    }

}