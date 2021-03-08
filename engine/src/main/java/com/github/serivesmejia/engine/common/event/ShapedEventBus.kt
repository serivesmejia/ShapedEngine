package com.github.serivesmejia.engine.common.event

import com.github.serivesmejia.engine.common.event.type.ShapedEvent
import java.lang.reflect.Method
import kotlin.reflect.KClass

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
 * Events are fired to all subscribers with the fire() method
 */
class ShapedEventBus {

    private val subscribedMethods = mutableMapOf<Class<out ShapedEvent>, MutableList<Pair<Any, Method>>>()

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
    fun register(obj: Any) {
        val methods = obj::class.java.declaredMethods

        //iterate through all methods of the passed object
        for(method in methods) {
            val annotations = method.annotations

            //iterate through all annotations of the current method
            //searching for the "Subscribe" annotation
            for(annotation in annotations) {
                //found the annotation!
                if(annotation is Subscribe) {
                    //smart cast the annotation to Subscribe
                    //and get the event class specified in it
                    val eventClass = annotation.eventClass.java

                    //add to the list of abject and method if the
                    //event class has been registered as a key before
                    if(subscribedMethods.containsKey(eventClass)) {
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
    }

    /**
     * Clears all the registered subscribers
     */
    fun clear() = subscribedMethods.clear()

}

/**
 * Subscribe a function/method to a specific event
 * @property eventClass the class of the event to subscribe
 */
annotation class Subscribe(val eventClass: KClass<out ShapedEvent>)