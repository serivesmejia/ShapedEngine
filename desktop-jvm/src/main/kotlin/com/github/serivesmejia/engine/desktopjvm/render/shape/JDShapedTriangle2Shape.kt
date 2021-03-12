package com.github.serivesmejia.engine.desktopjvm.render.shape

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.desktopjvm.render.mesh.JDShapedMeshLoader
import com.github.serivesmejia.engine.render.ShapedTexture
import com.github.serivesmejia.engine.render.shape.ShapedShape
import org.lwjgl.opengl.GL30.*

class JDShapedTriangle2Shape(
    override var position: Vector2,
    override var size: Size2,
    override var color: Color4,
    override var texture: ShapedTexture?
) : ShapedShape {

    private var vertices = floatArrayOf(
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0f, 0.5f, 0f
    )

    private var indices = intArrayOf(0, 1, 2)

    val mesh = JDShapedMeshLoader.createMesh(vertices, indices)

    override fun draw() {
        glBindVertexArray(mesh.vao)

        glEnableVertexAttribArray(0)
        glDrawElements(GL_TRIANGLES, mesh.vertex, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }

    override fun clear() {

    }

}