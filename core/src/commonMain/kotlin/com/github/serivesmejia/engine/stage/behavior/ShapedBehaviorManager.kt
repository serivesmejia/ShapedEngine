package com.github.serivesmejia.engine.stage.behavior

import com.github.serivesmejia.engine.common.ShapedLoopComponent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import kotlin.reflect.KClass

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
    val behaviors get() = internalBehaviors.values.toTypedArray()

    private val internalBehaviors = mutableMapOf<KClass<out ShapedBehavior>, ShapedBehavior>()

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
        it.shapedObject = parent

        if(!it.created) it.create()
        it.update(deltaTime)

        if(it.destroyed) {
            internalBehaviors.remove(it::class)
        }
    }
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


    /**
     * Add a behavior to be managed by this manager.
     * If a behavior of the same type as the one to
     * be added is currently part of the manager, the
     * new one is not added.
     *
     * @param behavior the behavior to add
     */
    fun addBehavior(newBehavior: ShapedBehavior) {
        for(behavior in behaviors) {
            if(behavior::class == newBehavior::class) return
        }

        newBehavior.shapedObject = parent

        internalBehaviors[newBehavior::class] = newBehavior
    }

    /**
     * Removes a behavior from this manager
     * @param behavior the behavior to remove
     */
    fun removeBehavior(behavior: ShapedBehavior) = removeBehavior(behavior::class)

    /**
     * Removes a behavior by the class type,
     * nothing is removed if a behavior with
     * the specified class is not present in
     * this manager.
     *
     * @param behaviorClass the class of the behavior to remove
     */
    fun removeBehavior(behaviorClass: KClass<out ShapedBehavior>) = internalBehaviors.remove(behaviorClass)

    /**
     * Removes a behavior by a type parameter,
     * nothing is removed if a behavior with
     * the specified type is not present in
     * this manager.
     *
     * @param B the type of the behavior to remove
     */
    inline fun <reified B : ShapedBehavior> removeBehavior() = removeBehavior(B::class)

    /**
     * Looks for a behavior of a certain class in this manager.
     * Returns null if the manager does not contain a behavior
     * of the requested type.
     *
     * @param B the type of the behavior to look for
     * @param clazz the class of the behavior to look for
     * @return the found behavior of the requested type, or null if not found.
     */
    @Suppress("UNCHECKED_CAST")
    fun <B : ShapedBehavior> getBehavior(clazz: KClass<out B>) =
        try {
            internalBehaviors[clazz] as B?
        } catch(ex: Exception) {
            null
        }

    /**
     * Looks for a behavior of a certain type in this manager.
     * Returns null if the manager does not contain a behavior
     * of the requested type.
     *
     * @param B the type of the behavior to look for
     * @return the found behavior of the requested type, or null if not found.
     */
    inline fun <reified B : ShapedBehavior> getBehavior() = getBehavior(B::class)

}