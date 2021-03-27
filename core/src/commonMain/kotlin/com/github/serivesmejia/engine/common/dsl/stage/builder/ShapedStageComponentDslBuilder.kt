package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.common.dsl.ShapedDslBuilder
import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.timer.ShapedTimer
import com.github.serivesmejia.engine.stage.ShapedStageComponent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

abstract class ShapedStageComponentDslBuilder<T : ShapedStageComponent<*>>(val component: T) : ShapedDslBuilder<T> {

    private val objectBuilders = ArrayList<ShapedObjectDslBuilder>()

    /**
     * Adds a ShapedObject to this component
     * @param obj the object to add
     */
    abstract fun addChild(obj: ShapedObject)

    /**
     * Removes a ShapedObject from this component
     * @param obj the object to add
     */
    abstract fun removeChild(obj: ShapedObject)

    fun addChild(obj: ShapedObject, block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) {
        objectBuilders.add(ShapedObjectDslBuilder(obj, block))
        addChild(obj)
    }

    fun shapedObject(block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) {
        val obj = ShapedObject()
        objectBuilders.add(ShapedObjectDslBuilder(obj, block))

        addChild(obj)
    }

    inline fun <reified T : ShapedEvent> on(noinline block: (T) -> Unit) = component.on(block)

    fun timeout(timeoutSecs: Double, block: (ShapedTimer) -> Unit) =
        component.timeout(timeoutSecs, block)

    fun interval(timeoutSecs: Double, block: (ShapedTimer) -> Unit) =
        component.interval(timeoutSecs, block)

    operator fun ShapedObject.unaryPlus() = this@ShapedStageComponentDslBuilder.addChild(this)

    operator fun ShapedObject.unaryMinus() = this@ShapedStageComponentDslBuilder.removeChild(this)

    internal fun buildObjects() = objectBuilders.forEach { it.build() }

}