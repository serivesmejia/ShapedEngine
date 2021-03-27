package com.github.serivesmejia.engine.stage.behavior.common

import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior

class TestBehavior : ShapedBehavior() {

    override fun init() {
        println("helo from behavior! $parent")
    }

    override fun update(deltaTime: Float) {
        println("update from behavior! $deltaTime")
    }

}