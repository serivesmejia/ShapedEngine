package com.github.serivesmejia.engine.stage.`object`

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.ShapedComponent
import com.github.serivesmejia.engine.common.event.standard.StageChangeEvent
import com.github.serivesmejia.engine.stage.ShapedStageManager

class ShapedGlobalObjectManager(private val stageManager: ShapedStageManager) : ShapedComponent {

    /**
     * Get the current global objects in this manager,
     * as an unrelated typed array.
     */
    val globalObjects get() = internalGlobalObjects.toTypedArray()

    private val internalGlobalObjects = mutableListOf<ShapedObject>()

    override fun create(): ShapedGlobalObjectManager {
        Shaped.on<StageChangeEvent> {
            for(obj in globalObjects) {
                obj.parent = null //remove the current parent of the global object
                it.newStage.addChild(obj) //add the global object as a child to the new stage
            }
        }

        return this
    }

    /**
     * Adds and makes global an object from this manager
     * @param obj the object to add to this manager
     */
    fun addGlobalObject(obj: ShapedObject) {
        obj.isGlobal = true //tell the object that he is global now

        internalGlobalObjects.add(obj) //add the object to our internal list
        stageManager.currentStage?.addChild(obj) //add the object to the current stage, if any
    }

    /**
     * Removes and unmakes global an object from this manager
     * @param obj the object to remove from this manager
     */
    fun removeGlobalObject(obj: ShapedObject) {
        obj.isGlobal = false //tell the object that he is not global anymore :(

        internalGlobalObjects.remove(obj) //remove the object from our internal list
        obj.destroy() //dispose the object
    }

    override fun destroy(): ShapedGlobalObjectManager {
        for (obj in globalObjects) {
            removeGlobalObject(obj)
        }

        return this
    }

}