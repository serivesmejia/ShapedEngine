package com.github.serivesmejia.engine.desktop

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.modular.ModulePriority
import com.github.serivesmejia.engine.desktop.render.ShapedRenderLoop
import com.github.serivesmejia.engine.desktop.render.ShapedDesktopWindow
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

object DesktopLauncher {

    val engine = ShapedEngine()
    val window = ShapedDesktopWindow()

    fun launch() {
        engine.addModule(window, ModulePriority.HIGH)
        engine.addModule(ShapedRenderLoop(engine, window), ModulePriority.LOW)

        engine.create().start { glfwWindowShouldClose(window.ptr) }
    }

}

fun main() = DesktopLauncher.launch()