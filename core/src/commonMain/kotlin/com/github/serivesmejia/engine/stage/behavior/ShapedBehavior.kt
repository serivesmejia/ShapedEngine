package com.github.serivesmejia.engine.stage.behavior

import com.github.serivesmejia.engine.common.ShapedLoopComponent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

abstract class ShapedBehavior : ShapedLoopComponent {

    lateinit var shapedObject: ShapedObject
    val parentStage get() = shapedObject.parentStage

    var created = false
        private set

    var destroyed = false
        private set

    override fun create(): ShapedBehavior {
        if(!created) {
            init()
            created = true
        }
        return this
    }

    override fun destroy(): ShapedBehavior {
        if(!destroyed) {
            dispose()
            destroyed = true
        }
        return this
    }

    abstract fun init()

    open fun dispose() { }



}