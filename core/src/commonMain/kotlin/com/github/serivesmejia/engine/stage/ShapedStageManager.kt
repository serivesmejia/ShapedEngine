package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.common.modular.ShapedModule
import com.github.serivesmejia.engine.common.timer.ShapedTimerManager
import com.github.serivesmejia.engine.stage.common.InitialStage

class ShapedStageManager : ShapedModule<ShapedEngine> {

    var currentStage: ShapedStage? = null
        private set

    val eventBus = ShapedEventBus()

    val timerManager = ShapedTimerManager()

    override fun create(): ShapedStageManager {
        changeStage(InitialStage())

        //add the event bus of this manager as a child to the global one
        Shaped.globalEventBus.addChild(eventBus)

        return this
    }

    override fun update(deltaTime: Float) {
        currentStage?.internalUpdate(deltaTime)
        timerManager.update()
    }

    fun changeStage(stage: ShapedStage) {
        currentStage?.destroy()

        //clear all shapes and destroy all timers
        Shaped.Graphics.renderer.clear()
        timerManager.destroyAll()

        eventBus.clear()
        stage.eventBus = eventBus
        stage.timerManager = timerManager

        currentStage = stage
        eventBus.register(stage)
    }

    override fun destroy(): ShapedStageManager {
        currentStage?.destroy()
        return this
    }

}
