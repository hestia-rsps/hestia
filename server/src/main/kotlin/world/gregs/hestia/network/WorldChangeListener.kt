package world.gregs.hestia.network

interface WorldChangeListener {
    fun init(id: Int)

    fun disconnect(id: Int)
}