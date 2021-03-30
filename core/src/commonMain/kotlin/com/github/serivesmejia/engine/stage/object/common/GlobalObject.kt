package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.dsl.shapedObject
import com.github.serivesmejia.engine.common.math.Math.toDegrees
import com.github.serivesmejia.engine.common.math.Math.toRadians
import com.github.serivesmejia.engine.common.math.geometry.Quaternion
import com.github.serivesmejia.engine.common.math.geometry.Vector3
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class GlobalObject : ShapedObject() {

    override fun init() {
        transform.position = Vector3(20f, 20f, 0f)
        transform.rotation = Quaternion.fromDegrees(60f)

        println("${transform.absolutePosition}, ${transform.rotation.angle.toDegrees()}")

        + shapedObject {
            it.transform.position = Vector3(15f, 15f, 0f)
            it.transform.rotation = Quaternion.fromDegrees(20f)

            println("${it.transform.absolutePosition}, ${it.transform.rotation.angle.toDegrees()}")
        }
    }

    override fun update(deltaTime: Float) { }

}