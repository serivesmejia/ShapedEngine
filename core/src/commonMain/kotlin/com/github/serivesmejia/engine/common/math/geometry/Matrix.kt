package com.github.serivesmejia.engine.common.math.geometry

// TODO target 1.5 api version so this can be a sealed interface
interface Matrix<T: Number> {
    var buffer: List<T>
}
