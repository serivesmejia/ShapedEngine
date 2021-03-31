package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.common.dsl.ShapedDslBuilder
import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.timer.ShapedTimer
import com.github.serivesmejia.engine.stage.ShapedStageComponent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

abstract class ShapedStageComponentDslBuilder<T : ShapedStageComponent<*>>(val component: T) : ShapedDslBuilder<T> {

}