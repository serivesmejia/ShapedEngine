package com.github.serivesmejia.engine.stage.`object`

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.ShapedComponent
import com.github.serivesmejia.engine.common.event.standard.StageChangeEvent
import com.github.serivesmejia.engine.stage.ShapedStageManager

class ShapedGlobalObjectManager(private val stageManager: ShapedStageManager) : ShapedComponent {

    val globalObjects get() = internalGlobalObjects.toTypedArray()

    private val internalGlobalObjects = mutableListOf<ShapedObject>()

    override fun create(): ShapedGlobalObjectManager {
        Shaped.on<StageChangeEvent> {
            for(obj in globalObjects) {
                it.newStage.addChild(obj)
            }
        }

        return this
    }

    fun addGlobalObject(obj: ShapedObject) {
        internalGlobalObjects.add(obj)
        stageManager.currentStage?.addChild(obj)
    }

    fun removeGlobalObject(obj: ShapedObject) {
        internalGlobalObjects.remove(obj)
        obj.destroy()
    }

    override fun destroy(): ShapedGlobalObjectManager {
        for(obj in globalObjects) {
            obj.destroy()
        }
        return this
    }

}