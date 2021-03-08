package com.github.serivesmejia.engine.common.event

/**
 * Classes overriding this interface will be considered as "registrators"
 * when registered them to a ShapedEventBus, meaning that register() will
 * be called once registration finishes for the class.
 */
interface ShapedEventRegistrator {
    fun register(eventBus: ShapedEventBus)
}