package worlds.gregs.hestia.artemis.event

data class ComparableEvent(val priority: Int, val event: Any) : Comparable<ComparableEvent> {

    override fun compareTo(other: ComparableEvent): Int {
        return other.priority - priority
    }
}