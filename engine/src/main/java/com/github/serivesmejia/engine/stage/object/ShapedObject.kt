package com.github.serivesmejia.engine.stage.`object`

import com.github.serivesmejia.engine.common.ShapedComponent
import com.github.serivesmejia.engine.stage.ShapedStage

abstract class ShapedObject: ShapedComponent {

    override fun create(): ShapedObject {
        init()
        return this
    }

    abstract fun init()

    abstract fun update(deltaTime: Float)

    override fun destroy(): ShapedObject {
        dispose()
        return this
    }

    abstract fun dispose()

}