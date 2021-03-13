package com.github.serivesmejia.engine.desktopjvm.render.opengl.shader

import com.github.serivesmejia.engine.render.opengl.shader.ShapedShader
import com.github.serivesmejia.engine.render.opengl.shader.ShapedShaderLoader
import org.lwjgl.opengl.GL20.*

object JDShapedShaderLoader : ShapedShaderLoader() {

    override fun loadShader(vertexShader: String, fragShader: String): ShapedShader {
        val vert = createShader(vertexShader, GL_VERTEX_SHADER)
        val frag = createShader(fragShader, GL_FRAGMENT_SHADER)

        val program = glCreateProgram()

        glAttachShader(program, vert)
        glAttachShader(program, frag)

        glLinkProgram(program)
        glValidateProgram(program)

        return JDShapedShader(program, vert, frag)
    }

    private fun createShader(shader: String, type: Int): Int{
        val shaderID = glCreateShader(type)

        glShaderSource(shaderID, shader)
        glCompileShader(shaderID)

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            println(glGetShaderInfoLog(shaderID, 500))
            throw IllegalArgumentException("Compilation error with shader")
        }

        return shaderID
    }

}