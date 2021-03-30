package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.common.HierarchyShapedComponent
import com.github.serivesmejia.engine.common.ShapedContainer
import com.github.serivesmejia.engine.common.dsl.stage.builder.ShapedObjectDslBuilder
import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.common.event.subscriber.ShapedEventRegistrator
import com.github.serivesmejia.engine.common.event.subscriber.ShapedEventSubscriber
import com.github.serivesmejia.engine.common.timer.ShapedTimer
import com.github.serivesmejia.engine.common.timer.ShapedTimerManager

/**
 * Common class for all stage components to implement. including the stage itself.
 * Used to accept both ShapedObject or ShapedStage as a parent for ShaoedObjects.
 *
 * Therefore, when trying to use the parent, it's also required to check whether
 * if its another ShapedObject or if it's a ShapedStage
 *
 * The "isStage" property can be used for checking, but it's more recommended to
 * use Kotlin's "is" keyword (similar to Java's instanceof) to perform smart casting.
 */
abstract class ShapedStageComponent<T : HierarchyShapedComponent<T>>
    : ShapedEventRegistrator, HierarchyShapedComponent<T>, ShapedContainer<ShapedObject>() {

    override var parent: ShapedContainer<T>? = null

    lateinit var eventBus: ShapedEventBus
        internal set

    lateinit var timerManager: ShapedTimerManager
        internal set

    private val subscribers = mutableListOf<ShapedEventSubscriber>()

    private var hasBeenCreated = false

    /**
     * Check whether if this StageComponent is a Stage, otherwise it's a StageObject
     */
    val isStage: Boolean
        get() = this is ShapedStage

    internal open fun internalUpdate(deltaTime: Float) {
        if(!hasBeenCreated) { //call create on this component if we haven't done so
            create()
            hasBeenCreated = true
        }

        for(child in children) { //updates all the children first
            child.internalUpdate(deltaTime)
        }

        update(deltaTime) //then call update open function
    }

    /**
     * Called each frame after updating all children
     * @param deltaTime the difference of time in seconds between the current and last frame
     */
    open fun update(deltaTime: Float) { }

    /**
     * Override of addChild function to register this new children
     * to the subscribed event buses.
     *
     * @param child the ShapedObject child to add to this component
     */
    override fun addChild(child: ShapedObject) {
        if(children.contains(child)) return

        child.eventBus = eventBus
        child.timerManager = timerManager

        for(subscriber in subscribers) {
            subscriber.register(child)
        }

        super.addChild(child)
    }

    /**
     * Override of addChild function un register this new children
     * to the subscribed event buses and calling destroy() on them
     *
     * @param child the ShapedObject child to remove from this component
     */
    override fun removeChild(child: ShapedObject) {
        for(subscriber in subscribers) {
            subscriber.unregister(child)
        }
        if(!child.isGlobal) child.destroy()
        super.removeChild(child)
    }

    /**
     * Gets called when someone registers this component to an EventBus
     */
    override fun register(subscriber: ShapedEventSubscriber) {
        //adds all children objects to the eventBus
        for(obj in children) {
            subscriber.register(obj)
        }

        //adds the eventbus from the list of subscribed eventbuses
        subscribers.add(subscriber)
    }

    /**
     * Gets called when someone unregisters this component from an EventBus
     */
    override fun unregister(subscriber: ShapedEventSubscriber) {
        //removes all children objects from this eventBus
        for(obj in children) {
            subscriber.unregister(obj)
        }

        //remove the eventbus from the list of subscribed eventbuses
        subscribers.remove(subscriber)
    }

    /**
     * Short-hand function for eventBus.on
     *
     * Allows to register events that work on all platforms
     *
     * (compared to the @Subscribe mechanism which only
     * works on platforms that can handle reflection,
     * such as the JVM)
     *
     * @param T the event to listen to
     * @param block the callback to call when the event is fired
     */
    inline fun <reified T : ShapedEvent> on(noinline block: (T) -> Unit) = eventBus.on(block)

    /**
     * Adds a block to be executed ONCE after a certain timeout
     * @param seconds the time in seconds to execute this block once it timeouts
     * @param block the block to execute after the timeout
     */
    fun timeout(seconds: Double, block: (ShapedTimer) -> Unit) = timerManager.timeout(seconds, block)

    /**
     * Adds a block to be executed REPETITIVELY after a certain timeout
     * @param seconds the time in seconds to execute this block once it timeouts
     * @param block the block to execute REPETITIVELY after each timeout
     */
    fun interval(seconds: Double, block: (ShapedTimer) -> Unit) = timerManager.interval(seconds, block)

    /**
     * Adds a ShapedObject to this stage component,
     * with the syntax...
     * ```kotlin
     *  +ShapedObject()
     * ```
     */
    operator fun ShapedObject.unaryPlus() = this@ShapedStageComponent.addChild(this)

    /**
     * Removes a ShapedObject from this stage component,
     * with the syntax...
     * ```kotlin
     *  -ShapedObject()
     * ```
     */
    operator fun ShapedObject.unaryMinus() =  this@ShapedStageComponent.removeChild(this)

    /**
     * Adds a child ShapedObject to this stage component,
     * with a DSL syntax for adding children, event listeners, etc.
     * @param child the children ShapedObject to add
     * @param block the DSL block of code to run and build the object
     */
    fun addChild(child: ShapedObject,
                 block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) {
        addChild(child)
        ShapedObjectDslBuilder(child, block).build()
    }

}