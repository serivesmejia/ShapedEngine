package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.event.Subscribe
import com.github.serivesmejia.engine.common.event.general.WindowMoveEvent
import com.github.serivesmejia.engine.common.event.general.WindowResizeEvent
import com.github.serivesmejia.engine.stage.ShapedStage

class InitialStage : ShapedStage("Stage-Initial") {

    override fun update(deltaTime: Float) {
        
    }

    @Subscribe(WindowMoveEvent::class)
    fun onMove(evt: WindowMoveEvent) {
        println("move! $evt")
    }

    @Subscribe(WindowResizeEvent::class)
    fun onResize(evt: WindowResizeEvent) {
        println("resize! $evt")
    }

}