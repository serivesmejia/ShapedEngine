package com.github.serivesmejia.engine.common.modular

import com.github.serivesmejia.engine.common.ShapedComponent

/**
 * Represents a module that can be added to a ShapedModular
 * @param M the ShapedModular this module can be added to
 */
interface ShapedModule<M : ShapedModular<M>> : ShapedComponent {
    /**
     * Update function called by the ShapedModular
     * @param deltaTime the difference in second between the current and last call
     */
    fun update(deltaTime: Float)
}