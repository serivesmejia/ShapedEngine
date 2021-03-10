package com.github.serivesmejia.engine.util

import com.github.serivesmejia.engine.Shaped

/**
 * Utility class timer for measuring ElapsedTime
 * @property resolution the resolution to be used by this ElapsedTime
 * @property nsStartTime the start time of this ElapsedTime in nanoseconds
 */
class ElapsedTime(private val resolution: Resolution = Resolution.SECOND_IN_NANO,
                  private var nsStartTime: Long = nsNow) {

    /**
     * Returns the current time on the clock used by the timer
     *
     * @param unit the time unit in which the current time will be returned
     * @return the current time on the clock used by the timer
     */
     fun now(unit: TimeUnit): Long {
        return unit.convert(nsNow, TimeUnit.NANOSECONDS)
    }

    /**
     * Returns, in resolution dependent units, the time at which this timer was last reset
     */
    val startTime get() = nsStartTime / resolution.value

    /**
     * Returns in nanos the time at which this timer was last reset
     */
    val startTimeNanos get() = nsStartTime

    /**
     * Returns the time, in resolution dependent units, that has elapsed since the last reset
     */
    val time get() = nanos / resolution.value

    /**
     * Returns the time that has elapsed since the last reset in seconds
     */
    val seconds get() = nanos / SECOND_IN_NANO.toDouble()

    /**
     * Returns the time that has elapsed since the last reset in milliseconds
     */
    val millis get() = seconds * 1000.0

    /**
     * Returns the time that has elapsed since the last reset in nanoseconds
     */
    val nanos get() = (nsNow - nsStartTime).toDouble()

    /**
     * Resets the elapsed time back to zero
     */
    fun reset() {
        nsStartTime = nsNow
    }

    override fun toString() = "$time, ${resolution.name}"

    companion object {
        /**
         * Returns the current nanoTime, precision subject to the system lock
         */
        private val nsNow get() = Shaped.System.nanoTime

        private val SECOND_IN_NANO = 1000000000L
        private val MILLIS_IN_NANO = 1000000L
    }

    /**
     * Represents the resolution to be used for a ElapsedTime
     */
    enum class Resolution(val value: Long) {
        /**
         * Translates nanos to seconds
         */
        SECOND_IN_NANO(ElapsedTime.SECOND_IN_NANO),

        /**
         * Translates nanos to millis
         */
        MILLIS_IN_NANO(ElapsedTime.MILLIS_IN_NANO)
    }

}