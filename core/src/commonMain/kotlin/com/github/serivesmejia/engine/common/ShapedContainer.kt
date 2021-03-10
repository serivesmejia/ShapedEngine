package com.github.serivesmejia.engine.common

import com.github.serivesmejia.engine.common.extension.clipUpperZero

/**
 * Abstract class for any sort of component that can hold ShapedComponent children
 */
@Suppress("UNCHECKED_CAST")
abstract class ShapedContainer<C : HierarchyShapedComponent<C>> {

    /**
     * Get the children of this container as an ArrayList
     * Note that the returned ArrayList is a clone of the internal
     * one, sp any addition or deletion won't affect the container.
     * Use addChild() or removeChild() to do so.
     */
    val children get() = internalChildren.subList(0, (internalChildren.size - 1).clipUpperZero)

    private val internalChildren = ArrayList<C>()

    /**
     * Adds a child to this container
     * @param child the child to add
     * @throws IllegalArgumentException if the children has already a parent
     */
    open fun addChild(child: C) {
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
    open fun removeChild(child: C) {
        if(child.parent != this) {
            throw IllegalArgumentException("Child doesn't belong here")
        }

        child.parent = null
        internalChildren.remove(child)
    }

}