package com.github.serivesmejia.engine.desktopjvm.render.opengl.shader

import com.github.serivesmejia.engine.render.opengl.shader.ShapedShader
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*

class JDShapedShader(programID: Int, vertexShaderID: Int, fragmentShaderID: Int)
    : ShapedShader(programID, vertexShaderID, fragmentShaderID) {

    override val locationProjection by lazy { getUniformLocation("projection") }

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

    override fun getUniformLocation(uniformName: String): Int = glGetUniformLocation(programID, uniformName)

    override fun loadMatrix(location: Int, value: FloatArray) {
        val matrix = BufferUtils.createFloatBuffer(value.size)
        matrix.put(value)

        glUniformMatrix4fv(location, false, matrix)
    }

}