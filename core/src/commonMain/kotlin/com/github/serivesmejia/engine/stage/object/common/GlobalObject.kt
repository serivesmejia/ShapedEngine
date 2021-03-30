package com.github.serivesmejia.engine.stage.`object`.common

import com.github.serivesmejia.engine.stage.`object`.ShapedObject
import com.github.serivesmejia.engine.stage.behavior.common.TestBehavior

class GlobalObject : ShapedObject() {

    override fun init() {
        interval(3.0) {
            println("my parent is $parent, parent stage is $parentStage")
        }

        timeout(1.0) {
            println("timeout!")
        }

        +TestBehavior()
    }

}