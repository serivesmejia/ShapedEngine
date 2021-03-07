package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.ShapedComponent
import com.github.serivesmejia.engine.common.color
import com.github.serivesmejia.engine.render.desktop.ShapedWindow
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class ShapedEngine : ShapedComponent {

    lateinit var window: ShapedWindow

    /**
     * Initializes and runs the engine, blocking.
     * Exits until the glfw window is closed
     */
    override fun create(): ShapedEngine {
        window = ShapedWindow().create().center()

        GL.createCapabilities()
        loop()

        return this
    }

    private fun loop() {
        // Set the clear color
        glClearColor(40f.color, 127f.color, 0.0f, 1f);

        while(!glfwWindowShouldClose(window.ptr)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear the framebuffer

            glfwSwapBuffers(window.ptr) // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents()
        }

        destroy()
    }

    override fun destroy(): ShapedEngine {
        window.destroy()
        return this
    }

}