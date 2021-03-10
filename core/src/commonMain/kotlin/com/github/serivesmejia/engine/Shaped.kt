package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.system.PlaceholderSystemTimer
import com.github.serivesmejia.engine.common.system.SystemTimer
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

    object System {
        var timer: SystemTimer = PlaceholderSystemTimer
    }

    object Graphics {
        var window: ShapedWindow = PlaceholderWindow
            internal set
    }

    fun end() {
        closeRequested = true
    }

}