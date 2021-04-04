package com.github.serivesmejia.engine.render.opengl.mesh

import com.github.serivesmejia.engine.render.texture.ShapedTexture

interface ShapedMesh {
    fun draw(mode: Int, texture: ShapedTexture? = null)
}