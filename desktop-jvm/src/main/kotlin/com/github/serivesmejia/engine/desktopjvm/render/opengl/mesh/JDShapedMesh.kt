package com.github.serivesmejia.engine.desktopjvm.render.opengl.mesh

import com.github.serivesmejia.engine.render.opengl.mesh.ShapedMesh
import org.lwjgl.opengl.GL30.*

data class JDShapedMesh(val vao: Int, val vertex: Int): ShapedMesh {

    override fun draw(mode: Int) {
        glBindVertexArray(vao)

        glEnableVertexAttribArray(0)
        glDrawElements(GL_QUADS, vertex, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)

        glBindVertexArray(0)
    }

}