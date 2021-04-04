package com.github.serivesmejia.engine.render.opengl.shader

/**
 * The engine's default vertex shader
 * @see ShapedShaderSource
 */
object DefaultVertexShader : ShapedShaderSource("""
    
#version 450 core

in vec3 position;
in vec2 uvs;

out vec2 pass_uvs;

void main(void) {
    gl_Position = vec4(position, 1.0);
    pass_uvs = uvs;
}

""") {
    override fun bindAttributes(shader: ShapedShader) {
        shader.bindAttribute("position", 0)
        shader.bindAttribute("texCoords", 1)
    }
}

/**
 * The engine's default fragment shader
 * @see ShapedShaderSource
 */
object DefaultFragmentShader : ShapedShaderSource("""
    
#version 450 core

in vec2 pass_texCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void) {
    out_Color = texture(textureSampler, pass_texCoords);
}

""")