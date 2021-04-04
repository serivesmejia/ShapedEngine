package com.github.serivesmejia.engine.jvm.event

import com.github.serivesmejia.engine.common.event.ShapedEvent
import com.github.serivesmejia.engine.common.event.subscriber.ShapedEventRegistrator
import com.github.serivesmejia.engine.common.event.subscriber.ShapedEventSubscriber
import com.github.serivesmejia.engine.common.event.subscriber.Subscribe

/**
 * An EventSubscriber for the JVM, registers all events
 * with a @Subscribe annotation in a registered object
 *
 * Example as follows:
 * ```
 * @Subscribe
 * fun onResize(evt: WindowResizeEvent) {
 *   //...
 * }
 * ```
 */
class JvmShapedEventSubscriber : ShapedEventSubscriber() {

    private val objectCallbacks = mutableMapOf<Any, MutableList<(ShapedEvent) -> Unit>>()

    override fun register(obj: Any) {
        //create event callbacks list for this obj
        val eventCallbacks = mutableListOf<(ShapedEvent) -> Unit>()

        //gets the methods of the class from the obj
        val methods = obj::class.java.declaredMethods

        //iterate through all the methods of the object
        for(method in methods) {
            //iterate through the annotations of the method searching for @Subscribe
            for(annotation in method.annotations) {
                //if this annotation is a Subscribe annotation
                if(annotation is Subscribe) {
                    if(method.parameterTypes.size != 1)
                        continue //we need exactly one method parameter

                    //get the first parameter which should be a class
                    val eventClass = method.parameterTypes[0]

                    //create block to call method when event is fired
                    val block: (ShapedEvent) -> Unit = {
                        try {
                            method.invoke(obj, it)
                        } catch(ignored: IllegalArgumentException) { } //if we fail to invoke the method by the arguments, ignore it
                    }
                    //add block to the callbacks list
                    eventCallbacks.add(block)

                    for(eventBus in wrappedEventBuses) { //iterate through all the event buses
                        //add the event class and block to the event bus
                        eventBus.untypedOn(eventClass.kotlin, block)
                    }
                }
            }
        }

        //call register function if the object is a registrator
        if(obj is ShapedEventRegistrator) {
            obj.register(this)
        }

        //add the callbacks, added from the object
        objectCallbacks[obj] = eventCallbacks
    }

    override fun unregister(obj: Any) {
        if(objectCallbacks.containsKey(obj)) { //if we have registered the object
            //iterate through all event buses
            for(eventBus in wrappedEventBuses) {
                //iterate through all the callbacks of this object
                for(block in objectCallbacks[obj]!!) {
                    eventBus.removeCallback(block) //remove the block from the event buss
                }
            }

            //call unregister function if the object is a registrator
            if(obj is ShapedEventRegistrator) {
                obj.unregister(this)
            }
        }
    }

}