package com.github.serivesmejia.engine.common

/**
 * The base class for most of the components of the engine.
 * Implements a create() and destroy() method which returns "this"
 */
interface ShapedComponent {
    fun create(): ShapedComponent
    fun destroy(): ShapedComponent
}