package worlds.gregs.hestia.game.update

import world.gregs.hestia.core.network.packet.PacketBuilder

/**
 * A stage in entity index synchronization
 * Sent in the client updating packets
 */
interface SyncStage {

    /**
     * Encodes the stage data into the packet
     * @param builder The packet to encode the data into
     */
    fun encode(builder: PacketBuilder)

    /**
     * Frees the object to the pool
     */
    open fun free() {
    }

    companion object {

        /**
         * Sync viewport change id's
         */
        const val ADD = 0
        const val REMOVE = 0
        const val UPDATE = 0
        const val WALKING = 1
        const val HEIGHT = 1
        const val RUNNING = 2
        const val REGION = 2
        const val MOVE = 3
    }

}