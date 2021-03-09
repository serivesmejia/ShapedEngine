package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.common.TestObject

class InitialStage : ShapedStage("Stage-Initial") {

    override fun init() {
        addChild(TestObject())
    }

}