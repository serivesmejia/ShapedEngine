package com.github.serivesmejia.engine.render.texture

interface ShapedTextureLoader {
    fun loadTexture(resourcePath: String): ShapedTexture
}