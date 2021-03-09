package com.github.serivesmejia.engine.common.event.general

import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.geometry.Size2
import com.github.serivesmejia.engine.common.geometry.Vector2

data class WindowMoveEvent(val windowPtr: Long,
                           val newPosition: Vector2) : ShapedEvent

data class WindowResizeEvent(val windowPtr: Long,
                             val newSize: Size2) : ShapedEvent