package com.github.serivesmejia.engine.common.math.geometry

data class MatrixShape(var rows: Int, var cols: Int)

infix fun Int.by(other: Int) = MatrixShape(this, other)