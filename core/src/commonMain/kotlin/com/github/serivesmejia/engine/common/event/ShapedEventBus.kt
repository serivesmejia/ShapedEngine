package com.github.serivesmejia.engine.common.event

import com.github.serivesmejia.engine.common.event.subscriber.ShapedEventSubscriber
import com.github.serivesmejia.engine.common.event.wrapper.ShapedEventWrapper
import kotlin.reflect.KClass

/**
 * Handles events via a callback mechanism
 */
class ShapedEventBus {

    private val callbacks = mutableMapOf<KClass<out ShapedEvent>, MutableList<(ShapedEvent) -> Unit>>()

    private val subscribers = mutableListOf<ShapedEventSubscriber>()

    /**
     * Fires all the callbacks for the specified event
     * @event the event to fire (and to be passed to the subscribed methods)
     */
    fun fire(event: ShapedEvent) {
        callbacks[event::class]?.forEach {
            it(event)
        }
    }

    /**
     * Adds a callback to be called when a certain event is fired.
     * See inline version below for a better syntax
     *
     * @param E the event as a type parameter
     * @param eventClass the class of the event to fire this callback on
     * @param block the block to call when the event is fired
     */
    @Suppress("UNCHECKED_CAST")
    fun <E : ShapedEvent> on(eventClass: KClass<E>, block: (E) -> Unit) {
        val castedBlock = block as (ShapedEvent) -> Unit

        if(callbacks.containsKey(eventClass)) {
            callbacks[eventClass]!!.add(castedBlock)
        } else {
            val list = mutableListOf(castedBlock)
            callbacks[eventClass] = list
        }
    }

    /**
     * Adds a callback to be called when a certain event is fired.
     *
     * @param E the event to fire the callback on
     * @param block the block to call when the event is fired
     */
    inline fun <reified E : ShapedEvent> on(noinline block: (E) -> Unit) = on(E::class, block)

    /**
     * Clears all callbacks in this EventBus
     */
    fun clear() {
        callbacks.clear()
    }

    /**
     * Wraps this ShapedEventBus around a wrapper
     */
    fun wrap(wrapper: ShapedEventWrapper) = wrapper.wrap(this)

    /**
     * Registers the object to all added ShapedEventSubscriber-s
     * @param obj the object to register
     */
    fun register(obj: Any) {
        for(subscriber in subscribers) {
            subscriber.register(obj)
        }
    }
    /**
     * Unregisters the object to all added ShapedEventSubscriber-s
     * @param obj the object to unregister
     */
    fun unregister(obj: Any) {
        for(subscriber in subscribers) {
            subscriber.register(obj)
        }
    }

    /**
     * Adds an event subscriber to this ShapedEventBus
     * and wraps the subscriber with this event bus
     * @param subscriber the subscriber to add and wrap
     */
    fun addSubscriber(subscriber: ShapedEventSubscriber) {
        subscribers.add(subscriber)
        subscriber.wrap(this)
    }

    /**
     * Removes an event subscriber to this ShapedEventBus
     * and unwraps the subscriber with this event bus
     * @param subscriber the subscriber to remove and unwrap
     */
    fun removeSubscriber(subscriber: ShapedEventSubscriber) {
        subscribers.remove(subscriber)
        subscriber.unwrap(this)
    }

}