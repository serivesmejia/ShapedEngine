package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class ShapedObjectDslBuilder(
    val obj: ShapedObject,
    val block: ShapedObjectDslBuilder.(ShapedObject) -> Unit
) : ShapedStageComponentDslBuilder<ShapedObject>(obj) {

    constructor(block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) : this(ShapedObject(), block)

    override fun add(obj: ShapedObject) = this.obj.addChild(obj)

    override fun remove(obj: ShapedObject) = this.obj.removeChild(obj)

    override fun build(): ShapedObject {
        block(this, obj)
        buildObjects()

        return obj
    }

}