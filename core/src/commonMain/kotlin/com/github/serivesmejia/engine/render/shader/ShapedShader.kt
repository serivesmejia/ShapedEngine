package com.github.serivesmejia.engine.render.shader

/**
 * Represents an OpenGL shader.
 *
 * Creation of shaders is platform-specific,
 * therefore, they need to be created with a
 * ShapedShaderLoader.
 *
 * @param programID the ID of the shader program
 * @param vertexShaderID the ID of the vertex shader
 * @param fragmentShaderID the ID of the fragment shader
 */
abstract class ShapedShader(val programID: Int, val vertexShaderID: Int, val fragmentShaderID: Int) {

    /**
     * Begins applying this shader
     * Everything following this call
     * will be rendered using this shader
     */
    abstract fun begin()

    /**
     * Ends applying this shader
     * Should follow a begin() call,
     * everything following this
     * call won't use any shader
     */
    abstract fun end()

    /**
     * Destroys this shader's program
     * and both vertex and fragment shaders.
     */
    abstract fun destroy()

    /**
     * Binds an attribute to be passed to
     * the shader.
     *
     * @param variableName the name of the variable declared on the shader to set
     * @param attribute the value to set
     */
    abstract fun bindAttribute(variableName: String, attribute: Int)

}