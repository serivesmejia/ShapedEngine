package com.github.serivesmejia.engine.desktopjvm.render.shape

import com.github.serivesmejia.engine.desktopjvm.render.opengl.mesh.JDShapedMeshBuilder
import com.github.serivesmejia.engine.render.shape.ShapedShape2
import org.lwjgl.opengl.GL30.*

class JDShapedTriangleShape2 : ShapedShape2() {

    private var vertices = floatArrayOf(
        -0.5f, -0.5f, 0f,
        -0.5f, 0.5f, 0f,
        0.5f, 0.5f, 0f
    )

    private var indices = intArrayOf(0, 1, 2)

    val mesh = JDShapedMeshBuilder.createMesh(vertices, vertices, indices)

    override fun update() {

    }

    override fun draw() {
        shader?.begin()

        glBindVertexArray(mesh.vao)

        glEnableVertexAttribArray(0)
        glDrawElements(GL_TRIANGLES, mesh.vertex, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)

        glBindVertexArray(0)

        shader?.end()
    }

    override fun clear() {

    }

}