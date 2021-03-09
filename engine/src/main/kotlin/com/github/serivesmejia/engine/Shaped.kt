package com.github.serivesmejia.engine

object Shaped {

    var hasCreatedEngine = false
        internal set

    var engineThread: Thread? = null
        internal set

    var deltaTime = 0.0f
        internal set

    var fps = 0
        internal set

    object Graphics {
        var width = 0
            internal set
        var height = 0
            internal set
    }

    fun end() = engineThread?.interrupt()

}