package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.common.HierarchyShapedComponent
import com.github.serivesmejia.engine.common.ShapedContainer
import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.common.event.subscriber.ShapedEventRegistrator
import com.github.serivesmejia.engine.common.event.subscriber.ShapedEventSubscriber

/**
 * Common class for all stage components to implement. including the stage itself.
 * Used to accept both ShapedObject or ShapedStage as a parent for ShaoedObjects.
 *
 * Therefore, when trying to use the parent, it's also required to check whether
 * if its another ShapedObject or if it's a ShapedStage
 *
 * The "isStage" property can be used for checking, but it's more recommended to
 * use Kotlin's "is" keyword (similar to Java's instanceof) to perform smart casting.
 *
 */
abstract class ShapedStageComponent<T : HierarchyShapedComponent<T>>
    : ShapedEventRegistrator, HierarchyShapedComponent<T>, ShapedContainer<ShapedObject>() {

    override var parent: ShapedContainer<T>? = null

    lateinit var eventBus: ShapedEventBus
        internal set

    private val subscribers = mutableListOf<ShapedEventSubscriber>()

    /**
     * Check whether if this StageComponent is a Stage, otherwise it's a StageObject
     */
    val isStage: Boolean
        get() = this is ShapedStage

    /**
     * Override of addChild function to register this new children
     * to the subscribed event buses and calling create() on them
     *
     * @param child the ShapedObject child to add to this component
     */
    override fun addChild(child: ShapedObject) {
        child.eventBus = eventBus

        child.create()
        super.addChild(child)
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

}