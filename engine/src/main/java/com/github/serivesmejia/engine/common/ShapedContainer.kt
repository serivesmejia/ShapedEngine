package com.github.serivesmejia.engine.common

/**
 * Abstract class for any sort of component that can hold ShapedComponent children
 */
@Suppress("UNCHECKED_CAST")
abstract class ShapedContainer<C : HierarchyShapedComponent<C>> {

    val children get() = internalChildren.clone() as ArrayList<C>

    private val internalChildren = ArrayList<C>()

    /**
     * Adds a child to this container
     * @param child the child to add
     * @throws IllegalArgumentException if the children has already a parent
     */
    fun addChild(child: C) {
        if(child.parent != null) {
            throw IllegalArgumentException("Child already has a parent")
        }

        child.parent = this
        internalChildren.add(child)
    }

    /**
     * Removes a child from this container
     * @param child the child to remove
     * @throws IllegalArgumentException if the children is not contained here
     */
    fun removeChild(child: C) {
        if(child.parent != this) {
            throw IllegalArgumentException("Child doesn't belong here")
        }

        child.parent = null
        internalChildren.remove(child)
    }

}