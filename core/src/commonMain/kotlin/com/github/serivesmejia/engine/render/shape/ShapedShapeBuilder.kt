package com.github.serivesmejia.engine.render.shape

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Triangle2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.render.ShapedTexture

private val defColor = Color4(255f, 255f, 255f, 255f)

/**
 * Build platform-specific shapes from common code
 * to be added to platform-specific renderers
 */
abstract class ShapedShapeBuilder {

    /**
     * Creates a triangle shape
     *
     * @param triangle the triangle to create
     * @param color the triangle's render color
     *
     * @return the ShapedShape of the given triangle
     */
    abstract fun triangle(triangle: Triangle2, color: Color4 = defColor): ShapedShape

    /**
     * Creates a triangle shape
     *
     * @param position the position of the triangle to create
     * @param size the size of the triangle to create
     * @param color the triangle's render color
     *
     * @return the ShapedShape of the given triangle
     */
    fun triangle(position: Vector2, size: Size2, color: Color4 = defColor) =
        triangle(Triangle2(position, size), color)

    /**
     * Creates a textured triangle shape
     *
     * @param triangle the triangle to create
     * @param texture the texture for this shape
     * @param color the triangle's render color
     *
     * @return the textured ShapedShape of the given triangle
     */
    abstract fun texturedTriangle(triangle: Triangle2, texture: ShapedTexture, color: Color4 = defColor): ShapedShape

    /**
     * Creates a textured triangle shape
     *
     * @param position the position of the triangle to create
     * @param size the size of the triangle to create
     * @param texture the texture for this shape
     * @param color the triangle's render color
     *
     * @return the ShapedShape of the given triangle
     */
    fun texturedTriangle(position: Vector2, size: Size2, texture: ShapedTexture, color: Color4 = defColor) =
        texturedTriangle(Triangle2(position, size), texture, color)

    /**
     * Creates a rectangle shape
     *
     * @param rect the rectangle to create
     * @param color the rectangle's render color
     *
     * @return the ShapedShape of the given rectangle
     */
    abstract fun rectangle(rect: Rectangle2, color: Color4 = defColor): ShapedShape

    /**
     * Creates a rectangle shape
     *
     * @param position the position of the rectangle to create
     * @param size the size of the rectangle to create
     * @param color the rectangle's render color
     *
     * @return the ShapedShape of the given rectangle
     */
    fun rectangle(position: Vector2, size: Size2, color: Color4 = defColor) =
        rectangle(Rectangle2(position, size), color)


    /**
     * Creates a textured rectangle shape
     *
     * @param rect the rectangle to create
     * @param texture the texture for this shape
     * @param color the rectangle's render color
     *
     * @return the textured ShapedShape of the given rectangle
     */
    abstract fun texturedRectangle(rect: Rectangle2, texture: ShapedTexture, color: Color4 = defColor): ShapedShape

    /**
     * Creates a textured rectangle shape
     *
     * @param position the position of the rectangle to create
     * @param size the size of the rectangle to create
     * @param texture the texture for this shape
     * @param color the rectangle's render color
     *
     * @return the ShapedShape of the given rectangle
     */
    fun texturedRectangle(position: Vector2, size: Size2, texture: ShapedTexture, color: Color4 = defColor) =
        texturedRectangle(Rectangle2(position, size), texture, color)

    abstract fun circle(position: Vector2, radius: Float, color: Color4 = defColor): ShapedShape

    abstract fun line(from: Vector2, to: Vector2, thickness: Float, color: Color4 = defColor): ShapedShape

}