package com.github.serivesmejia.engine.desktopjvm.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.event.standard.WindowResizeEvent
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.desktopjvm.event.wrapper.JDGlfwEventWrapper
import com.github.serivesmejia.engine.desktopjvm.render.opengl.mesh.JDShapedMeshBuilder
import com.github.serivesmejia.engine.desktopjvm.render.opengl.shader.JDShapedShaderLoader
import com.github.serivesmejia.engine.desktopjvm.render.opengl.texture.JDShapedTextureLoader
import com.github.serivesmejia.engine.desktopjvm.render.shape.JDShapedShapeBuilder
import com.github.serivesmejia.engine.jvm.event.JvmShapedEventSubscriber
import com.github.serivesmejia.engine.render.ShapedRenderer
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11.*

class JDShapedRenderer(private val engine: ShapedEngine,
                       private val window: JDShapedWindow) : ShapedRenderer() {

    override val meshBuilder  = JDShapedMeshBuilder
    override val shapeBuilder = JDShapedShapeBuilder

    override val shaderLoader  = JDShapedShaderLoader
    override val textureLoader = JDShapedTextureLoader

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

        engine.stageManager.eventBus.on<WindowResizeEvent> {
            updateViewport()
        }

        updateViewport()

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

    private fun updateViewport() {
        glViewport(0, 0, window.size.width.toInt(), window.size.height.toInt())
    }

}