package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.common.HierarchyShapedComponent
import com.github.serivesmejia.engine.common.ShapedContainer
import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.common.event.ShapedEventRegistrator

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

    /**
     * Check whether if this StageComponent is a Stage, otherwise it's a StageObject
     */
    val isStage: Boolean
        get() = this is ShapedStage

    private val eventBuses = ArrayList<ShapedEventBus>()

    /**
     * Override of addChild function to register this new children
     * to the subscribed event buses and calling create() on them
     *
     * @param child the ShapedObject child to add to this component
     */
    override fun addChild(child: ShapedObject) {
        for(eventBus in eventBuses) {
            eventBus.register(child)
        }

        child.create()
        super.addChild(child)
    }

    /**
     * Gets called when someone registers this component to an EventBus
     */
    override fun register(eventBus: ShapedEventBus) {
        //adds all children objects to the eventBus
        for(obj in children) {
            eventBus.register(obj)
        }
        //adds the eventbus from the list of subscribed eventbuses
        eventBuses.add(eventBus)
    }

    /**
     * Gets called when someone unregisters this component from an EventBus
     */
    override fun unregister(eventBus: ShapedEventBus) {
        //removes all children objects from this eventBus
        for(obj in children) {
            eventBus.unregister(obj)
        }
        //remove the eventbus from the list of subscribed eventbuses
        eventBuses.remove(eventBus)
    }

}