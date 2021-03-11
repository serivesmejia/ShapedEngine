package com.github.serivesmejia.engine.desktopjvm.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.desktopjvm.event.wrapper.JvmGlfwEventWrapper
import com.github.serivesmejia.engine.desktopjvm.render.shape.JvmShapedDesktopShapeBuilder
import com.github.serivesmejia.engine.jvm.event.JvmShapedEventSubscriber
import com.github.serivesmejia.engine.render.ShapedRenderer
import com.github.serivesmejia.engine.render.shape.ShapedShapeBuilder
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class JvmShapedDesktopRenderer(private val engine: ShapedEngine,
                               private val window: JvmShapedDesktopWindow) : ShapedRenderer() {

    override val shapeBuilder: ShapedShapeBuilder = JvmShapedDesktopShapeBuilder

    override var backgroundColor: Color4 = Color4(0f, 0f, 0f)
        set(value) {
            val decColor = value.toDecimal() //convert color to decimal values
            glClearColor(decColor.r, decColor.g, decColor.b, decColor.a) //set gl clear color

            field = value //set backing field to the new color value
        }

    /**
     * Creates this renderer
     * Performs more platform-specific stuff
     * such as registering the jvm subscriber
     * in the eventBus of the stageManager
     *
     * This function shouldn't be manually called
     */
    override fun create(): JvmShapedDesktopRenderer {
        //wrap to make @Subscribe mechanism available for use (JVM)
        engine.stageManager.eventBus.addSubscriber(JvmShapedEventSubscriber())
        //wrap glfw callbacks with shaped events
        engine.stageManager.eventBus.wrap(JvmGlfwEventWrapper(window))

        return this
    }

    /**
     * Updates this renderer
     *
     * This function shouldn't be manually called
     *
     * @param deltaTime the difference in seconds between the last and current frame
     */
    override fun update(deltaTime: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        GLFW.glfwSwapBuffers(window.ptr)
        GLFW.glfwPollEvents()
    }

    /**
     * Destroys this renderer
     */
    override fun destroy(): JvmShapedDesktopRenderer {
        return this
    }

}