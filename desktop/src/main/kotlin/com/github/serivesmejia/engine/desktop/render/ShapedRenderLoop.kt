package com.github.serivesmejia.engine.desktop.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.extension.color
import com.github.serivesmejia.engine.common.modular.ShapedModule
import com.github.serivesmejia.engine.desktop.event.wrapper.GlfwEventWrapper
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11

class ShapedRenderLoop(private val engine: ShapedEngine,
                       private val window: ShapedDesktopWindow) : ShapedModule<ShapedEngine> {

    override fun create(): ShapedRenderLoop {
        println("create render loop")

        engine.stageManager.eventBus.wrap(GlfwEventWrapper(window))
        GL.createCapabilities()

        return this
    }

    override fun update(deltaTime: Float) {
        GL11.glClearColor(40f.color, 127f.color, 82f.color, 1f)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

        glfwSwapBuffers(window.ptr)
        glfwPollEvents()
    }

    override fun destroy(): ShapedRenderLoop {
        return this
    }

}