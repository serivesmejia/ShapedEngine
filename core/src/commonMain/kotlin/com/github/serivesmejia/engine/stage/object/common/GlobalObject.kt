package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.dsl.shapedObject
import com.github.serivesmejia.engine.common.math.geometry.rotation.Quaternion
import com.github.serivesmejia.engine.common.math.geometry.position.Vector3
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class GlobalObject : ShapedObject() {

    override fun init() {
        transform.position = Vector3(20f, 20f)
        transform.rotation = Quaternion.fromDegrees(40f)

        println(transform.absoluteRotation.euler.toDegrees())

        + shapedObject {
            transform.position = Vector3(15f, 15f)
            transform.rotation = Quaternion.fromDegrees(20f)
            println(transform.absoluteRotation.euler.toDegrees())
        }
    }

    override fun update(deltaTime: Float) { }

}