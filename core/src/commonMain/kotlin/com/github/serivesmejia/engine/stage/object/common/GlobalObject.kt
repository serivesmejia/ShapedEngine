package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.dsl.shapedObject
import com.github.serivesmejia.engine.common.math.geometry.rotation.Quaternion
import com.github.serivesmejia.engine.common.math.geometry.position.Vector3
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class GlobalObject : ShapedObject() {

    val obj = shapedObject {
        transform.position = Vector3(15f, 15f)
        transform.rotation = Quaternion(90f)

        println("${transform.absoluteRotation}, ${transform.absolutePosition}")
    }

    override fun init() {
        transform.position = Vector3(20f, 20f)
        transform.rotation = Quaternion(90f)

        + obj
    }

    override fun update(deltaTime: Float) {
        transform.rotation = Quaternion(transform.rotation.euler.pitch + 0.5f)

        obj.run {
            println("${transform.absoluteRotation}, ${transform.absolutePosition}")
        }
    }

}