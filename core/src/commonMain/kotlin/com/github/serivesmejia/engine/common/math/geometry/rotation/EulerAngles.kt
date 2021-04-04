package com.github.serivesmejia.engine.common.math.geometry.rotation

import com.github.serivesmejia.engine.common.math.toDegrees
import com.github.serivesmejia.engine.common.math.toRadians

data class EulerAngles(val pitch: Float,
                       val yaw: Float,
                       val roll: Float) {

    operator fun plus(other: EulerAngles) = EulerAngles(
        pitch + other.pitch,
        yaw + other.yaw,
        roll + other.roll
    )

    fun toDegrees() = EulerAngles(
        pitch.toDegrees().toFloat(),
        yaw.toDegrees().toFloat(),
        roll.toDegrees().toFloat()
    )

    fun toRadians() = EulerAngles(
        pitch.toRadians().toFloat(),
        yaw.toRadians().toFloat(),
        roll.toRadians().toFloat()
    )

    companion object
}