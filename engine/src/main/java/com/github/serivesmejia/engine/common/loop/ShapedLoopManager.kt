package com.github.serivesmejia.engine.common.loop

import com.github.serivesmejia.engine.common.ShapedComponent

open class ShapedLoopManager : ShapedLoop {

    private val loops = ArrayList<ShapedLoop>()

    override fun create(): ShapedLoopManager {
        return this
    }

    override fun update(deltaTime: Float) {
        for(loop in loops.toTypedArray()) {
            loop.update(deltaTime)
        }
    }

    override fun destroy(): ShapedLoopManager {
        for(loop in loops.toTypedArray()) { loop.destroy() }
        return this
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