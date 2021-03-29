package com.github.serivesmejia.engine.stage.behavior.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.event.standard.WindowMoveEvent
import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior

class TestBehavior : ShapedBehavior() {

    override fun init() = parent.run {
        on<WindowMoveEvent> {
            Shaped.Engine.end()
        }
    }

    override fun update(deltaTime: Float) {
    }

}