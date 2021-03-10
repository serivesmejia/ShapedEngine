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

    fun show(): ShapedWindow
    fun hide(): ShapedWindow
    fun center(): ShapedWindow

}

object PlaceholderWindow : ShapedWindow {

    override val isVisible: Boolean
        get() = false
    override val isFocused: Boolean
        get() = false
    override var title: String
        get() = ""
        set(value) {}

    override val windowRectangle: Rectangle2
        get() = Rectangle2(position, size)
    override var position: Vector2
        get() = Vector2(0f, 0f)
        set(value) {}
    override var size: Size2
        get() = Size2(0f, 0f)
        set(value) {}

    override fun show() = this
    override fun hide() = this
    override fun center() = this

    override fun update(deltaTime: Float) { }
    override fun create() = this
    override fun destroy() = this

}