package com.github.serivesmejia.engine.jvm.system

import com.github.serivesmejia.engine.common.system.SystemTimer

object JvmSystemTimer : SystemTimer {
    override val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    override val nanoTime: Long
        get() = System.nanoTime()
}