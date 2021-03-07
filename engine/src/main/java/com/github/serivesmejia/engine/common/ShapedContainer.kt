package com.github.serivesmejia.engine.common

/**
 * Abstract class for any sort of component that can hold ShapedComponent children
 */
@Suppress("UNCHECKED_CAST")
abstract class ShapedContainer<T : HierarchyShapedComponent<T>> {

    /**
     * Typed array of the current children of this container
     */
    val children: Array<T>
        get() = internalChildren.toArray() as Array<T>

    private val internalChildren = ArrayList<T>()

    /**
     * Adds a child to this container
     * @param child the child to add
     * @throws IllegalArgumentException if the children has already a parent
     */
    fun addChild(child: T) {
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
    fun removeChild(child: T) {
        if(child.parent != this) {
            throw IllegalArgumentException("Child doesn't belong here")
        }

        child.parent = null
        internalChildren.remove(child)
    }

}