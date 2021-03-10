package com.github.serivesmejia.engine.util

class FpsCounter {

    private val timer = ElapsedTime()
    private var fpsCount = 0

    var fps = 0
        private set

    fun update() {
        fpsCount++

        if(timer.seconds >= 1) {
            fps = fpsCount; fpsCount = 0
            timer.reset()
        }
    }

}