package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.Shaped
import com.github.serivesmejia.engine.common.dsl.shapedObject
import com.github.serivesmejia.engine.common.math.Color4
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.position.Vector2
import com.github.serivesmejia.engine.common.math.geometry.rotation.Rotation2
import com.github.serivesmejia.engine.render.shape.ShapedShape2
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class GlobalObject : ShapedObject() {

    val obj = shapedObject {
        transform2.position = Vector2(20f, 20f)
        transform2.rotation = Rotation2.degrees(2f)
    }

    lateinit var shape: ShapedShape2

    override fun init() {
        transform2.position = Vector2(180f, 180f)
        transform2.rotation = Rotation2.degrees(1f)

        + obj

        later {
            println(obj.transform2.absolutePosition)
            println(obj.transform2.absoluteRotation)
            println(obj.transform2.isLocal)

            shape = Shaped.Graphics.shapes.rectangle(
                obj.transform2.absolutePosition,
                Size2(100f, 100f),
                Color4(255f, 255f, 255f)
            )
        }
    }

    override fun update(deltaTime: Float) {
        shape.draw()
    }

}