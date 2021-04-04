package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.common.dsl.ShapedDslBuilder
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior

class ShapedObjectDslBuilder(
    val obj: ShapedObject,
    val block: ShapedObject.() -> Unit
) : ShapedDslBuilder<ShapedObject> {

    constructor(block: ShapedObject.() -> Unit) : this(ShapedObject(), block)

    override fun build(): ShapedObject {
        block(obj)

        return obj
    }

}