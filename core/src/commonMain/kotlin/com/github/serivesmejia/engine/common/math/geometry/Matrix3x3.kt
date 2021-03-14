package com.github.serivesmejia.engine.common.math.geometry

/**
 * [a, b, c]
 * [d, e, f]
 * [g, h, i]
 */
data class Matrix3x3(
    private var a: Float,
    private var b: Float,
    private var c: Float,
    private var d: Float,
    private var e: Float,
    private var f: Float,
    private var g: Float,
    private var h: Float,
    private var i: Float,
) : StaticMatrix<Float> {
    override var buffer: List<Float>
        get() = listOf(a, b, c, d, e, f, g, h, i)
        set(value) {
            a = value[0]; b = value[1]; c = value[2]
            d = value[3]; e = value[4]; f = value[5]
            g = value[6]; h = value[7]; i = value[8]
        }

    override val shape: MatrixShape = 3 by 3
}
