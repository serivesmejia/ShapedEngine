package com.github.serivesmejia.engine.common.event.standard

import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.stage.ShapedStage

data class StageChangeEvent(val oldStage: ShapedStage?,
                            val newStage: ShapedStage) : ShapedEvent