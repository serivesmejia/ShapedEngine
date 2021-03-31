package com.github.serivesmejia.engine.common.dsl.stage

import com.github.serivesmejia.engine.common.dsl.stage.builder.ShapedStageDslBuilder
import com.github.serivesmejia.engine.stage.ShapedStage

class ShapedDslStage(
    name: String = "Stage-Unknown",
    private var builder: ShapedStageDslBuilder
) : ShapedStage(name) {

    //placeholder this() call, can't access "this" right now so we'll redefine builder later
    constructor(name: String = "Stage-Unknown",
                block: ShapedStage.() -> Unit) : this(name, placeholderBuilder) {
        builder = ShapedStageDslBuilder(this, block) //redefine builder with "this" object
    }

    override fun init() {
        builder.build()
    }

}

private val placeholderBuilder = ShapedStageDslBuilder {}