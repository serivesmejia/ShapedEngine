package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.event.Subscribe
import com.github.serivesmejia.engine.common.event.standard.WindowMoveEvent
import com.github.serivesmejia.engine.common.event.standard.WindowResizeEvent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class TestObject : ShapedObject() {

    override fun init() {
        println("helo from object!")
    }

    override fun update(deltaTime: Float) { }

    @Subscribe(WindowMoveEvent::class)
    fun onMove(evt: WindowMoveEvent) {
        println("object move! $evt")
    }

    @Subscribe(WindowResizeEvent::class)
    fun onResize(evt: WindowResizeEvent) {
        println("object resize! $evt")
    }

    override fun dispose() {

    }

}