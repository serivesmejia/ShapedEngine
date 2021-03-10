package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.geometry.Rectangle2
import com.github.serivesmejia.engine.render.PlaceholderWindow
import com.github.serivesmejia.engine.render.ShapedWindow

object Shaped {

    var hasCreatedEngine = false
        internal set

    internal var closeRequested = false

    var deltaTime = 0.0f
        internal set

    var fps = 0
        internal set

    object Graphics {
        var window: ShapedWindow = PlaceholderWindow
            internal set
    }

    fun end() {
        closeRequested = true
    }

}