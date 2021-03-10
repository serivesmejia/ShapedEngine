package com.github.serivesmejia.engine.util

/**
 * Multiplatform utility class for representing time
 * units and performing conversions among them.
 */
enum class TimeUnit(val fromSeconds: Double, val toSeconds: Double) {
    NANOSECONDS(1e+9, 1e-9),
    MICROSECONDS(1e+6, 1e-6),
    MILLISECONDS(1000.0, 0.001),
    SECONDS(1.0, 1.0),
    MINUTES(0.0166667, 60.0),
    HOURS(0.000277778, 3600.0),
    DAYS(1.1574e-5, 86400.0);

    /**
     * Converts the value from this unit to the specified unit
     * @param value the value to convert
     * @param unit the unit to convert to
     */
    fun convert(value: Long, unit: TimeUnit): Long {
        if(this == unit) return value
        return ((value * toSeconds) * unit.fromSeconds).toLong()
    }
}