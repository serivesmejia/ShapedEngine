package com.github.serivesmejia.engine.common.math.geometry

/**
 * [a, b]
 * [c, d]
 */
data class Matrix2x2(
    private var a: Float,
    private var b: Float,
    private var c: Float,
    private var d: Float,
) : StaticMatrix<Float> {
    override var buffer: List<Float>
        get() = listOf(a, b, c, d)
        set(value) {
            a = value[0]; b = value[1]
            c = value[2]; d = value[3]
        }

    override val shape: MatrixShape = 2 by 2

    companion object
}
