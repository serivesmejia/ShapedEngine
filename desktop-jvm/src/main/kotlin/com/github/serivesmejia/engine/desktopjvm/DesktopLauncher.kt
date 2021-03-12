package com.github.serivesmejia.engine.desktopjvm

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.modular.ModulePriority
import com.github.serivesmejia.engine.desktopjvm.render.JDShapedRenderer
import com.github.serivesmejia.engine.desktopjvm.render.JDShapedWindow
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

object DesktopLauncher {

    val window = JDShapedWindow()

    fun launch() {
        ShapedEngine.addModule(window, ModulePriority.HIGH)
        ShapedEngine.addModule(JDShapedRenderer(ShapedEngine, window), ModulePriority.LOW)

        ShapedEngine.create().start { glfwWindowShouldClose(window.ptr) }
    }

}

fun main() = DesktopLauncher.launch()