package com.github.serivesmejia.engine.desktopjvm.render.opengl.mesh

import com.github.serivesmejia.engine.render.opengl.mesh.ShapedMesh
import com.github.serivesmejia.engine.render.texture.ShapedTexture
import org.lwjgl.opengl.GL30.*

data class JDShapedMesh(val vao: Int, val vertex: Int): ShapedMesh {

    override fun draw(mode: Int, texture: ShapedTexture?) {
        glBindVertexArray(vao)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        texture?.bind()
        glDrawElements(mode, vertex, GL_UNSIGNED_INT, 0)
        texture?.unbind()

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)

        glBindVertexArray(0)
    }

}