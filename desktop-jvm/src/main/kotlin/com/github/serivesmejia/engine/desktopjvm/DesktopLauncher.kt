package com.github.serivesmejia.engine.desktopjvm

import com.github.serivesmejia.engine.ShapedEngine
import com.github.serivesmejia.engine.common.modular.ModulePriority
import com.github.serivesmejia.engine.desktopjvm.render.JvmShapedDesktopRenderer
import com.github.serivesmejia.engine.desktopjvm.render.JvmShapedDesktopWindow
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

object DesktopLauncher {

    val engine = ShapedEngine
    val window = JvmShapedDesktopWindow()

    fun launch() {
        engine.addModule(window, ModulePriority.HIGH)
        engine.addModule(JvmShapedDesktopRenderer, ModulePriority.MEDIUM)
        engine.addModule(JvmShapedDesktopLoop(engine, window), ModulePriority.MEDIUM)

        engine.create().start { glfwWindowShouldClose(window.ptr) }
    }

}

fun main() = DesktopLauncher.launch()