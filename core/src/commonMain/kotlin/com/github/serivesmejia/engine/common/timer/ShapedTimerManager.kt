package com.github.serivesmejia.engine.common.timer

class ShapedTimerManager {

    val timers get() = internalTimers.toTypedArray()

    private val internalTimers = mutableListOf<ShapedTimer>()

    /**
     * Updates, creates, executes, and/or destroys
     * all the timers in this manager.
     *
     * Should be executed in a fast enough loop.
     */
    fun update() {
        for(timer in timers) {
            if(!timer.created) timer.create()
            timer.update()

            if(timer.destroyed) //remove the timer if it was destroyed for any reason
                internalTimers.remove(timer)
        }
    }

    /**
     * Adds a block to be executed ONCE after a certain timeout
     * @param seconds the time in seconds to execute this block once it timeouts
     * @param block the block to execute after the timeout
     */
    fun timeout(seconds: Double, block: (ShapedTimer) -> Unit): ShapedTimer {
        val timer = ShapedTimer(seconds, timeoutBlock = block)

        internalTimers.add(timer)
        return timer
    }

    /**
     * Adds a block to be executed REPETITIVELY after a certain timeout
     * @param seconds the time in seconds to execute this block once it timeouts
     * @param block the block to execute REPETITIVELY after each timeout
     */
    fun interval(seconds: Double, block: (ShapedTimer) -> Unit): ShapedTimer {
        val timer = ShapedTimer(seconds, true, block)

        internalTimers.add(timer)
        return timer
    }

    /**
     * Destroys all the timers in this manager
     */
    fun destroyAll() {
        for(timer in timers) {
            timer.destroy()
            internalTimers.remove(timer)
        }
    }

}