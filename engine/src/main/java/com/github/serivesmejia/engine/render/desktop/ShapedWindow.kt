package com.github.serivesmejia.engine.render.desktop

import com.github.serivesmejia.engine.common.HierarchyShapedComponent
import com.github.serivesmejia.engine.common.ShapedContainer
import com.github.serivesmejia.engine.common.extension.cantHaveParentG
import com.github.serivesmejia.engine.common.extension.cantHaveParentS
import org.lwjgl.glfw.*
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.*

class ShapedWindow(val title: String = "ShapedEngine",
                   val width: Int = 640,
                   val height: Int = 480,
                   val vsync: Boolean = true) : HierarchyShapedComponent<ShapedWindow> {

    override var parent: ShapedContainer<ShapedWindow>?
        get() = cantHaveParentG()
        set(value) = cantHaveParentS()

    /**
     * Long native pointer of this window
     */
    var ptr = 0L
        private set
    
    val isVisible: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_VISIBLE) == GLFW_TRUE

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
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            // Center the window
            glfwSetWindowPos(
                ptr,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
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