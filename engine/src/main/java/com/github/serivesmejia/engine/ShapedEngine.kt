package com.github.serivesmejia.engine

import com.github.serivesmejia.engine.common.*
import com.github.serivesmejia.engine.common.loop.ShapedLoopManager
import com.github.serivesmejia.engine.render.ShapedRenderLoop
import com.github.serivesmejia.engine.render.desktop.ShapedWindow
import com.github.serivesmejia.engine.stage.ShapedStageManager
import com.github.serivesmejia.engine.util.ElapsedTime
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL

class ShapedEngine : ShapedComponent {

    lateinit var window: ShapedWindow
        private set

    lateinit var stageManager: ShapedStageManager
        private set

    val loopManager = ShapedLoopManager()

    private val deltaTimer = ElapsedTime()

    /**
     * Initializes and runs the engine, blocking.
     * Exits until the glfw window is closed
     */
    override fun create(): ShapedEngine {
        //not a precisely good idea to have more than one engine...
        if(Shaped.hasCreatedEngine)
            throw IllegalStateException("Can't have more than one engine running per JVM")

        //tell globally that we have one engine running
        Shaped.hasCreatedEngine = true
        //tell globally on which thread we're running
        Shaped.engineThread = Thread.currentThread()

        //create the window
        window = ShapedWindow().create().center()

        stageManager = ShapedStageManager().create()

        //add the render loop to the list of loops to run
        loopManager.addLoop(ShapedRenderLoop(window, stageManager))

        //essential line
        GL.createCapabilities()
        //begin looping now
        beginEngineLoop()

        return this
    }

    private fun beginEngineLoop() {
        //restart the timer before beginning the loop
        deltaTimer.reset()

        //loop until window requested to close or thread interrupted
        while(!glfwWindowShouldClose(window.ptr) && !Thread.currentThread().isInterrupted) {
            Shaped.deltaTime = deltaTimer.seconds.toFloat() //calculate delta time
            deltaTimer.reset() //reset back to zero

            //run all loops
            loopManager.update(Shaped.deltaTime)
        }

        //destroy if the loop exits
        destroy()
    }

    /**
     * Destroys this ShapedEngine
     */
    override fun destroy(): ShapedEngine {
        loopManager.destroy()
        window.destroy()
        Shaped.end()
        return this
    }

}