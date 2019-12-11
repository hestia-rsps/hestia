package worlds.gregs.hestia.artemis.event

import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener


interface ExtendedEventDispatchStrategy {

    /**
     * Dispatch event to registered listeners.
     */
    fun register(eventListener: ArtemisEventListener)

}