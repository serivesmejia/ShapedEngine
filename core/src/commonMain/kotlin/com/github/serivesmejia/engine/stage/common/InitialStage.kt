package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.dsl.shapedObject
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.common.TestObject

class InitialStage : ShapedStage("Stage-Initial") {

    override fun init() {

        val texture = Shaped.Graphics.loadTexture("/test.png")

        println("${texture.size}")

        Shaped.Graphics.run {
            val rectShape = shapes.texturedRectangle(
                Vector2(200f, 200f),
                Size2(100f, 100f),
                texture
            )

            renderer.addShape(rectShape)
            renderer.backgroundColor = Color4(127f, 255f, 100f)

            window.center()
        }

        + shapedObject {
            + TestObject()
        }
    }

}