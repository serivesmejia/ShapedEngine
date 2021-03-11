package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.common.TestObject

class InitialStage : ShapedStage("Stage-Initial") {

    override fun init() {
        addChild(TestObject())
        Shaped.Graphics.renderer.backgroundColor = Color4(255f, 255f, 255f)
    }

}