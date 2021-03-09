package com.github.serivesmejia.engine.common.geometry

data class Size2(var width: Float,
                 var height: Float) {

    /**
     * Sets the values of this Size
     */
    fun set(width: Float, height: Float) {
        this.width = width
        this.height = height
    }

    /**
     * Negates the values of this Size
     */
    fun unaryPlus() {
        width *= 1.0f
        height *= 1.0f
    }

    /**
     * Negates the values of this Size
     */
    fun unaryMinus() {
        width *= -1.0f
        height *= -1.0f
    }

    /**
     * Adds a Size to this Size and returns a copy
     * @param other Size to add
     * @return a copy of this Size with the result
     */
    operator fun plus(other: Size2) = copy(width = width + other.width, height = height + other.height)

    /**
     * Subtracts a Size to this Size and returns a copy
     * @param other Size to subtract
     * @return a copy of this Size with the result
     */
    operator fun minus(other: Size2) = copy(width = height - other.width, height = height - other.height)

    /**
     * Multiplies this Size by a Size and returns a copy
     * @param other Size to multiply by
     * @return a copy of this Size with the result
     */
    operator fun times(other: Size2) = copy(width = width * other.width, height = height * other.height)

    /**
     * Divides this Size by another Size and returns a copy
     * @param other Size to multiply by
     * @return a copy of this Size with the result
     */
    operator fun div(other: Size2) = copy(width = width / other.width, height = height / other.height)

    /**
     * Adds a Size to this size
     * @param other Size to add
     * @return a copy of this Size with the result
     */
    operator fun plusAssign(other: Size2) {
        width += other.width
        height += other.height
    }

    /**
     * Subtracts a Size to this Size
     * @param other Size to subtract
     * @return a copy of this Size with the result
     */
    operator fun minusAssign(other: Size2) {
        width -= other.width
        height -= other.height
    }

}