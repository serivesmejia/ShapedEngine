package com.github.serivesmejia.engine.common.modular

/**
 * Represents a module that can hold other modules
 * Used for engine modularity.
 *
 * @param T the type of this ShapedModular
 */
abstract class ShapedModular<T : ShapedModular<T>> : ShapedModule<T> {

    /**
     * Get the list of modules currently on this modular
     */
    val modules: Array<ShapedModule<T>>
        get() = internalModules.keys.toTypedArray()

    private val internalModules = HashMap<ShapedModule<T>, Boolean>()

    /**
     * Call update to all the modules
     * @param deltaTime the deltaTime to be passed to the modules update function
     */
    internal fun updateModules(deltaTime: Float) {
        for((module, created) in internalModules.entries) {
            if(!created) createModule(module)
            module.update(deltaTime)
        }
    }

    /**
     * Calls create() in all of the current
     * modules that haven't been created
     */
    internal fun createModules() {
        for((module, created) in internalModules.entries) {
            if(!created) createModule(module)
        }
    }

    private fun createModule(module: ShapedModule<T>) {
        module.create()
        internalModules[module] = true
    }

    /**
     * Destroys all modules contained by this modular
     */
    internal fun destroyModules() {
        for(module in internalModules.keys) {
            module.destroy()
        }
    }

    /**
     * Adds a module to this modular
     * @param module a module to add to this modular
     */
    fun addModule(module: ShapedModule<T>) {
        if(!internalModules.containsKey(module)) {
            internalModules[module] = false
        }
    }

    /**
     * Removes a module from this modular
     * @param module the module to remove from this modular
     */
    fun removeModule(module: ShapedModule<T>) {
        if(internalModules.containsKey(module)) {
            module.destroy()
            internalModules.remove(module)
        }
    }

}