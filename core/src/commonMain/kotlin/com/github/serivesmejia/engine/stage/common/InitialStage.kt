package com.github.serivesmejia.engine.stage.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.Triangle2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.render.shape.ShapedShape2
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.common.TestObject

class InitialStage : ShapedStage("Stage-Initial") {

    lateinit var triangShape: ShapedShape2

    override fun init() {
        addChild(TestObject())

        triangShape = Shaped.Graphics.shapes.triangle(
            Triangle2(
                Vector2(200f, 200f),
                Size2(100f, 100f)
            ),
            Color4(128f, 127f, 255f)
        )

        //Shaped.Graphics.renderer.addShape(triangShape)

        val rectShape = Shaped.Graphics.shapes.rectangle(
            Rectangle2(
                Vector2(200f, 200f),
                Size2(100f, 100f)
            ),
            Color4(128f, 127f, 255f)
        )

        Shaped.Graphics.renderer.addShape(rectShape)

        Shaped.Graphics.renderer.backgroundColor = Color4(127f, 255f, 100f)
    }

}