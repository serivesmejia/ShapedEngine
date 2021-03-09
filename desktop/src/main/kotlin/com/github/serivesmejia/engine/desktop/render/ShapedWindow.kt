package com.github.serivesmejia.engine.desktop.render

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.geometry.Size2
import com.github.serivesmejia.engine.common.geometry.Vector2
import com.github.serivesmejia.engine.common.modular.ShapedModule
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

class ShapedWindow(val title: String = "ShapedEngine",
                   val width: Int = 640,
                   val height: Int = 480,
                   val vsync: Boolean = true) : ShapedModule<ShapedEngine> {

    /**
     * Long native pointer of this window
     */
    var ptr = 0L
        private set

    val isVisible: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_VISIBLE) == GLFW_TRUE

    /**
     * Gets the current position of this window as a Vector2
     */
    val position: Vector2
        get() {
            val x = BufferUtils.createIntBuffer(1)
            val y = BufferUtils.createIntBuffer(1)
            glfwGetWindowPos(ptr, x, y)
            return Vector2(x.get(0).toFloat(), y.get(0).toFloat())
        }

    /**
     * Gets the current size of this window as a Size2
     */
    val size: Size2
        get() {
            val w = BufferUtils.createIntBuffer(1)
            val h = BufferUtils.createIntBuffer(1)
            glfwGetWindowSize(ptr, w, h)

            return Size2(
                w.get(0).toFloat(),
                h.get(0).toFloat()
            )
        }

    /**
     * Initializes glfw and creates this window
     */
    override fun create(): ShapedWindow {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw IllegalStateException("Unable to initialize GLFW")

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

        // Create the window
        ptr = glfwCreateWindow(width, height, title, NULL, NULL)
        if (ptr == NULL)
            throw RuntimeException("Failed to create the GLFW window")

        // Make the OpenGL context current
        glfwMakeContextCurrent(ptr)
        if(vsync) {
            // Enable v-sync
            glfwSwapInterval(1)
        }

        // Make the window visible
        glfwShowWindow(ptr)

        return this
    }

    override fun update(deltaTime: Float) {
        val cachedSize = this.size
        val cachedPos = this.position

        Shaped.Graphics.displayRect.position.set(cachedPos.x, cachedPos.y)
        Shaped.Graphics.displayRect.size.set(cachedSize.width, cachedSize.height)
    }

    /**
     * Shows this window
     */
    fun show(): ShapedWindow {
        glfwShowWindow(ptr)
        return this
    }

    /**
     * Hides this window
     */
    fun hide(): ShapedWindow {
        glfwHideWindow(ptr)
        return this
    }

    /**
     * Centers the window in the screen
     */
    fun center(): ShapedWindow {
        // Get the thread stack and push a new frame
        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(ptr, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            // Center the window
            glfwSetWindowPos(
                ptr,
                (vidMode!!.width() - pWidth[0]) / 2,
                (vidMode.height() - pHeight[0]) / 2
            )
        }

        return this
    }

    /**
     * Closes this window, terminates glfw.
     */
    override fun destroy(): ShapedWindow {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(ptr)
        glfwDestroyWindow(ptr)

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()

        return this
    }

}