package com.github.serivesmejia.engine.common.dsl.stage

import com.github.serivesmejia.engine.common.dsl.stage.builder.ShapedStageDslBuilder
import com.github.serivesmejia.engine.stage.ShapedStage

class ShapedDslStage(
    name: String = "Stage-Unknown",
    block: ShapedStageDslBuilder.(ShapedStage) -> Unit
) : ShapedStage(name) {

    private val builder = ShapedStageDslBuilder(this, block)

    override fun init() {
        builder.build()
    }

}