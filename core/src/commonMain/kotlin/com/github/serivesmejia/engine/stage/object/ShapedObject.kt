package com.github.serivesmejia.engine.stage.`object`

import com.github.serivesmejia.engine.stage.ShapedStage
import com.github.serivesmejia.engine.stage.ShapedStageComponent
import com.github.serivesmejia.engine.stage.behavior.ShapedBehavior
import com.github.serivesmejia.engine.stage.behavior.ShapedBehaviorManager
import com.github.serivesmejia.engine.stage.behavior.common.Transform2Behavior
import kotlin.reflect.KClass

open class ShapedObject: ShapedStageComponent<ShapedObject>() {

    private lateinit var behaviorManager: ShapedBehaviorManager

    var isGlobal = false
        internal set

    val transform2 = Transform2Behavior()

    /**
     * Initializes this object.
     * User shouldn't manually call this function
     */
    override fun create(): ShapedObject {
        parentStage = null //invalidate cached parent state

        behaviorManager = ShapedBehaviorManager(this)
        behaviorManager.create()

        + transform2

        init()
        return this
    }

    /**
     * Called once, when this stage is about to be used
     */
    open fun init() {}

    override fun internalUpdate(deltaTime: Float) {
        super.internalUpdate(deltaTime)

        if(::behaviorManager.isInitialized)
            behaviorManager.update(deltaTime)

        //if we're global, we have our own, independent timerManager
        //therefore, we need to update here manually
        if(isGlobal) timerManager.update()
    }

    /**
     * Destroys this object and its children
     * It is not recommended to manually call this method, it
     * will be called by the parent Stage once it determines
     * that it is no longer necessary for any reason.
     */
    override fun destroy(): ShapedObject {
        parent = null

        behaviorManager.destroy()
        dispose()
        return this
    }

    /**
     * Called when destroying.
     * Can be used to perform any operation that frees memory
     * or destroys other (untracked) objects when this object
     * is destroyed.
     */
    open fun dispose() {}

    var parentStage: ShapedStage? = null
        private set
        get() {
            //if our "field" cache is not null, return it
            if(field != null) return field

            if(parent is ShapedStage) { //our parent is a stage!
                //store into field variable for caching
                field = parent as ShapedStage
            } else {
                //if our current parent is a StageComponent
                if(parent is ShapedStageComponent<*>) {
                    //get its parent
                    var currParent = (parent as ShapedStageComponent<*>).parent

                    //linear bottom-to-top scan to find the stage
                    while(currParent != null) {
                        if(currParent is ShapedStage) { //we found a stage!
                            field = currParent
                            break
                        } else if(currParent is ShapedStageComponent<*>) { //if curr parent is still a stage component
                            //didn't found a stage. set curr parent to the parent of current
                            //to continue searching in next iteration
                            currParent = currParent.parent
                        } else {
                            //we found something that doesn't belong to this hierarchy!
                            //break the loop here since there's nothing we can do
                            break
                        }
                    }
                }
            }

            return field
        }

    operator fun ShapedBehavior.unaryPlus() = addBehavior(this)
    operator fun ShapedBehavior.unaryMinus() = removeBehavior(this)

    fun addBehavior(behavior: ShapedBehavior) = behaviorManager.addBehavior(behavior)

    fun removeBehavior(behavior: ShapedBehavior) = behaviorManager.removeBehavior(behavior)

    fun <B : ShapedBehavior> removeBehavior(behaviorClass: KClass<B>) =
        behaviorManager.removeBehavior(behaviorClass)

    inline fun <reified B : ShapedBehavior> removeBehavior() = removeBehavior(B::class)

    fun <B : ShapedBehavior> getBehavior(clazz: KClass<out B>) = behaviorManager.getBehavior(clazz)

    inline fun <reified B : ShapedBehavior> getBehavior() =
        getBehavior(B::class) ?: throw NoSuchElementException("Behavior ${B::class} is not part of ${this::class}")

}