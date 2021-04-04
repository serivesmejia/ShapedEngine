package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.common.dsl.ShapedDslBuilder
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class ShapedStageDslBuilder(
    private val stage: ShapedStage,
    private val block: ShapedStage.() -> Unit
) : ShapedDslBuilder<ShapedStage> {
    constructor(block: ShapedStage.() -> Unit) : this(ShapedStage(), block)

    override fun build(): ShapedStage {
        block(stage)
        return stage
    }
}