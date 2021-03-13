package com.github.serivesmejia.engine.render.opengl.mesh

interface ShapedMeshBuilder {
    fun createMesh(positions: FloatArray, uvs: FloatArray, indices: IntArray): ShapedMesh
}