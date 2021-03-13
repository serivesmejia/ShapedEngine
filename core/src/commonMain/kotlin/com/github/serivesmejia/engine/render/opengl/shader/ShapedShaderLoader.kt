package com.github.serivesmejia.engine.render.opengl.shader

/**
 * Loads a GLSL shader, platform-specific.
 */
abstract class ShapedShaderLoader {

    /**
     * Loads a shader from a string source code
     *
     * @param vertexShader the source code of the vertex shader
     * @param fragShader the source code of the frag shader
     * @return the loaded ShapedShader from the source shaders strings
     */
    abstract fun loadShader(vertexShader: String, fragShader: String): ShapedShader

    /**
     * Loads a shader from ShapedShaderSource objects
     *
     * @param vertexShaderSource the ShapedShaderSource for vertex
     * @param fragShaderSource the ShapedShaderSource for fragment
     * @return the loaded ShapedShader from the source shaders strings
     */
    fun loadShader(vertexShaderSource: ShapedShaderSource, fragShaderSource: ShapedShaderSource) =
        loadShader(vertexShaderSource.sourceCode, fragShaderSource.sourceCode)

}