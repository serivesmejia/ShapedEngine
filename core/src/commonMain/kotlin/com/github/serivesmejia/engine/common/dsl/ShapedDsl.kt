package com.github.serivesmejia.engine.common.dsl

import com.github.serivesmejia.engine.common.dsl.stage.ShapedDslObject
import com.github.serivesmejia.engine.common.dsl.stage.ShapedDslStage
import com.github.serivesmejia.engine.common.dsl.stage.builder.ShapedObjectDslBuilder
import com.github.serivesmejia.engine.common.dsl.stage.builder.ShapedStageDslBuilder
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

fun shapedStage(name: String = "Stage-Unknown",
                block: ShapedStageDslBuilder.(ShapedStage) -> Unit) = ShapedDslStage(name, block)

fun shapedObject(block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) = ShapedDslObject(block)