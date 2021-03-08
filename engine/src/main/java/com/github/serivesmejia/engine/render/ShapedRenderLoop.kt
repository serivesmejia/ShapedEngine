package com.github.serivesmejia.engine.render

import com.github.serivesmejia.engine.common.loop.ShapedLoop
import com.github.serivesmejia.engine.common.color
import com.github.serivesmejia.engine.render.desktop.ShapedWindow
import com.github.serivesmejia.engine.stage.ShapedStageManager
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11

class ShapedRenderLoop(private val window: ShapedWindow,
                       private val stageManager: ShapedStageManager) : ShapedLoop {

    private lateinit var thread: Thread

    override fun create(): ShapedRenderLoop {
        thread = Thread.currentThread()
        return this
    }

    override fun update(deltaTime: Float) {
        // Set the clear color
        GL11.glClearColor(40f.color, 127f.color, 82f.color, 1f)

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT) // clear the framebuffer

        GLFW.glfwSwapBuffers(window.ptr) // swap the color buffers
        // Poll for window events.
        GLFW.glfwPollEvents()

        stageManager.update(deltaTime)
    }

    override fun destroy(): ShapedRenderLoop {
        thread.interrupt()
        return this
    }

}