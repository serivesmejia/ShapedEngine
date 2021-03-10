package com.github.serivesmejia.engine.common.event.subscriber

import com.github.serivesmejia.engine.common.event.ShapedEventBus
import com.github.serivesmejia.engine.common.event.wrapper.ShapedEventWrapper

/**
 * Wrapper around a ShapedEventBus to provide
 * a @Subscribe mechanism in supported platforms
 */
abstract class ShapedEventSubscriber : ShapedEventWrapper {

    val wrappedEventBuses get() = internalWrappedEventBuses.toTypedArray()

    private val internalWrappedEventBuses = mutableListOf<ShapedEventBus>()

    /**
     * Register an object to this subscriber
     * @param obj the object to subscribe the annotated functions from
     */
    abstract fun register(obj: Any)

    /**
     * Register an object to this subscriber
     * @param obj the object to unsubscribe the annotated functions from
     */
    abstract fun unregister(obj: Any)

    override fun wrap(eventBus: ShapedEventBus) {
        internalWrappedEventBuses.add(eventBus)
    }

    override fun unwrap(eventBus: ShapedEventBus) {
        internalWrappedEventBuses.remove(eventBus)
    }

}

/**
 * Subscribe a function/method to a specific event
 */
@Target(AnnotationTarget.FUNCTION)
annotation class Subscribe