package worlds.gregs.hestia.artemis.event


interface ExtendedEventDispatchStrategy {

    /**
     * Dispatch event to registered listeners.
     */
    fun register(listener: ExtendedEventListener)

}