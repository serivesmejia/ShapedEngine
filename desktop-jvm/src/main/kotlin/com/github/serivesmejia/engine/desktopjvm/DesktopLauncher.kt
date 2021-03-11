package com.github.serivesmejia.engine.desktopjvm

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.modular.ModulePriority
import com.github.serivesmejia.engine.desktopjvm.render.JvmShapedDesktopRenderer
import com.github.serivesmejia.engine.desktopjvm.render.JvmShapedDesktopWindow
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

object DesktopLauncher {

    val window = JvmShapedDesktopWindow()

    fun launch() {
        ShapedEngine.addModule(window, ModulePriority.HIGH)
        ShapedEngine.addModule(JvmShapedDesktopRenderer(ShapedEngine, window), ModulePriority.HIGH)

        ShapedEngine.create().start { glfwWindowShouldClose(window.ptr) }
    }

}

fun main() = DesktopLauncher.launch()