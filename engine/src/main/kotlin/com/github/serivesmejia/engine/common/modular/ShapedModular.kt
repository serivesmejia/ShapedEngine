package com.github.serivesmejia.engine.common.modular

abstract class ShapedModular<T : ShapedModular<T>> : ShapedModule<T> {

    private val modules = HashMap<ShapedModule<T>, Boolean>()

    internal fun updateModules(deltaTime: Float) {
        for((module, created) in modules.entries) {
            if(!created) createModule(module)
            module.update(deltaTime)
        }
    }

    internal fun createModules() {
        for((module, created) in modules.entries) {
            if(!created) createModule(module)
        }
    }

    private fun createModule(module: ShapedModule<T>) {
        module.create()
        modules[module] = true
    }

    internal fun destroyModules() {
        for(module in modules.keys) {
            module.destroy()
        }
    }

    fun addModule(module: ShapedModule<T>) {
        if(!modules.containsKey(module)) {
            modules[module] = false
        }
    }

    fun removeModule(module: ShapedModule<T>) {
        if(modules.containsKey(module)) {
            module.destroy()
            modules.remove(module)
        }
    }

}