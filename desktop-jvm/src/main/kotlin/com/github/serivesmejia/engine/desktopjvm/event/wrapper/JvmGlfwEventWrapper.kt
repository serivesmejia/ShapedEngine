package com.github.serivesmejia.engine.desktopjvm.event.wrapper

import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.common.event.standard.*
import com.github.serivesmejia.engine.common.event.wrapper.ShapedEventWrapper
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.desktopjvm.render.JvmShapedDesktopWindow
import org.lwjgl.glfw.GLFW.*

/**
 * Wraps the GLFW callback system to the ShapedEventBus architecture
 * @param window the current active ShapedWindow
 */
class JvmGlfwEventWrapper(private val window: JvmShapedDesktopWindow) : ShapedEventWrapper {

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

    override fun unwrap(eventBus: ShapedEventBus) {}

}