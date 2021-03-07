package com.github.serivesmejia.engine.common

interface HierarchyShapedComponent<P : HierarchyShapedComponent<P>> : ShapedComponent {
    var parent: P?
}