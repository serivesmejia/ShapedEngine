package com.github.serivesmejia.engine.desktopjvm.render.opengl.shader

import com.github.serivesmejia.engine.render.opengl.shader.ShapedShader
import com.github.serivesmejia.engine.render.opengl.shader.ShapedShaderLoader
import com.github.serivesmejia.engine.render.opengl.shader.ShapedShaderSource
import org.lwjgl.opengl.GL20.*

object JDShapedShaderLoader : ShapedShaderLoader() {

    override fun loadShader(vertexShaderSource: ShapedShaderSource, fragShaderSource: ShapedShaderSource): ShapedShader {
        val vert = createShader(vertexShaderSource.sourceCode, GL_VERTEX_SHADER)
        val frag = createShader(fragShaderSource.sourceCode, GL_FRAGMENT_SHADER)

        val program = glCreateProgram()
        val shader = JDShapedShader(program, vert, frag)

        glAttachShader(program, vert)
        glAttachShader(program, frag)

        vertexShaderSource.bindAttributes(shader)
        fragShaderSource.bindAttributes(shader)

        glLinkProgram(program)
        glValidateProgram(program)

        return shader
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