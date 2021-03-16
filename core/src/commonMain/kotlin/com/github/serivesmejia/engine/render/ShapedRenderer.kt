package com.github.serivesmejia.engine.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.modular.ShapedModule
import com.github.serivesmejia.engine.render.opengl.mesh.ShapedMeshBuilder
import com.github.serivesmejia.engine.render.opengl.shader.ShapedShaderLoader
import com.github.serivesmejia.engine.render.shape.ShapedShape2
import com.github.serivesmejia.engine.render.shape.ShapedShapeBuilder
import com.github.serivesmejia.engine.render.texture.ShapedTextureLoader

/**
 * Abstract class for handling multiplatform rendering
 * Handles shape rendering, textures, background color...
 * @see ShapedShape2
 * @see ShapedShapeBuilder
 */
abstract class ShapedRenderer : ShapedModule<ShapedEngine> {

    /**
     * The ShapedMeshBuilder to be used for the platform
     * @see ShapedMeshBuilder
     */
    abstract val meshBuilder: ShapedMeshBuilder

    /**
     * The ShapedShapeBuilder to be used for the platform
     * @see ShapedShapeBuilder
     */
    abstract val shapeBuilder: ShapedShapeBuilder

    /**
     * The ShapedShaderLoader to be used for the platform
     * @see ShapedShaderLoader
     */
    abstract val shaderLoader: ShapedShaderLoader

    /**
     * The ShapedTextureLoader to be used for the platform
     * @see ShapedTextureLoader
     */
    abstract val textureLoader: ShapedTextureLoader

    /**
     * Get or set the currently rendering background color
     */
    abstract var backgroundColor: Color4

    /**
     * Get all the shapes currently on this
     * renderer as an independent typed array
     */
    val shapes get() = internalShapes.toTypedArray()

    private val internalShapes = mutableListOf<ShapedShape2>()

    /**
     * Adds a shape to this renderer
     * @param shape the shape to add to this renderer
     */
    open fun addShape(shape: ShapedShape2) {
        if(!internalShapes.contains(shape)) {
            internalShapes.add(shape)
        }
    }

    /**
     * Removes a shape from this renderer and clears it
     * @param shape the shape to remove from this renderer and clear
     */
    open fun removeShape(shape: ShapedShape2) {
        if(internalShapes.contains(shape)) {
            shape.clear()
            internalShapes.remove(shape)
        }
    }

    /**
     * Draws all the current shapes of this ShapedRender
     */
    fun drawAll() {
        for(shape in shapes) {
            shape.internalDraw()
        }
    }

    /**
     * Clears this renderer by removing all shapes
     * and calling clear() on each one of them
     */
    fun clear() {
        for(shape in internalShapes.toTypedArray()) {
            removeShape(shape)
        }
    }

}