package com.github.serivesmejia.engine.render.shape

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.render.shader.ShapedShader
import com.github.serivesmejia.engine.render.texture.ShapedTexture

/**
 * Represents any sort of drawable shape,
 * using platform-specific implementations.
 *
 * @see ShapedShapeBuilder
 */
abstract class ShapedShape2 {

    /**
     * The 2d position of this shape
     */
    var position = Vector2(0f, 0f)
        set(value) {
            if(hasBeenDrawnOnce) update()  //call update whenever this shape is updated
            field = value
        }

    /**
     * The 2d size of this shape
     */
    var size = Size2(0f, 0f)
        set(value) {
            if(hasBeenDrawnOnce) update() //call update whenever this shape is updated
            field = value
        }

    /**
     * The render color of this shape
     */
    var color = Color4(255f, 255f, 255f)
        set(value) {
            if(hasBeenDrawnOnce) update() //call update whenever this shape is updated
            field = value
        }

    /**
     * The render texture of this shape
     * null means no texture, therefore
     * the color is the one used to
     * render the shape in this case
     */
    var texture: ShapedTexture? = null
        set(value) {
            if(hasBeenDrawnOnce) update() //call update whenever this shape is updated
            field = value
        }

    /**
     * The shader to be used for this shape
     * If null, nothing will be applied.
     */
    var shader: ShapedShader? = null

    private var hasBeenDrawnOnce = false

    /**
     * Called when any property is updated, used
     * to perform any operations that require
     * updating the geometry of this shape
     */
    abstract fun update()

    internal fun internalDraw() {
        hasBeenDrawnOnce = true
        draw()
    }

    /**
     * Called every frame by a renderer
     * to perform drawing operations
     */
    abstract fun draw()

    /**
     * Called when this shaped needs
     * to be cleared. e.g deleted
     * from a renderer
     */
    abstract fun clear()
}