package com.github.serivesmejia.engine.stage.`object`

import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.ShapedStageComponent

abstract class ShapedObject: ShapedStageComponent<ShapedObject>() {

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

    /**
     * Called each frame after updating all children
     * @param deltaTime the difference of time in seconds between the current and last frame
     */
    abstract fun update(deltaTime: Float)

    /**
     * Destroys this object and its children
     * It is not recommended to manually call this method, it
     * will be called by the parent Stage once it determines
     * that it is no longer necessary for any reason.
     */
    override fun destroy(): ShapedObject {
        parent = null

        dispose()
        return this
    }

    /**
     * Called when destroying.
     * Can be used to perform any operation that frees memory
     * or destroys other (untracked) objects when this object
     * is destroyed.
     */
    abstract fun dispose()

    val parentStage: ShapedStage?
        get() {
            if(parent is ShapedStage) { //our parent is a stage!
                return parent as ShapedStage //simply cast our parent as a stage
            } else {
                //if our current parent is a StageComponent
                if(parent is ShapedStageComponent<*>) {
                    //get its parent
                    var currParent = (parent as ShapedStageComponent<*>).parent

                    //linear bottom-to-top scan to find the stage
                    while(currParent != null) {
                        if(currParent is ShapedStage) return currParent //we found a stage!
                        else if(currParent is ShapedStageComponent<*>) { //if curr parent is still a stage component
                            //didn't found a stage. set curr parent to the parent of current
                            //to continue searching in next iteration
                            currParent = currParent.parent
                        } else {
                            //we found something that doesn't belong to this stage!
                            //break the loop here since there's nothing we can do
                            break
                        }
                    }
                }
            }

            return null //we didn't found any stage :(
        }

}