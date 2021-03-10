package com.github.serivesmejia.engine.common.event.wrapper

import com.github.serivesmejia.engine.common.event.ShapedEventBus

/**
 * Provides a simple wrapping mechanism for the ShapedEngine
 * event system.
 * @see GlfwEventWrapper
 */
interface ShapedEventWrapper {
    fun wrap(eventBus: ShapedEventBus)
    fun unwrap(eventBus: ShapedEventBus)
}