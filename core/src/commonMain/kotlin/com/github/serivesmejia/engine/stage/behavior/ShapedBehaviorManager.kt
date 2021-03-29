package com.github.serivesmejia.engine.stage.behavior

import com.github.serivesmejia.engine.common.ShapedLoopComponent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

/**
 * Manages behaviors per stage object. Every stage object
 * has an individual manager which is internally created,
 * initialized, and destroyed.
 *
 * @property parent the parent ShapedObject of this behavior manager
 */
class ShapedBehaviorManager(val parent: ShapedObject) : ShapedLoopComponent {

    /**
     * Get the current behaviors in this
     * manager as an independent typed array
     */
    val behaviors get() = internalBehaviors.toTypedArray()

    private val internalBehaviors = mutableListOf<ShapedBehavior>()

    /**
     * Create this behavior manager
     */
    override fun create(): ShapedBehaviorManager {
        return this
    }

    /**
     * Update this behavior manager.
     * Calls create() on behaviors that haven't been created,
     * update() on all behaviors, and removes all behaviors
     * that have been destroyed.
     */
    override fun update(deltaTime: Float) = behaviors.forEach {
        it.parent = parent

        if(!it.created) it.create()
        it.update(deltaTime)

        if(it.destroyed) {
            internalBehaviors.remove(it)
        }
    }

    /**
     * Add a behavior to be managed by this manager
     * @param behavior the behavior to add
     */
    fun addBehavior(behavior: ShapedBehavior) = internalBehaviors.add(behavior)

    /**
     * Removes a behavior from this manager
     * @param behavior the behavior to remove
     */
    fun removeBehavior(behavior: ShapedBehavior) = internalBehaviors.remove(behavior)

    /**
     * Destroy this behavior manager and all
     * the currently-managed behaviors.
     */
    override fun destroy(): ShapedBehaviorManager {
        for(behavior in behaviors) {
            behavior.destroy()
        }

        return this
    }

}