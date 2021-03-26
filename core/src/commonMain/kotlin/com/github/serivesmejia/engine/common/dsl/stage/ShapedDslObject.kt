package com.github.serivesmejia.engine.common.dsl.stage

import com.github.serivesmejia.engine.common.dsl.stage.builder.ShapedObjectDslBuilder
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class ShapedDslObject(
    private var builder: ShapedObjectDslBuilder
) : ShapedObject() {

    //placeholder this() call, can't access "this" right now so we'll redefine builder later
    constructor(block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) : this(placeholderBuilder) {
        builder = ShapedObjectDslBuilder(this, block) //redefine builder with "this" object
    }

    override fun init() {
        builder.build()
    }

}

private val placeholderBuilder = ShapedObjectDslBuilder {}