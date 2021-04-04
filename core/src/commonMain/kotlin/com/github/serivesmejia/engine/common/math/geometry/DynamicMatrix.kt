package com.github.serivesmejia.engine.common.math.geometry

interface DynamicMatrix<T : Number> : Matrix<T> {
    var shape: MatrixShape
}