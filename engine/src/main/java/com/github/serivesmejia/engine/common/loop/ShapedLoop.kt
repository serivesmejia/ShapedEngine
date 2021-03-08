package com.github.serivesmejia.engine.common.loop

import com.github.serivesmejia.engine.common.ShapedComponent

interface ShapedLoop : ShapedComponent {
    fun update(deltaTime: Float)
}