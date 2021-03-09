package com.github.serivesmejia.engine.desktop.event.wrapper

import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.common.event.standard.*
import com.github.serivesmejia.engine.common.event.wrapper.ShapedEventWrapper
import com.github.serivesmejia.engine.common.geometry.Size2
import com.github.serivesmejia.engine.common.geometry.Vector2
import com.github.serivesmejia.engine.desktop.render.ShapedDesktopWindow
import org.lwjgl.glfw.GLFW.*

/**
 * Wraps the GLFW callback system to the ShapedEventBus architecture
 * @param window the current active ShapedWindow
 */
class GlfwEventWrapper(private val window: ShapedDesktopWindow) : ShapedEventWrapper {

    override fun wrap(eventBus: ShapedEventBus) {
        //wrap for resize events
        glfwSetWindowSizeCallback(window.ptr) { _: Long, width: Int, height: Int ->
            eventBus.fire(
                WindowResizeEvent(Size2(width.toFloat(), height.toFloat()))
            )
        }

        //wrap for window move events
        glfwSetWindowPosCallback(window.ptr) { _: Long, x: Int, y: Int ->
            eventBus.fire(
                WindowMoveEvent(Vector2(x.toFloat(), y.toFloat()))
            )
        }
    }

}