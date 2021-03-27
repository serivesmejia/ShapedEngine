package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior

class ShapedObjectDslBuilder(
    val obj: ShapedObject,
    val block: ShapedObjectDslBuilder.(ShapedObject) -> Unit
) : ShapedStageComponentDslBuilder<ShapedObject>(obj) {

    constructor(block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) : this(ShapedObject(), block)

    override fun addChild(obj: ShapedObject) = this.obj.addChild(obj)

    override fun removeChild(obj: ShapedObject) = this.obj.removeChild(obj)

    override fun build(): ShapedObject {
        block(this, obj)
        buildObjects()

        return obj
    }


    fun addBehavior(behavior: ShapedBehavior) = obj.addBehavior(behavior)
    fun removeBehavior(behavior: ShapedBehavior) = obj.removeBehavior(behavior)

    operator fun ShapedBehavior.unaryPlus() = addBehavior(this)
    operator fun ShapedBehavior.unaryMinus() = removeBehavior(this)

}