package com.github.serivesmejia.engine.desktopjvm

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.modular.ModulePriority
import com.github.serivesmejia.engine.desktopjvm.render.JDShapedRenderer
import com.github.serivesmejia.engine.desktopjvm.render.JDShapedWindow
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

object DesktopLauncher {

    val engine = ShapedEngine()
    val window = JDShapedWindow()

    fun launch() {
        engine.addModule(window, ModulePriority.HIGH)
        engine.addModule(JDShapedRenderer(engine, window), ModulePriority.LOW)

        engine.create().start { glfwWindowShouldClose(window.ptr) }
    }

}

fun main() = DesktopLauncher.launch()