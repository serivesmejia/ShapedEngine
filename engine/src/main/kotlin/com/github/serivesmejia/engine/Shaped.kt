package com.github.serivesmejia.engine

object Shaped {

    var hasCreatedEngine = false
        internal set

    var engineThread: Thread? = null
        internal set

    var deltaTime = 0.0f
        internal set

    fun end() = engineThread?.interrupt()

}