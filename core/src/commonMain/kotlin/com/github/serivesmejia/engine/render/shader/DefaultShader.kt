package com.github.serivesmejia.engine.render.shader

/**
 * The engine's default vertex shader
 * @see ShapedShaderSource
 */
object DefaultVertexShader : ShapedShaderSource("""
    
#version 400 core

layout(location = 0) in vec3 position;

out vec3 colour;

void main(void) {
    gl_Position = vec4(position, 1.0);
    colour = vec3(position.x + 0.5, 1.0, position.y + 0.5);
}

""")

/**
 * The engine's default fragment shader
 * @see ShapedShaderSource
 */
object DefaultFragmentShader : ShapedShaderSource("""
    
#version 400 core

in vec3 colour;

layout(location = 0) out vec4 out_Color;

void main(void) {
    out_Color = vec4(colour, 1.0);
}

""")