package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.stage.common.ShapedStageComponent

open class ShapedStage : ShapedStageComponent<ShapedStage>() {

    /**
     * Initializes this Stage
     * User shouldn't manually call this function
     */
    override fun create(): ShapedStage {
        init() //call open method
        return this
    }

    /**
     * Called once, when this stage is about to be used
     */
    open fun init() { }

    internal fun internalUpdate(deltaTime: Float) {
        for(child in children) { //updates all the children first
            child.internalUpdate(deltaTime)
        }

        update(deltaTime) //then call update open function
    }

    /**
     * Called each frame after updating all children
     * @param deltaTime the difference of time in millis between the current and last frame
     */
    open fun update(deltaTime: Float) { }

    /**
     * Destroys this stage
     * It is not recommended to manually call this method, it will
     * be called by the current StageManager once it determines
     * that this stage is no longer necessary for any reason.
     */
    override fun destroy(): ShapedStage {
        for(child in children) {
            child.destroy()
        }

        dispose()
        return this
    }

    open fun dispose() { }

}