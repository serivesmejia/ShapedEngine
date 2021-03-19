package com.github.serivesmejia.engine.common.modular

import com.github.serivesmejia.engine.common.math.Range1
import kotlin.collections.HashMap
import kotlin.reflect.KClass

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

    private val requirements = mutableMapOf<KClass<out ShapedModule<T>>, Pair<Range1<Int>, ((ShapedModule<T>) -> Boolean)?>>()
    private var hasValidRequirements = false

    private val internalModules = HashMap<ShapedModule<T>, Pair<ModulePriority, Boolean>>()

    /**
     * Call update to all the modules
     * @param deltaTime the deltaTime to be passed to the modules update function
     */
    internal fun updateModules(deltaTime: Float) {
        if(!hasValidRequirements) checkRequirements()

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
        checkRequirements()
        for((module, data) in sortedModules) {
            if(!data.second) createModule(module)
        }
    }

    private fun createModule(module: ShapedModule<T>) {
        module.create()

        //get the (ModulePriority, created) pair of this module
        val pair = internalModules[module]
        //tell that we created this module already
        internalModules[module] = pair!!.copy(second = true)

        cachedSortedModules = null //need to invalidate sorted caches since we changed the "created" boolean
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

            cachedSortedModules = null //invalidate sorted caches
            hasValidRequirements = false //to revalidate requirements
        }
    }

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

            cachedSortedModules = null //invalidate sorted caches
            hasValidRequirements = false //to revalidate requirements
        }
    }

    /**
     * Open function called when a module is removed
     * Can be used to perform any check or save any state
     */
    internal open fun onModuleRemove(module: ShapedModule<T>) {}

    /**
     * Check the requirements for this module and
     * throw an exception if they're not complied
     */
    internal fun checkRequirements() {
        //iterate through all the requirements added by user
        for((requirement, rangeAndValidator) in requirements) {
            var requiresAmount = 0

            val validator = rangeAndValidator.second

            for(module in modules) { //iterate through all the modules
                //found a requirement! by comparing classes or using user's validator if it isn't null
                if(module::class == requirement || (validator != null && validator(module)))
                    requiresAmount++
            }

            val range = rangeAndValidator.first

            //check if we comply the min and max amount of requirements
            if(range.isInRange(requiresAmount)) {
                continue //move on to the next
            } else {
                //the requirement is not complied :(
                throw IllegalStateException(
                    "Modular ${this::class.simpleName} requires $range module(s) of type ${requirement.simpleName}"
                )
            }
        }

        hasValidRequirements = true //if we reached to this point it means all requirements are complied!
    }

    /**
     * Adds a module requirement from the modular, with a validator
     * @param moduleKClass the module requirement to add
     * @param requireMin the minimum requirement for the module
     * @param requireMax the maximum requirement for the module
     * @param validator the block to be called to validate this requirement
     */
    internal fun addRequirement(moduleKClass: KClass<out ShapedModule<T>>,
                                requireMin: Int,
                                requireMax: Int = Int.MAX_VALUE,
                                validator: ((ShapedModule<T>) -> Boolean)?) {
        requirements[moduleKClass] = Pair(Range1(requireMin, requireMax), validator)
    }

    /**
     * Adds a module requirement from the modular with a validator, using a better syntax.
     * @param M the module to add
     * @param requireMin the minimum requirement for the module
     * @param requireMax the maximum requirement for the module
     */
    internal inline fun <reified M : ShapedModule<T>> addRequirement(requireMin: Int,
                                                                     requireMax: Int = Int.MAX_VALUE,
                                                                     noinline validator: ((ShapedModule<T>) -> Boolean)?)
    = addRequirement(M::class, requireMin, requireMax, validator)


    /**
     * Adds a module requirement from the modular
     * @param moduleKClass the module requirement to add
     * @param requireMin the minimum requirement for the module
     * @param requireMax the maximum requirement for the module
     */
    internal fun addRequirement(moduleKClass: KClass<out ShapedModule<T>>,
                                requireMin: Int,
                                requireMax: Int = Int.MAX_VALUE)
    = addRequirement(moduleKClass, requireMin, requireMax, null)

    /**
     * Adds a module requirement from the modular, with a better syntax.
     * @param M the module to add
     * @param requireMin the minimum requirement for the module
     * @param requireMax the maximum requirement for the module
     */
    internal inline fun <reified M : ShapedModule<T>> addRequirement(requireMin: Int,
                                                                     requireMax: Int = Int.MAX_VALUE) {
        addRequirement(M::class, requireMin, requireMax)
    }

    /**
     * Removes a module requirement from the modular
     * @param moduleKClass the class of the module to remove
     */
    internal fun removeRequirement(moduleKClass: KClass<out ShapedModule<T>>) {
        requirements.remove(moduleKClass)
    }

    /**
     * Removes a module requirement from the modular, with a better syntax.
     * @param M the module to remove
     */
    internal inline fun <reified M : ShapedModule<T>> addRequirement() = removeRequirement(M::class)

    /**
     * Caching last sorted list so that we don't have to resort every time
     */
    private var cachedSortedModules: List<Pair<ShapedModule<T>, Pair<ModulePriority, Boolean>>>? = null

    /**
     * Sorts the modules by their ModulePriority
     */
    val sortedModules: List<Pair<ShapedModule<T>, Pair<ModulePriority, Boolean>>>
        get() {
            if(cachedSortedModules == null) { //check if we don't have cached
                //recreate cached list if we don't have one
                cachedSortedModules = internalModules.toList().sortedBy { (_, value) -> value.first.priority }
            }
            return cachedSortedModules!! //return cached
        }

}

enum class ModulePriority(val priority: Int) {
    HIGH(-1), MEDIUM(0), LOW(1)
}