package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.render.ShapedRenderer
import com.github.serivesmejia.engine.render.ShapedWindow
import com.github.serivesmejia.engine.render.opengl.shader.DefaultFragmentShader
import com.github.serivesmejia.engine.render.opengl.shader.DefaultVertexShader
import com.github.serivesmejia.engine.render.opengl.shader.ShapedShader
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.ShapedStageManager
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.util.TimeUnit
import com.github.serivesmejia.engine.stage.`object`.ShapedGlobalObjectManager
import kotlinx.datetime.Clock

/**
 * Static accessibility object for multiple util aspects of the engine
 */
object Shaped {

    /**
     * Global event bus, parent of the
     * event bus in ShapedStageManager.
     */
    val globalEventBus = ShapedEventBus()

    /**
     * Registers a callback to the global event bus.
     * Shorthand for Shaped.globalEventBus.on
     * @see ShapedEventBus.on
     */
    inline fun <reified T : ShapedEvent> on(noinline block: (T) -> Unit) = globalEventBus.on(block)

    /**
     * Fires an event to the global event bus.
     * Shorthand for Shaped.globalEventBus.fire
     * @see ShapedEventBus.fire
     */
    fun fire(event: ShapedEvent) = globalEventBus.fire(event)

    object Engine {

        /**
         * The current stage manager being used
         */
        lateinit var stageManager: ShapedStageManager
            internal set

        /**
         * Tells whether the engine has been created or not
         */
        var hasCreatedEngine = false
            internal set

        /**
         * Tells whether the user requested
         * to close with the end() function
         */
        internal var closeRequested = false
            private set

        /**
         * Difference of time between the last
         * and current frame, in seconds
         */
        var deltaTime = 0.0f
            internal set

        /**
         * Current FPS of the engine loop
         */
        var fps = 0
            internal set

        /**
         * Changes to another stage and destroys the old one
         * @param stage the stage to change to
         */
        fun changeStage(stage: ShapedStage) = stageManager.changeStage(stage)

        /**
         * Adds a global object (persists through all
         * stages & is not disposed until the game ends or
         * it is manually removed).
         * @param obj the object to add and make global
         * @see ShapedGlobalObjectManager
         */
        fun addGlobalObject(obj: ShapedObject) = stageManager.globalObjectManager.addGlobalObject(obj)

        /**
         * Removes a global object.
         * @param obj the object to remove and unmake global
         * @see ShapedGlobalObjectManager
         */
        fun removeGlobalObject(obj: ShapedObject) = stageManager.globalObjectManager.removeGlobalObject(obj)

        /**
         * Request the engine to end
         */
        fun end() {
            closeRequested = true
        }

    }


    /**
     * System module for accessing multiple multiplatform utilities
     */
    object System {

        /**
         * The system clock, from the kotlin
         * datetime common library.
         */
        val clock = Clock.System

        /**
         * Gets the current time in millis,
         * multiplatform equivalent of Java's
         * System.currentTimeMillis()
         */
        val currentTimeMillis get() = clock.now().toEpochMilliseconds()

        /**
         * Gets the current nanotime, multiplatform
         * equivalent of Java's System.nanoTime().
         *
         * With less precision since it's converted
         * from currentTimeMillis to nanos
         */
        val nanoTime get() = TimeUnit.MILLISECONDS.convert(currentTimeMillis, TimeUnit.NANOSECONDS)

    }

    /**
     * Graphics module for accessing multiplatform rendering aspects
     */
    object Graphics {

        /**
         * The current window being
         * used in the engine.
         *
         * Note that this property is
         * not available before calling
         * create() on the ShapedEngine
         */
        lateinit var window: ShapedWindow
            internal set

        /**
         * The current renderer being
         * used in this engine.
         *
         * Note that this property is
         * not available before calling
         * create() on the ShapedEngine
         */
        lateinit var renderer: ShapedRenderer
            internal set

        /**
         * Short-hand for getting the
         * ShapedShapeBuilder declared
         * in the renderer.
         *
         * Used to build platform
         * specific shapes.
         */
        val shapes get() = renderer.shapeBuilder

        /**
         * Short-hand for getting the ShapedShaderLoader
         * declared in the renderer.
         *
         * Used to load ShapedShaderSource-s
         */
        val shaders get() = renderer.shaderLoader

        /**
         * The engine's default shader
         *
         * Initialized lazily, therefore it
         * cannot be gotten before the
         * ShapedRenderer is available
         */
        val defaultShader: ShapedShader by lazy {
            shaders.loadShader(DefaultVertexShader, DefaultFragmentShader)
        }

        /**
         * Loads a texture from a resoruce file,
         * platform-dependent call. Short-hand for
         * the texture loader in Shaped.Graphics.renderer
         *
         * @param resourcePath the absolute path to the texture resource
         * @return the loaded texture from the resource
         */
        fun loadTexture(resourcePath: String) = renderer.textureLoader.loadTexture(resourcePath)

    }

}