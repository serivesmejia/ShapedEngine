package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.common.ShapedComponent

open class ShapedStage : ShapedComponent {

    override fun create(): ShapedStage {
        init()
        return this
    }

    open fun init() {}

    open fun update(deltaTime: Float) {}

    override fun destroy(): ShapedStage {
        dispose()
        return this
    }

    open fun dispose() {}

}