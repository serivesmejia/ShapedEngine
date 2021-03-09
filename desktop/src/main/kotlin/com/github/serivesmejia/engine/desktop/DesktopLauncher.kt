package com.github.serivesmejia.engine.desktop

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.desktop.render.ShapedRenderLoop
import com.github.serivesmejia.engine.desktop.render.ShapedDesktopWindow
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

object DesktopLauncher {
    val engine = ShapedEngine()
    val window = ShapedDesktopWindow()

    fun launch() {
        engine.addModule(window)
        engine.addModule(ShapedRenderLoop(engine, window))
        engine.create()

        while(!glfwWindowShouldClose(window.ptr) && !Thread.currentThread().isInterrupted)
            engine.update()

        engine.destroy()
    }
}

fun main() = DesktopLauncher.launch()