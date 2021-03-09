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

    val isStage: Boolean
        get() = this is ShapedStage

    private val eventBuses = ArrayList<ShapedEventBus>()

    override fun addChild(child: ShapedObject) {
        for(eventBus in eventBuses) {
            eventBus.register(child)
        }

        child.create()
        super.addChild(child)
    }

    override fun register(eventBus: ShapedEventBus) {
        for(obj in children) {
            eventBus.register(obj)
        }
        eventBuses.add(eventBus)
    }

}