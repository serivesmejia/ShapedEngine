package com.github.serivesmejia.engine.common.timer

import com.github.serivesmejia.engine.common.ShapedComponent
import com.github.serivesmejia.engine.util.ElapsedTime

class ShapedTimer(private val timeSecs: Double,
                  private val isInterval: Boolean = false,
                  private val timeoutBlock: (ShapedTimer) -> Unit) : ShapedComponent {

    private val timer = ElapsedTime()

    var destroyed = false
        private set

    var created = false
        private set

    override fun create(): ShapedTimer {
        if(!created) {
            timer.reset()
            created = true
        }
        return this
    }

    /**
     * Updates this timer and executes
     * the timeoutBlock if the time runs out
     */
    fun update() {
        if(!destroyed && timer.seconds >= timeSecs) {
            timeoutBlock(this) //call the onTimeout block
            if(!isInterval) destroy() //if this timer isn't interval, destroy

            timer.reset()
        }
    }

    override fun destroy(): ShapedTimer {
        destroyed = true
        return this
    }


}