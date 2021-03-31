package com.github.serivesmejia.engine.stage.behavior.common

import com.github.serivesmejia.engine.common.math.geometry.rotation.Quaternion
import com.github.serivesmejia.engine.common.math.geometry.position.Vector3
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior

class TransformBehavior : ShapedBehavior() {

    var position = Vector3()

    val absolutePosition: Vector3
        get() = if(isLocal)
            position + (shapedObject.parent as ShapedObject).transform.absolutePosition
        else position

    var rotation = Quaternion()

    val absoluteRotation: Quaternion
        get() = if(isLocal)
            rotation + (shapedObject.parent as ShapedObject).transform.absoluteRotation
        else rotation

    val isLocal get() = shapedObject.parent !is ShapedStage

    override fun init() { }

    override fun update(deltaTime: Float) { }

    fun translate(x: Float = 0f, y: Float = 0f, z: Float = 0f) {
        position += Vector3(x, y, z)
    }

}