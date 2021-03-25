package com.github.serivesmejia.engine.common.dsl.stage.builder

import com.github.serivesmejia.engine.common.dsl.ShapedDslBuilder
import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.timer.ShapedTimer
import com.github.serivesmejia.engine.stage.ShapedStageComponent
import com.github.serivesmejia.engine.stage.`object`.ShapedObject

abstract class ShapedStageComponentDslBuilder<T : ShapedStageComponent<*>>(val component: T) : ShapedDslBuilder<T> {

    private val objectBuilders = ArrayList<ShapedObjectDslBuilder>()

    /**
     * Adds a ShapedObject
     */
    abstract fun add(obj: ShapedObject)
    abstract fun remove(obj: ShapedObject)

    fun add(obj: ShapedObject, block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) {
        objectBuilders.add(ShapedObjectDslBuilder(obj, block))
        add(obj)
    }

    operator fun ShapedObject.unaryPlus() {
        add(this)
    }

    fun stageObject(block: ShapedObjectDslBuilder.(ShapedObject) -> Unit) {
        val obj = ShapedObject()
        objectBuilders.add(ShapedObjectDslBuilder(obj, block))

        add(obj)
    }

    inline fun <reified T : ShapedEvent> on(noinline block: (T) -> Unit) = component.on(block)

    fun timeout(timeoutSecs: Double, block: (ShapedTimer) -> Unit) =
        component.timeout(timeoutSecs, block)

    fun interval(timeoutSecs: Double, block: (ShapedTimer) -> Unit) =
        component.interval(timeoutSecs, block)

    internal fun buildObjects() = objectBuilders.forEach { it.build() }

}