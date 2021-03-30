package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.event.standard.WindowResizeEvent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.stage.behavior.common.TestBehavior

class GlobalObject : ShapedObject() {

    override fun init() {
        println("create global object")

        interval(3.0) {
            println("global, my parent is $parent, parent stage is $parentStage")
        }

        timeout(1.0) {
            println("timeout global!")
        }

        +TestBehavior()

        on<WindowResizeEvent> {
            println("global resize!")
        }
    }

    var frames = 0

    override fun update(deltaTime: Float) {
        frames++
        if(frames > 30) {
            frames = 0
            println("update global 30!")
        }
    }

    override fun dispose() {
        println("dispose global")
    }

}