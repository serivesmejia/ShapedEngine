package com.github.serivesmejia.engine.common.modular

import java.util.*
import kotlin.collections.HashMap

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

    private val internalModules = HashMap<ShapedModule<T>, Pair<ModulePriority, Boolean>>()

    /**
     * Call update to all the modules
     * @param deltaTime the deltaTime to be passed to the modules update function
     */
    internal fun updateModules(deltaTime: Float) {
        for((module, data) in sortedModules) {
            if(!data.second) createModule(module)
            module.update(deltaTime)
        }
    }

    /**
     * Calls create() in all of the current
     * modules that haven't been created
     */
    internal fun createModules() {
        for((module, data) in sortedModules) {
            if(!data.second) createModule(module)
        }
    }

    private fun createModule(module: ShapedModule<T>) {
        module.create()
        val pair = internalModules[module]
        internalModules[module] = pair!!.copy(second = true)
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
    fun addModule(module: ShapedModule<T>, priority: ModulePriority = ModulePriority.MEDIUM) {
        if(!internalModules.containsKey(module)) {
            internalModules[module] = Pair(priority, false)
            onModuleAdd(module)
        }
    }

    /**
     * Sorts the modules by their ModulePriority
     */
    val sortedModules: List<Pair<ShapedModule<T>, Pair<ModulePriority, Boolean>>>
        get() = internalModules.toList().sortedBy { (_, value) -> value.first.priority }

    /**
     * Open function called when a module is added
     * Can be used to perform any check or save any state
     */
    internal open fun onModuleAdd(module: ShapedModule<T>) {}

    /**
     * Removes a module from this modular
     * @param module the module to remove from this modular
     */
    fun removeModule(module: ShapedModule<T>) {
        if(internalModules.containsKey(module)) {
            module.destroy()
            internalModules.remove(module)
            onModuleRemove(module)
        }
    }

    /**
     * Open function called when a module is removed
     * Can be used to perform any check or save any state
     */
    internal open fun onModuleRemove(module: ShapedModule<T>) {}

}

enum class ModulePriority(val priority: Int) {
    HIGH(-1), MEDIUM(0), LOW(1)
}