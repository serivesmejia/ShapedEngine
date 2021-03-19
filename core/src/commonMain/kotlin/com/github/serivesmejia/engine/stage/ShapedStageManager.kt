package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.Shaped
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

        //add the event bus of this manager as a child to the global one
        Shaped.globalEventBus.addChild(eventBus)

        return this
    }

    override fun update(deltaTime: Float) {
        currentStage?.internalUpdate(deltaTime)
    }

    fun changeStage(stage: ShapedStage) {
        currentStage?.destroy()
        Shaped.Graphics.renderer.clear()

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
