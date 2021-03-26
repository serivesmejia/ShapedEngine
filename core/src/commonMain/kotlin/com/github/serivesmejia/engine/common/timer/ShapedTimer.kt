package com.github.serivesmejia.engine.common.timer

import com.github.serivesmejia.engine.common.ShapedComponent
import com.github.serivesmejia.engine.util.ElapsedTime

/**
 * A ShapedTimer is a simple common utility in the engine for
 * scheduling a non-blocking timeout callback via an update()
 * mechanism.
 *
 * @property timeSecs the time in seconds to execute the timeout block
 * @property isInterval whether this timer will be destroyed (stopped) after the first timeout or it will continue running an undefined amount of time
 * @property timeoutBlock the block to call after the time runs out
 *
 * @see ShapedTimerManager
 */
class ShapedTimer(private val timeSecs: Double,
                  private val isInterval: Boolean = false,
                  private val timeoutBlock: (ShapedTimer) -> Unit) : ShapedComponent {

    private val timer = ElapsedTime()

    /**
     * Tells whether this timer has been destroyed or not
     */
    var destroyed = false
        private set

    /**
     * Tells whether this timer has been created or not
     */
    var created = false
        private set

    /**
     * Creates this timer and starts counting the time
     */
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

    /**
     * Destroys this timer, meaning that
     * the timeoutBlock wont be called anymore.
     */
    override fun destroy(): ShapedTimer {
        destroyed = true
        return this
    }

}