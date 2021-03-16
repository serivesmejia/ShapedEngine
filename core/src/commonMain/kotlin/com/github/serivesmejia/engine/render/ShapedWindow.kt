package com.github.serivesmejia.engine.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.math.geometry.Rectangle2
import com.github.serivesmejia.engine.common.math.geometry.Vector2
import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.common.modular.ShapedModule

interface ShapedWindow : ShapedModule<ShapedEngine> {

    val isVisible: Boolean
    val isFocused: Boolean

    var title: String

    val windowRectangle: Rectangle2

    var position: Vector2
    var size: Size2

    val projectionMatrix: FloatArray

    fun show(): ShapedWindow
    fun hide(): ShapedWindow
    fun center(): ShapedWindow

}