package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.common.modular.ShapedModule
import com.github.serivesmejia.engine.stage.common.InitialStage

class ShapedStageManager : ShapedModule<ShapedEngine> {

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
        stage.eventBus = eventBus

        currentStage = stage
        eventBus.register(stage)
    }

    override fun destroy(): ShapedStageManager {
        currentStage?.destroy()
        return this
    }

}
