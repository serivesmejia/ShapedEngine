package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.event.standard.WindowResizeEvent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class TestObject : ShapedObject() {

    override fun init() {
        on<WindowResizeEvent> {
            println("it works! $it")
        }
    }

    override fun update(deltaTime: Float) {
    }

    override fun dispose() { }

}