package com.github.serivesmejia.engine.common.event

import com.github.serivesmejia.engine.common.event.wrapper.ShapedEventWrapper
import java.lang.reflect.Method
import kotlin.reflect.KClass

/**
 * Handles events via a "@Subscribe" mechanism.
 *
 * A sample use would be:
 * ```
 * @Subscribe
 * fun onResize(evt: ResizeEvent) {
 *  val w = evt.newWidth
 *  val h = evt.newHeight
 *  //...
 * }
 * ````
 *
 * Note that objects with @Subscribe methods need to be manually
 * registered via the register() method.
 * Events are fired to all subscribers, using the fire() method
 */
class ShapedEventBus {

    private val subscribedMethods = mutableMapOf<Class<*>, MutableList<Pair<Any, Method>>>()

    /**
     * Fires all the subscribed methods with the specified event
     * @event the event to fire (and to be passed to the subscribed methods)
     */
    fun fire(event: ShapedEvent) {
        val eventClass = event::class.java

        subscribedMethods[eventClass]?.forEach { //for each subscribed method to the event
            try {
                //try invoking the subscribed method with a "event" parameter
                it.second.invoke(it.first, event)
            } catch(ex: NoSuchMethodException) { //need different arguments
                try {
                    //try invoking the same method without any arguments
                    it.second.invoke(it.first)
                } catch(ex: NoSuchMethodException) { /* give up, nothing else we can really do..*/ }
            }
        }
    }

    /**
     * Registers all @Subscribe-d methods
     * in a specified object.
     *
     * @param obj the object to register the methods from
     */
    @Suppress("UNCHECKED_CAST")
    fun register(obj: Any) {
        val methods = obj::class.java.declaredMethods

        //iterate through all methods of the passed object
        for (method in methods) {
            val annotations = method.annotations

            //iterate through all annotations of the current method
            //searching for the "Subscribe" annotation
            for (annotation in annotations) {
                //found the annotation!
                if (annotation is Subscribe) {
                    //we only take methods with one parameter
                    if(method.parameterTypes.size != 1)
                        continue

                    //get the type of the event from the first (and only) parameter of this method
                    val eventClass = try {
                        //try casting the type of this parameter to a "extends" ShapedEvent class
                        method.parameterTypes[0]
                    } catch(ex: Exception) {
                        continue //we can't use this method, we require specifically 1 parameter...
                    }

                    //add to the list of abject and method if the
                    //event class has been registered as a key before
                    if (subscribedMethods.containsKey(eventClass)) {
                        subscribedMethods[eventClass]!!.add(Pair(obj, method))
                    } else {
                        //create a new list of methods for this event class type
                        val listOfMethods = mutableListOf<Pair<Any, Method>>()
                        listOfMethods.add(Pair(obj, method)) //add the pair of object and value to the new list
                        subscribedMethods[eventClass] = listOfMethods //add the list to the master map
                    }
                }
            }
        }

        //call the register method if object is a registrator
        if(obj is ShapedEventRegistrator) {
            obj.register(this)
        }
    }

    /**
     * Unregisters all subscribers from a specific object
     * @param unregObj the object to unregister all the subscribers from
     */
    fun unregister(unregObj: Any) {
        //search for obj list in every event type
        for(list in subscribedMethods.values) {
            //iterate the obj list as an independent typed array
            for(pair in list.toTypedArray()) {
                //if the obj of the current iteration equals the object to unregister
                if(pair.first === unregObj) {
                    list.remove(pair) //remove

                    //call unregister if object to unregister is an EventRegistrator
                    if(unregObj is ShapedEventRegistrator) unregObj.unregister(this)
                }
            }
        }
    }

    /**
     * Wraps this ShapedEventBus around the specified wrapper
     * Useful for stuff like mapping GLFW events to ShapedEvents
     */
    fun wrap(wrapper: ShapedEventWrapper) = wrapper.wrap(this)

    /**
     * Clears all the registered subscribers
     */
    fun clear() = subscribedMethods.clear()

}

/**
 * Subscribe a function/metod to a specific event (JVM only)
 */
@Target(AnnotationTarget.FUNCTION)
annotation class Subscribe