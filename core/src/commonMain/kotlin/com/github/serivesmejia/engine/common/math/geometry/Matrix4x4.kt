package com.github.serivesmejia.engine.common.math.geometry

/**
 * [a, b, c, d]
 * [e, f, g, h]
 * [i, j, k, l]
 * [m, n, o, p]
 */
data class Matrix4x4(
    private var a: Float,
    private var b: Float,
    private var c: Float,
    private var d: Float,
    private var e: Float,
    private var f: Float,
    private var g: Float,
    private var h: Float,
    private var i: Float,
    private var j: Float,
    private var k: Float,
    private var l: Float,
    private var m: Float,
    private var n: Float,
    private var o: Float,
    private var p: Float,
) : StaticMatrix<Float> {
    override var buffer: List<Float>
        get() = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, o, p)
        set(value) {
            a = value[ 0]; b = value[ 1]; c = value[ 2]; d = value[ 3]
            e = value[ 4]; f = value[ 5]; g = value[ 6]; h = value[ 7]
            i = value[ 8]; j = value[ 9]; k = value[10]; l = value[11]
            m = value[12]; n = value[13]; o = value[14]; p = value[15]
        }

    override val shape: MatrixShape = 4 by 4

    companion object
}
