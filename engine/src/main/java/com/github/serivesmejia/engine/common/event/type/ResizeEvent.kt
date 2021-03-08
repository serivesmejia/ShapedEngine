package com.github.serivesmejia.engine.common.event.type

data class ResizeEvent(val newWidth: Int,
                       val newHeight: Int) : ShapedEvent