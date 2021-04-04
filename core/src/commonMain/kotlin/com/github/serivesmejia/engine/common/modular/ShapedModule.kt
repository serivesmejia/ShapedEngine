package com.github.serivesmejia.engine.common.modular
import com.github.serivesmejia.engine.common.ShapedLoopComponent

/**
 * Represents a module that can be added to a ShapedModular
 * @param M the ShapedModular this module can be added to
 */
interface ShapedModule<M : ShapedModular<M>> : ShapedLoopComponent