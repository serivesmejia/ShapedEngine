package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.common.loop.ShapedLoop
import com.github.serivesmejia.engine.stage.common.InitialStage

class ShapedStageManager : ShapedLoop {

    var currentStage: ShapedStage? = null
        private set

    val eventBus = ShapedEventBus()

    override fun create(): ShapedStageManager {
        changeStage(InitialStage())
        return this
    }

    override fun update(deltaTime: Float) {
        currentStage?.internalUpdate(deltaTime)
    }

    fun changeStage(stage: ShapedStage) {
        currentStage?.destroy()
        eventBus.clear()

        stage.create()
        currentStage = stage
        eventBus.register(stage)
    }

    override fun destroy(): ShapedStageManager {
        currentStage?.destroy()
        return this
    }

}