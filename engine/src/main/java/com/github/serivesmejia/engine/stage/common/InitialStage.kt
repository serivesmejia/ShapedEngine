package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.stage.ShapedStage

class InitialStage : ShapedStage("Stage-Initial") {

    override fun update(deltaTime: Float) {
        println(deltaTime)
    }

}