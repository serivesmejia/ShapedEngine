package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.event.standard.WindowMoveEvent
import com.github.serivesmejia.engine.common.event.standard.WindowResizeEvent
import com.github.serivesmejia.engine.common.event.subscriber.Subscribe
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class TestObject : ShapedObject() {

    override fun init() {
        on<WindowMoveEvent> {

        }
    }

    override fun update(deltaTime: Float) {
    }

    override fun dispose() { }

}