package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.modular.ModulePriority
import com.github.serivesmejia.engine.common.modular.ShapedModular
import com.github.serivesmejia.engine.common.modular.ShapedModule
import com.github.serivesmejia.engine.render.ShapedRenderer
import com.github.serivesmejia.engine.render.ShapedWindow
import com.github.serivesmejia.engine.stage.ShapedStageManager
import com.github.serivesmejia.engine.util.ElapsedTime
import com.github.serivesmejia.engine.util.FpsCounter

object ShapedEngine : ShapedModular<ShapedEngine>() {

    lateinit var stageManager: ShapedStageManager
        private set

    private var hasWindowModule = false

    private val fpsCounter = FpsCounter()
    private val deltaTimer = ElapsedTime()

    //setup requirements in init block
    init {
        addRequirement<ShapedWindow>(1, 1) { it is ShapedWindow }
        addRequirement<ShapedRenderer>(1, 1) { it is ShapedRenderer }

        addRequirement<ShapedStageManager>(1, 1)
    }

    /**
     * Initializes the engine and all its core modules
     */
    override fun create(): ShapedEngine {
        //not a precisely good idea to have more than one engine...
        if (Shaped.hasCreatedEngine)
            throw IllegalStateException("Can't have more than one engine running per program")

        //tell globally that we have one engine running
        Shaped.hasCreatedEngine = true

        stageManager = ShapedStageManager()
        addModule(stageManager, ModulePriority.MEDIUM)

        createModules()

        return this
    }

    override fun onModuleAdd(module: ShapedModule<ShapedEngine>) {
        when (module) {
            is ShapedWindow -> Shaped.Graphics.window = module
            is ShapedRenderer -> Shaped.Graphics.renderer = module
            is ShapedStageManager -> Shaped.Engine.stageManager = module
        }
    }

    /**
     * Starts this engine, blocking.
     * Make sure to add required modules and call create() before calling this function
     * Automatically calls destroy() once the exit condition is true or engine is told to end
     *
     * @param exitCondition callback returning a boolean to be called every loop to determine if we need to exit, platform dependent
     */
    fun start(exitCondition: () -> Boolean = { true }): ShapedEngine {
        while(!exitCondition() && !Shaped.closeRequested)
            update()

        destroy()

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
        Shaped.fps = fpsCounter.fps

        deltaTimer.reset() //reset back to zero

        //update modules
        updateModules(Shaped.deltaTime) //update all modules
        fpsCounter.update() //fps counting
    }

    /**
     * Destroys this ShapedEngine
     */
    override fun destroy(): ShapedEngine {
        destroyModules()
        Shaped.Engine.end()
        return this
    }

}