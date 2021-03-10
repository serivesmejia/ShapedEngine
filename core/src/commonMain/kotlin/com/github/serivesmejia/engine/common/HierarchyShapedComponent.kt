package com.github.serivesmejia.engine.common

/**
 * ShapedComponent to represent a parent-child hierarchy.
 * Can be added to a "ShapedContainer"
 * @param P the parent ShapedContainer of this hierarchy
 */
interface HierarchyShapedComponent<P : HierarchyShapedComponent<P>> : ShapedComponent {
    var parent: ShapedContainer<P>?
}