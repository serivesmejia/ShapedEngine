package com.github.serivesmejia.engine.common.timer

class ShapedTimerManager {

    val timers get() = internalTimers.toTypedArray()

    private val internalTimers = mutableListOf<ShapedTimer>()

    fun update() {
        for(timer in timers) {
            if(!timer.created) timer.create()
            timer.update()

            if(timer.destroyed) //remove the timer if it was destroyed for any reason
                internalTimers.remove(timer)
        }
    }

    fun timeout(seconds: Double, block: (ShapedTimer) -> Unit): ShapedTimer {
        val timer = ShapedTimer(seconds, timeoutBlock = block)

        internalTimers.add(timer)
        return timer
    }

    fun interval(seconds: Double, block: (ShapedTimer) -> Unit): ShapedTimer {
        val timer = ShapedTimer(seconds, true, block)

        internalTimers.add(timer)
        return timer
    }

    fun destroyAll() {
        for(timer in timers) {
            timer.destroy()
            internalTimers.remove(timer)
        }
    }

}