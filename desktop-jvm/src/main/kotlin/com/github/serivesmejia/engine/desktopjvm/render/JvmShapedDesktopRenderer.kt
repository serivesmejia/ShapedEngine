package com.github.serivesmejia.engine.desktopjvm.render

import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.render.ShapedRenderer
import org.lwjgl.opengl.GL11.*

object JvmShapedDesktopRenderer : ShapedRenderer {

    override fun create(): JvmShapedDesktopRenderer {
        return this
    }

    override fun update(deltaTime: Float) { }

    override fun destroy(): JvmShapedDesktopRenderer {
        return this
    }

    override fun drawRectangle(rect: Rectangle2, color: Color4) {
        val x = rect.position.x
        val y = rect.position.y

        val width = rect.size.width
        val height = rect.size.height

        println("draw $rect")

        glColor3f(0.5f, 0.5f, 0.5f)

        glBegin(GL_QUADS)

        glVertex2f(-x, y)
        glVertex2f(width, y)
        glVertex2f(width, -height)
        glVertex2f(-x, -height)

        glEnd()
    }

    override fun drawBackgroundColor(color: Color4) {
        val decColor = color.toDecimal()
        glClearColor(decColor.r, decColor.g, decColor.b, decColor.a)
    }

}