package com.github.serivesmejia.engine.common.loop

open class ShapedLoopManager {

    private val loops = ArrayList<ShapedLoop>()

    fun update(deltaTime: Float) {
        for(loop in loops.toTypedArray()) {
            loop.update(deltaTime)
        }
    }

    fun addLoop(loop: ShapedLoop) {
        loop.create()
        loops.add(loop)
    }

    fun removeLoop(loop: ShapedLoop) {
        loop.destroy()
        loops.remove(loop)
    }

}