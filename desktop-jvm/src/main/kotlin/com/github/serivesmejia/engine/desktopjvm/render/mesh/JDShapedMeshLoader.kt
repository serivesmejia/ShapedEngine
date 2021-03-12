package com.github.serivesmejia.engine.desktopjvm.render.mesh

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import java.nio.FloatBuffer
import java.nio.IntBuffer

object JDShapedMeshLoader {

    private val vaos = mutableListOf<Int>()
    private val vbos = mutableListOf<Int>()

    private fun createFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun createIntBuffer(data: IntArray): IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun storeData(attribute: Int, dimensions: Int, data: FloatArray) {
        val vbo = glGenBuffers()
        vbos.add(vbo)

        glBindBuffer(GL_ARRAY_BUFFER, vbo)

        val buffer = createFloatBuffer(data)

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(attribute, dimensions, GL_FLOAT, false,0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private fun bindIndices(data: IntArray) {
        val vbo = glGenBuffers()
        vbos.add(vbo)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
        val buffer = createIntBuffer(data)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
    }

    fun createMesh(positions: FloatArray, indices: IntArray): JDShapedMesh {
        val vao = genVAO()

        storeData(0, 3, positions)
        bindIndices(indices)
        glBindVertexArray(0)

        return JDShapedMesh(vao, indices.size)
    }

   private fun genVAO(): Int {
        val vao = glGenVertexArrays()
        vaos.add(vao)
        glBindVertexArray(vao)

        return vao
    }

}