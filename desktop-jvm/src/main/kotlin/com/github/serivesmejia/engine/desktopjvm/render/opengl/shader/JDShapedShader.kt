package com.github.serivesmejia.engine.desktopjvm.render.opengl.shader

import com.github.serivesmejia.engine.render.opengl.shader.ShapedShader
import org.lwjgl.opengl.GL20.*

class JDShapedShader(programID: Int, vertexShaderID: Int, fragmentShaderID: Int)
    : ShapedShader(programID, vertexShaderID, fragmentShaderID) {

    override fun begin() {
        glUseProgram(programID)
    }

    override fun end() {
        glUseProgram(0)
    }

    override fun destroy() {
        end()

        glDetachShader(programID, vertexShaderID)
        glDetachShader(programID, fragmentShaderID)

        glDeleteShader(vertexShaderID)
        glDeleteShader(fragmentShaderID)

        glDeleteProgram(programID)
    }

    override fun bindAttribute(variableName: String, attribute: Int) {
        glBindAttribLocation(programID, attribute, variableName)
    }

}