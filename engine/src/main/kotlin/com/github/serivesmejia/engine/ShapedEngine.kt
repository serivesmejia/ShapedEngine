package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.modular.ShapedModular
import com.github.serivesmejia.engine.stage.ShapedStageManager
import com.github.serivesmejia.engine.util.ElapsedTime

class ShapedEngine : ShapedModular<ShapedEngine>() {

    lateinit var stageManager: ShapedStageManager
        private set

    private val deltaTimer = ElapsedTime()

    /**
     * Initializes the engine and all its core modules
     */
    override fun create(): ShapedEngine {
        //not a precisely good idea to have more than one engine...
        if(Shaped.hasCreatedEngine)
            throw IllegalStateException("Can't have more than one engine running per JVM")

        //tell globally that we have one engine running
        Shaped.hasCreatedEngine = true
        //tell globally on which thread we're running
        Shaped.engineThread = Thread.currentThread()

        stageManager = ShapedStageManager()
        addModule(stageManager)

        createModules()

        return this
    }

    /**
     * Update the engine without deltaTime parameter
     */
    fun update() = update(0f)

    /**
     * Update the engine
     * @param deltaTime ignored delta time (can be defaulted to zero)
     */
    override fun update(deltaTime: Float) {
        Shaped.deltaTime = deltaTimer.seconds.toFloat() //calculate delta time
        deltaTimer.reset() //reset back to zero

        //update modules
        updateModules(Shaped.deltaTime)
    }

    /**
     * Destroys this ShapedEngine
     */
    override fun destroy(): ShapedEngine {
        destroyModules()
        Shaped.end()
        return this
    }

}