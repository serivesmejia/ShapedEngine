package com.github.serivesmejia.engine.desktopjvm.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.desktopjvm.event.wrapper.JDGlfwEventWrapper
import com.github.serivesmejia.engine.desktopjvm.render.shape.JDShapedShapeBuilder
import com.github.serivesmejia.engine.desktopjvm.render.shape.JDShapedTriangle2Shape
import com.github.serivesmejia.engine.jvm.event.JvmShapedEventSubscriber
import com.github.serivesmejia.engine.render.ShapedRenderer
import com.github.serivesmejia.engine.render.shape.ShapedShapeBuilder
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11.*

class JDShapedRenderer(private val engine: ShapedEngine,
                       private val window: JDShapedWindow) : ShapedRenderer() {

    override val shapeBuilder: ShapedShapeBuilder = JDShapedShapeBuilder

    override var backgroundColor: Color4 = Color4(0f, 0f, 0f)

    /**
     * Creates this renderer
     * Performs more platform-specific stuff
     * such as registering the jvm subscriber
     * in the eventBus of the stageManager
     *
     * This function shouldn't be manually called
     */
    override fun create(): JDShapedRenderer {
        //wrap to make @Subscribe mechanism available for use (JVM)
        engine.stageManager.eventBus.addSubscriber(JvmShapedEventSubscriber())
        //wrap glfw callbacks with shaped events
        engine.stageManager.eventBus.wrap(JDGlfwEventWrapper(window))

        val triangle = JDShapedTriangle2Shape(
            Vector2(0f, 0f),
            Size2(0f, 0f),
            Color4(255f, 255f, 255f),
            null
        )

        addShape(triangle)

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
        val decBgColor = backgroundColor.toDecimal()
        glClearColor(decBgColor.r, decBgColor.g, decBgColor.b, decBgColor.a) //set gl clear color

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        drawAll()

        GLFW.glfwSwapBuffers(window.ptr)
        GLFW.glfwPollEvents()
    }

    /**
     * Destroys this renderer
     */
    override fun destroy(): JDShapedRenderer {
        return this
    }

}