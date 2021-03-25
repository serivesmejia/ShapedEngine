package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.stage.ShapedStage

class InitialStage : ShapedStage("Stage-Initial") {

    override fun init() {
        //addChild(TestObject())

        val texture = Shaped.Graphics.loadTexture("/test.png")

        println("${texture.size}")

        val rectShape = Shaped.Graphics.shapes.texturedRectangle(
            Vector2(200f, 200f),
            Size2(100f, 100f),
            texture
        )

        Shaped.Graphics.renderer.addShape(rectShape)

        Shaped.Graphics.renderer.backgroundColor = Color4(127f, 255f, 100f)

        Shaped.Graphics.window.center()
    }

}