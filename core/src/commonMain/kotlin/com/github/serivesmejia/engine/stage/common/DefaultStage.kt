package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.dsl.shapedObject
import com.github.serivesmejia.engine.common.dsl.shapedStage
import com.github.serivesmejia.engine.common.event.standard.WindowResizeEvent
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.position.Vector2
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.common.GlobalObject

class DefaultStage : ShapedStage("Stage-Default") {

    override fun init() {
        val texture = Shaped.Graphics.loadTexture("/test.png")

        Shaped.Graphics.run {
            val rectShape = shapes.texturedRectangle(
                Vector2(200f, 200f),
                Size2(100f, 100f),
                texture
            )

            //renderer.addShape(rectShape)
            renderer.backgroundColor = Color4(127f, 255f, 100f)

            window.center()
        }

        on<WindowResizeEvent> {
            Shaped.Engine.changeStage(
                shapedStage {
                    Shaped.Graphics.renderer.backgroundColor = Color4(25f, 100f, 100f)
                }
            )
        }
    }

}