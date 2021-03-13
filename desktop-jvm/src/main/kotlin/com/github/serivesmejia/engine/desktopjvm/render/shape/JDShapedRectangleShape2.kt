package com.github.serivesmejia.engine.desktopjvm.render.shape

import com.github.serivesmejia.engine.desktopjvm.render.opengl.mesh.JDShapedMeshBuilder
import com.github.serivesmejia.engine.desktopjvm.render.opengl.shader.JDShapedShader
import com.github.serivesmejia.engine.render.shape.ShapedShape2
import org.lwjgl.opengl.GL30.*

class JDShapedRectangleShape2 : ShapedShape2() {

    private var vertices = floatArrayOf(
        -0.25f, -0.25f, 0f,
        -0.25f, 0.25f, 0f,
        0.25f, 0.25f, 0f,
        0.25f, -0.25f, 0f
    )

    private var indices = intArrayOf(0, 1, 2, 3)

    val mesh = JDShapedMeshBuilder.createMesh(vertices, vertices, indices)

    override fun update() {

    }

    override fun draw() {
        if(shader is JDShapedShader) {
            shader?.bindAttribute("position", 0)
            shader?.bindAttribute("textureCoords", 1)
        }

        shader?.begin()
        texture?.bind()

        mesh.draw(GL_QUADS)

        texture?.unbind()
        shader?.end()
    }

    override fun clear() {

    }

}