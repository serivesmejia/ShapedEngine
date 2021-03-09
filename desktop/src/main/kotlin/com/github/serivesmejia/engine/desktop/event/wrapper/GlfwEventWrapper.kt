package com.github.serivesmejia.engine.desktop.event.wrapper

import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.common.event.general.*
import com.github.serivesmejia.engine.common.event.wrapper.ShapedEventWrapper
import com.github.serivesmejia.engine.common.geometry.Size2
import com.github.serivesmejia.engine.common.geometry.Vector2
import com.github.serivesmejia.engine.desktop.render.ShapedWindow
import org.lwjgl.glfw.GLFW.*

/**
 * Wraps the GLFW callback system to the ShapedEventBus architecture
 * @param window the current active ShapedWindow
 */
class GlfwEventWrapper(private val window: ShapedWindow) : ShapedEventWrapper {

    override fun wrap(eventBus: ShapedEventBus) {
        glfwSetWindowSizeCallback(window.ptr) { window: Long, width: Int, height: Int ->
            eventBus.fire(
                WindowResizeEvent(
                    window,
                    Size2(width.toFloat(), height.toFloat())
                )
            )
        }

        glfwSetWindowPosCallback(window.ptr) { window: Long, x: Int, y: Int ->
            eventBus.fire(
                WindowMoveEvent(
                    window,
                    Vector2(x.toFloat(), y.toFloat())
                )
            )
        }
    }

}