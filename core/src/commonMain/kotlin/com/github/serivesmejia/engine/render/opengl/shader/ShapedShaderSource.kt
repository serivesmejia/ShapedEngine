package com.github.serivesmejia.engine.render.opengl.shader

/**
 * Holds the source code of an OpenGL
 * shader, both vertex and fragment.
 *
 * @property sourceCode the source code for this shader
 *
 * @see DefaultVertexShader
 * @see DefaultFragmentShader
 */
abstract class ShapedShaderSource(val sourceCode: String)