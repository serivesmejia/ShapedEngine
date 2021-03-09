package com.github.serivesmejia.engine.common.event

/**
 * Classes overriding this interface will be considered as "registrators"
 * when registered them to a ShapedEventBus, meaning that register() will
 * be called once registration finishes for the class.
 */
interface ShapedEventRegistrator {
    /**
     * Called when registering this object from an EventBus
     * @param eventBus the event bus in which this was registered
     */
    fun register(eventBus: ShapedEventBus)

    /**
     * Called when unregistering this object from an EventBus
     * @param eventBus the event bus in which this was unregistered
     */
    fun unregister(eventBus: ShapedEventBus)
}