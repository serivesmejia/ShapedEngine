package com.github.serivesmejia.engine.desktopjvm

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.modular.ShapedModule
import com.github.serivesmejia.engine.desktopjvm.event.wrapper.JvmGlfwEventWrapper
import com.github.serivesmejia.engine.desktopjvm.render.JvmShapedDesktopWindow
import com.github.serivesmejia.engine.jvm.event.JvmShapedEventSubscriber
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class JvmShapedDesktopLoop(private val engine: ShapedEngine,
                           private val window: JvmShapedDesktopWindow
                           ) : ShapedModule<ShapedEngine> {

    override fun create(): JvmShapedDesktopLoop {
        //wrap to make @Subscribe mechanism available for use (JVM)
        engine.stageManager.eventBus.addSubscriber(JvmShapedEventSubscriber())
        //wrap glfw callbacks with shaped events
        engine.stageManager.eventBus.wrap(JvmGlfwEventWrapper(window))

        GL.createCapabilities()

        return this
    }

    override fun update(deltaTime: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        glfwSwapBuffers(window.ptr)
        glfwPollEvents()
    }

    override fun destroy(): JvmShapedDesktopLoop {
        return this
    }

}