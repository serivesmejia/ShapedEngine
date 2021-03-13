package com.github.serivesmejia.engine.desktopjvm.render.opengl.texture

import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.render.texture.ShapedTexture
import org.lwjgl.opengl.GL13.*

class JDShapedTexture(id: Int, size: Size2) : ShapedTexture(id, size) {

    override fun bind() {
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, id)
    }

    override fun unbind() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }

}