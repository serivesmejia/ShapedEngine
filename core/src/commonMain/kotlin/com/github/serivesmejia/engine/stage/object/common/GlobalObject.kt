package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.common.dsl.shapedObject
import com.github.serivesmejia.engine.common.math.geometry.Vector3
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

class GlobalObject : ShapedObject() {

    override fun init() {
        transform.position += Vector3(20f, 20f, 0f)

        println(transform.absolutePosition)

        + shapedObject {
            it.transform.position += Vector3(15f, 15f, 0f)
            println(it.transform.absolutePosition)
        }
    }

    override fun update(deltaTime: Float) { }

}