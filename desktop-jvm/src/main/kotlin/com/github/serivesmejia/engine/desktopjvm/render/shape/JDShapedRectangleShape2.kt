package com.github.serivesmejia.engine.desktopjvm.render.shape

import com.github.serivesmejia.engine.desktopjvm.render.mesh.JDShapedMeshLoader
import com.github.serivesmejia.engine.desktopjvm.render.shader.JDShapedShader
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

    val mesh = JDShapedMeshLoader.createMesh(vertices, indices)

    override fun update() {

    }

    override fun draw() {
        if(shader is JDShapedShader) {
            shader?.bindAttribute("position", mesh.vao)
        }

        shader?.begin()

        glBindVertexArray(mesh.vao)

        glEnableVertexAttribArray(0)
        glDrawElements(GL_QUADS, mesh.vertex, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)

        shader?.end()
    }

    override fun clear() {

    }

}