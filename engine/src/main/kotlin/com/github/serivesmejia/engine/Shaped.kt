package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.geometry.Rectangle2

object Shaped {

    var hasCreatedEngine = false
        internal set

    var engineThread: Thread? = null
        internal set

    var deltaTime = 0.0f
        internal set

    var fps = 0
        internal set

    object Graphics {
        val displayRect = Rectangle2()

        val displayX get() = displayRect.position.x
        val displayY get() = displayRect.position.y

        val displayWidth get() = displayRect.size.width
        val displayHeight get() = displayRect.size.height
    }

    fun end() = engineThread?.interrupt()

}