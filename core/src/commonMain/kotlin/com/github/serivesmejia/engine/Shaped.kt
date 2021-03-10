package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.render.PlaceholderWindow
import com.github.serivesmejia.engine.render.ShapedWindow
import com.github.serivesmejia.engine.util.TimeUnit
import kotlinx.datetime.Clock

object Shaped {

    var hasCreatedEngine = false
        internal set

    internal var closeRequested = false

    var deltaTime = 0.0f
        internal set

    var fps = 0
        internal set

    object System {
        val clock = Clock.System

        val currentTimeMillis get() = clock.now().toEpochMilliseconds()
        val nanoTime get() = TimeUnit.MILLISECONDS.convert(currentTimeMillis, TimeUnit.NANOSECONDS)
    }

    object Graphics {
        var window: ShapedWindow = PlaceholderWindow
            internal set
    }

    fun end() {
        closeRequested = true
    }

}