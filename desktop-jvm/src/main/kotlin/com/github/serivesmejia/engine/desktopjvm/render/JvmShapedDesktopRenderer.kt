package com.github.serivesmejia.engine.desktopjvm.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.desktopjvm.event.wrapper.JvmGlfwEventWrapper
import com.github.serivesmejia.engine.jvm.event.JvmShapedEventSubscriber
import com.github.serivesmejia.engine.render.ShapedRenderer
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class JvmShapedDesktopRenderer(private val engine: ShapedEngine,
                               private val window: JvmShapedDesktopWindow) : ShapedRenderer {

    /**
     *
     */
    override fun create(): JvmShapedDesktopRenderer {
        //wrap to make @Subscribe mechanism available for use (JVM)
        engine.stageManager.eventBus.addSubscriber(JvmShapedEventSubscriber())
        //wrap glfw callbacks with shaped events
        engine.stageManager.eventBus.wrap(JvmGlfwEventWrapper(window))

        GL.createCapabilities()

        return this
    }

    override fun update(deltaTime: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        GLFW.glfwSwapBuffers(window.ptr)
        GLFW.glfwPollEvents()
    }

    override fun destroy(): JvmShapedDesktopRenderer {
        return this
    }

    override fun drawRectangle(rect: Rectangle2, color: Color4) {

    }

    override fun drawBackgroundColor(color: Color4) {
        val decColor = color.toDecimal()
        glClearColor(decColor.r, decColor.g, decColor.b, decColor.a)
    }

}