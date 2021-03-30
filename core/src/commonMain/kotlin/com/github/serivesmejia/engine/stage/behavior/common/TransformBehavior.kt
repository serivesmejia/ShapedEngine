package com.github.serivesmejia.engine.stage.behavior.common

import com.github.serivesmejia.engine.common.math.geometry.Vector3
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior

class TransformBehavior : ShapedBehavior() {

    var position = Vector3()

    val absolutePosition: Vector3 get() {
        return when (shapedObject.parent) {
            is ShapedStage ->
                position
            is ShapedObject ->
                position + (shapedObject.parent as ShapedObject).transform.absolutePosition
            else ->
                Vector3()
        }
    }

    val isLocal get() = shapedObject.parent !is ShapedStage

    override fun init() { }

    override fun update(deltaTime: Float) { }

    fun translate(to: Vector3) {
        position += to
    }

    fun translate(x: Float, y: Float, z: Float = 0f) {
        position += Vector3(x, y, z)
    }

}