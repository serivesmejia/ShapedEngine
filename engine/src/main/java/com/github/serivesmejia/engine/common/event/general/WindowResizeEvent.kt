package com.github.serivesmejia.engine.common.event.general

import com.github.serivesmejia.engine.common.event.ShapedEvent

data class WindowResizeEvent(val windowPtr: Long,
                             val newWidth: Int,
                             val newHeight: Int) : ShapedEvent