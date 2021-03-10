package com.github.serivesmejia.engine.common.system

interface SystemTimer {
    val currentTimeMillis: Long
    val nanoTime: Long
}

object PlaceholderSystemTimer : SystemTimer {
    override val currentTimeMillis: Long
        get() = 0L
    override val nanoTime: Long
        get() = 0L
}