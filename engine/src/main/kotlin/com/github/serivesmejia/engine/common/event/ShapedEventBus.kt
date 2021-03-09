package com.github.serivesmejia.engine.common.event

import com.github.serivesmejia.engine.common.event.wrapper.ShapedEventWrapper
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions

/**
 * Handles events via a "@Subscribe(eventClass)" mechanism.
 *
 * A sample use would be:
 * ```
 * @Subscribe(ResizeEvent::class)
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

    private val subscribedMethods = mutableMapOf<KClass<out ShapedEvent>, MutableList<Pair<Any, KFunction<*>>>>()

    /**
     * Fires all the subscribed methods with the specified event
     * @event the event to fire (and to be passed to the subscribed methods)
     */
    fun fire(event: ShapedEvent) {
        val eventClass = event::class

        subscribedMethods[eventClass]?.forEach { //for each subscribed method to the event
            try {
                //try invoking the subscribed method with a "event" parameter
                it.second.call(it.first, event)
            } catch(ex: IllegalArgumentException) { //need different arguments
                try {
                    //try invoking the same method without any arguments
                    it.second.call(it.first)
                } catch(ex: IllegalArgumentException) { /* give up, nothing else we can really do..*/ }
            }
        }
    }

    /**
     * Registers all @Subscribe-d methods
     * in a specified object.
     *
     * @param obj the object to register the methods from
     */
    fun register(obj: Any) {
        val functions = obj::class.declaredFunctions

        //iterate through all methods of the passed object
        for (function in functions) {
            val annotations = function.annotations

            //iterate through all annotations of the current method
            //searching for the "Subscribe" annotation
            for (annotation in annotations) {
                //found the annotation!
                if (annotation is Subscribe) {
                    //smart cast the annotation to Subscribe
                    //and get the event class specified in it
                    val eventClass = annotation.eventClass

                    //add to the list of abject and method if the
                    //event class has been registered as a key before
                    if (subscribedMethods.containsKey(eventClass)) {
                        subscribedMethods[eventClass]!!.add(Pair(obj, function))
                    } else {
                        //create a new list of methods for this event class type
                        val listOfMethods = mutableListOf<Pair<Any, KFunction<*>>>()
                        listOfMethods.add(Pair(obj, function)) //add the pair of object and value to the new list
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
 * Subscribe a function/method to a specific event
 * @property eventClass the class of the event to subscribe
 */
@Target(AnnotationTarget.FUNCTION)
annotation class Subscribe(val eventClass: KClass<out ShapedEvent>)