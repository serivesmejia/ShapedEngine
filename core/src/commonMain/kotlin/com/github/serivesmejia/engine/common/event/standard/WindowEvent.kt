package com.github.serivesmejia.engine.common.event.standard

import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.math.geometry.position.Vector2

data class WindowMoveEvent(val newPosition: Vector2) : ShapedEvent

data class WindowResizeEvent(val newSize: Size2) : ShapedEvent