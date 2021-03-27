package com.github.serivesmejia.engine.stage.behavior

import com.github.serivesmejia.engine.common.ShapedLoopComponent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class ShapedBehaviorManager(val parent: ShapedObject) : ShapedLoopComponent {

    val behaviors get() = internalBehaviors.toTypedArray()

    private val internalBehaviors = mutableListOf<ShapedBehavior>()

    override fun create(): ShapedBehaviorManager {
        return this
    }

    override fun update(deltaTime: Float) = behaviors.forEach {
        it.parent = parent

        if(!it.created) it.create()
        it.update(deltaTime)

        if(it.destroyed) {
            internalBehaviors.remove(it)
        }
    }

    fun addBehavior(behavior: ShapedBehavior) = internalBehaviors.add(behavior)

    fun removeBehavior(behavior: ShapedBehavior) = internalBehaviors.remove(behavior)

    override fun destroy(): ShapedBehaviorManager {
        for(behavior in behaviors) {
            behavior.destroy()
        }

        return this
    }

}