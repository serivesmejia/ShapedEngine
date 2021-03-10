package com.github.serivesmejia.engine.render

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.ShapedComponent
import com.github.serivesmejia.engine.common.geometry.Rectangle2
import com.github.serivesmejia.engine.common.geometry.Vector2
import com.github.serivesmejia.engine.common.geometry.Size2
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
        get() = TODO("Not yet implemented")
    override val isFocused: Boolean
        get() = TODO("Not yet implemented")
    override var title: String
        get() = TODO("Not yet implemented")
        set(value) {}

    override val windowRectangle: Rectangle2
        get() = TODO("Not yet implemented")
    override var position: Vector2
        get() = TODO("Not yet implemented")
        set(value) {}
    override var size: Size2
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun show(): ShapedWindow { TODO("Not yet implemented") }
    override fun hide(): ShapedWindow { TODO("Not yet implemented") }
    override fun center(): ShapedWindow { TODO("Not yet implemented") }

    override fun update(deltaTime: Float) { TODO("Not yet implemented") }
    override fun create(): ShapedComponent { TODO("Not yet implemented") }
    override fun destroy(): ShapedComponent { TODO("Not yet implemented") }

}