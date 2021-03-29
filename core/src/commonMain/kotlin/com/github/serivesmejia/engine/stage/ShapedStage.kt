package com.github.serivesmejia.engine.stage

open class ShapedStage(val name: String = "Stage-Unknown") : ShapedStageComponent<ShapedStage>() {

    /**
     * Initializes this Stage
     * User shouldn't manually call this function
     */
    override fun create(): ShapedStage {
        init() //call open function
        return this
    }

    /**
     * Called once, when this stage is about to be used
     */
    open fun init() { }

    /**
     * Called each frame after updating all children
     * @param deltaTime the difference of time in seconds between the current and last frame
     */
    override fun update(deltaTime: Float) { }

    /**
     * Destroys this stage.
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

    /**
     * Called when destroying.
     * Can be used to perform any operation that frees memory
     * or destroys other (untracked) objects when this stage
     * is being destroyed.
     */
    open fun dispose() { }

}