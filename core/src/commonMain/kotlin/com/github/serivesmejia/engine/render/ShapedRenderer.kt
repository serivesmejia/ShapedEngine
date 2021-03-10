package com.github.serivesmejia.engine.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.common.modular.ShapedModule

interface ShapedRenderer : ShapedModule<ShapedEngine> {

    fun drawBackgroundColor(color: Color4)

    fun drawRectangle(rect: Rectangle2, color: Color4 = Color4(255f, 255f, 255f, 0f))

}

object PlaceholderShapedRenderer : ShapedRenderer {

    override fun drawBackgroundColor(color: Color4) {}
    override fun drawRectangle(rect: Rectangle2, color: Color4) { }

    override fun update(deltaTime: Float) {}
    override fun create() = this
    override fun destroy() = this
}