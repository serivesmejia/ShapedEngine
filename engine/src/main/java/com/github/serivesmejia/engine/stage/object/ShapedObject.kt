package com.github.serivesmejia.engine.stage.`object`

import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.common.ShapedStageComponent

abstract class ShapedObject: ShapedStageComponent() {

    val parentStage: ShapedStage
        get() {
            if(parent is ShapedStage) {
                return parent as ShapedStage
            } else {
                var currParent = parent.parent
                while(currParent != null) {

                }
            }
        }

    /**
     * Initializes this object
     * User shouldn't manually call this function
     */
    override fun create(): ShapedObject {
        init()
        return this
    }

    /**
     * Called once, when this stage is about to be used
     */
    abstract fun init()

    internal fun internalUpdate(deltaTime: Float) {
        for(child in children) {
            child.internalUpdate(deltaTime)
        }
        update(deltaTime)
    }

    abstract fun update(deltaTime: Float)

    override fun destroy(): ShapedObject {
        parent = null

        dispose()
        return this
    }

    abstract fun dispose()

}