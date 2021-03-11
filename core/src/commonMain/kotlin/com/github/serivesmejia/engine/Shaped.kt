package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.render.ShapedRenderer
import com.github.serivesmejia.engine.render.ShapedWindow
import com.github.serivesmejia.engine.util.TimeUnit
import kotlinx.datetime.Clock

/**
 * Static accessibility object for multiple aspects of the engine
 */
object Shaped {

    /**
     *
     */
    var hasCreatedEngine = false
        internal set

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
     * Request the engine to end
     */
    fun end() {
        closeRequested = true
    }

    /**
     * System module for accessing multiple multiplatform utilities
     */
    object System {
        val clock = Clock.System

        val currentTimeMillis get() = clock.now().toEpochMilliseconds()
        val nanoTime get() = TimeUnit.MILLISECONDS.convert(currentTimeMillis, TimeUnit.NANOSECONDS)
    }

    /**
     * Graphics module for accessing multiplatform rendering aspects
     */
    object Graphics {
        lateinit var window: ShapedWindow
            internal set

        lateinit var renderer: ShapedRenderer
            internal set
    }

}