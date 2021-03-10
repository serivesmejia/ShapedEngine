package com.github.serivesmejia.engine.desktopjvm.render

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.render.ShapedRenderer
import org.lwjgl.opengl.GL11.glClearColor

object JvmShapedDesktopRenderer : ShapedRenderer {

    override fun create(): JvmShapedDesktopRenderer {
        return this
    }

    override fun destroy(): JvmShapedDesktopRenderer {
        return this
    }


    override fun update(deltaTime: Float) {

    }

    override fun drawRectangle(rect: Rectangle2, color: Color4) {

    }

    override fun drawBackgroundColor(color: Color4) {
        val dcolor = color.toDecimal()
        glClearColor(dcolor.r, dcolor.g, dcolor.b, dcolor.a)
    }

}