package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.event.standard.WindowMoveEvent
import com.github.serivesmejia.engine.common.event.standard.WindowResizeEvent
import com.github.serivesmejia.engine.common.event.subscriber.Subscribe
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class TestObject : ShapedObject() {

    override fun init() {
        println("helo from object!")

        eventBus.on<WindowMoveEvent> {
            println("runEvt! $it")
        }
    }

    override fun update(deltaTime: Float) { }

    @Subscribe
    fun onMove(evt: WindowMoveEvent) {
        println("object move! $evt")
    }

    @Subscribe
    fun onResize(evt: WindowResizeEvent) {
        println("object resize! $evt")
    }

    override fun dispose() { }

}