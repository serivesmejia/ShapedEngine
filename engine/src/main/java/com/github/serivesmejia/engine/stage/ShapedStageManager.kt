package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.common.loop.ShapedLoop
import com.github.serivesmejia.engine.stage.common.InitialStage

class ShapedStageManager : ShapedLoop {

    var currentStage: ShapedStage? = null
        private set

    override fun create(): ShapedStageManager {
        changeStage(InitialStage())
        return this
    }

    override fun update(deltaTime: Float) {

    }

    fun changeStage(stage: ShapedStage) {
        currentStage?.destroy()

        stage.create()
        currentStage = stage
    }

    override fun destroy(): ShapedStageManager {

        return this
    }

}