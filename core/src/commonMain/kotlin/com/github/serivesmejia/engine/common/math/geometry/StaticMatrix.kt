package com.github.serivesmejia.engine.common.math.geometry

interface StaticMatrix<T : Number> : Matrix<T> {
    val shape: MatrixShape
}