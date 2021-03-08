package com.github.serivesmejia.engine.common.modular

import com.github.serivesmejia.engine.common.ShapedComponent

interface ShapedModule<M : ShapedModular<M>> : ShapedComponent {
    fun update(deltaTime: Float)
}