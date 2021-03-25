package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class ShapedStageDslBuilder(
    private val stage: ShapedStage,
    private val block: ShapedStageDslBuilder.(ShapedStage) -> Unit
) : ShapedStageComponentDslBuilder<ShapedStage>(stage) {

    constructor(block: ShapedStageDslBuilder.(ShapedStage) -> Unit) : this(ShapedStage(), block)

    override fun add(obj: ShapedObject) = stage.addChild(obj)
    override fun remove(obj: ShapedObject) = stage.removeChild(obj)

    override fun build(): ShapedStage {
        block(this, stage)
        buildObjects()

        return stage
    }

}