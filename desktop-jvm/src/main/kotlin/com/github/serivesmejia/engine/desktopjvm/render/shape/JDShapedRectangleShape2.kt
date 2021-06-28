package com.github.serivesmejia.engine.desktopjvm.render.shape

import com.github.serivesmejia.engine.desktopjvm.render.opengl.mesh.JDShapedMeshBuilder
import com.github.serivesmejia.engine.desktopjvm.render.opengl.shader.JDShapedShader
import com.github.serivesmejia.engine.render.shape.ShapedShape2
import org.lwjgl.opengl.GL30.*

class JDShapedRectangleShape2 : ShapedShape2() {

    private var vertices = floatArrayOf(
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        -0.5f, 0.5f, 0f,
        0.5f, 0.5f, 0f
    )

    private var uvs = floatArrayOf(
        0f, 1f,
        1f, 1f,
        0f, 0f,
        1f, 0f
    )

    private var indices = intArrayOf(
        0, 1, 2,
        1, 2, 3
    )

    val mesh = JDShapedMeshBuilder.createMesh(vertices, uvs, indices)

    override fun update() { }

    override fun draw() {
        shader?.begin()
        texture?.bind()
        mesh.draw(GL_TRIANGLES)
        texture?.unbind()
        shader?.end()
    }

    override fun clear() {

    }

}