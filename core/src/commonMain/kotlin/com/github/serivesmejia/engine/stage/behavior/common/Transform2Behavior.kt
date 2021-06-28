package com.github.serivesmejia.engine.stage.behavior.common

import com.github.serivesmejia.engine.common.math.geometry.position.Vector2
import com.github.serivesmejia.engine.common.math.geometry.rotation.Rotation2
import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior

class Transform2Behavior : ShapedBehavior() {

    private val objectParent get() = (shapedObject.parent as ShapedObject)

    var position = Vector2.zero

    val absolutePosition: Vector2
        get() = if(isLocal) {
            (position + objectParent.transform2.absolutePosition).rotateBy(absoluteRotation)
        } else position

    var rotation = Rotation2.zero

    val absoluteRotation: Rotation2
        get() = if(isLocal)
            rotation + objectParent.transform2.absoluteRotation
        else rotation

    val isLocal get() = shapedObject.parent !is ShapedStage

    override fun init() { }
    override fun update(deltaTime: Float) { }

    fun translate(vec: Vector2) {
        position += vec
    }

    fun translate(x: Float, y: Float) = translate(Vector2(x, y))

}