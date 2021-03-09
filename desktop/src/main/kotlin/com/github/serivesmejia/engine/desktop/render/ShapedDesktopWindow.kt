package com.github.serivesmejia.engine.desktop.render

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.geometry.Rectangle2
import com.github.serivesmejia.engine.common.geometry.Size2
import com.github.serivesmejia.engine.common.geometry.Vector2
import com.github.serivesmejia.engine.render.ShapedWindow
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

/**
 * Create a window using the desktop GLFW API
 * @property initialTitle the initial title of this window
 * @property initialSize the initial size of this window
 * @property vsync whether this window will use vertical synchronization or not.
 */
class ShapedDesktopWindow(initialTitle: String = "ShapedEngine",
                          private val initialSize: Size2 = Size2(640f, 480f),
                          val vsync: Boolean = true) : ShapedWindow {

    /**
     * Long native pointer of this window
     */
    var ptr = 0L
        private set

    /**
     * Get whether this window is currently visible
     * Use show() or hide() functions to update visibility
     */
    override val isVisible: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_VISIBLE) == GLFW_TRUE

    /**
     * Get whether this window is currently focused
     */
    override val isFocused: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_FOCUSED) == GLFW_TRUE

    /**
     * Get or set this window title
     *
     * Note that when setting the window title
     * manually with the glfw function, there's
     * no way to retrieve the current title, so
     * any independent change won't be reflected
     * on the value of this variable.
     */
    override var title: String = initialTitle
        set(value) {
            glfwSetWindowTitle(ptr, value)
            field = value
        }

    override val windowRectangle: Rectangle2
        get() = Rectangle2(position, size)

    /**
     * Gets the current position of this window as a Vector2
     */
    override var position: Vector2
        set(value) {
            glfwSetWindowPos(ptr, value.x.toInt(), value.y.toInt())
        }
        get() {
            val x = BufferUtils.createIntBuffer(1)
            val y = BufferUtils.createIntBuffer(1)
            glfwGetWindowPos(ptr, x, y)
            return Vector2(x.get(0).toFloat(), y.get(0).toFloat())
        }

    /**
     * Gets the current size of this window as a Size2
     */
    override var size: Size2
        set(value) {
            glfwSetWindowSize(ptr, value.width.toInt(), value.height.toInt())
        }
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
    override fun create(): ShapedDesktopWindow {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw IllegalStateException("Unable to initialize GLFW")

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

        // Create the window
        ptr = glfwCreateWindow(
            initialSize.width.toInt(),
            initialSize.height.toInt(),
            title, NULL, NULL
        )

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

    override fun update(deltaTime: Float) { }

    /**
     * Shows this window
     */
    override fun show(): ShapedDesktopWindow {
        glfwShowWindow(ptr)
        return this
    }

    /**
     * Hides this window
     */
    override fun hide(): ShapedDesktopWindow {
        glfwHideWindow(ptr)
        return this
    }

    /**
     * Centers the window in the screen
     */
    override fun center(): ShapedDesktopWindow {
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
    override fun destroy(): ShapedDesktopWindow {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(ptr)
        glfwDestroyWindow(ptr)

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()

        return this
    }

}