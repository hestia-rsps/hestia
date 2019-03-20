package worlds.gregs.hestia.game.update.sync

import world.gregs.hestia.core.network.codec.packet.PacketBuilder

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

    companion object {

        /**
         * Sync viewport change id's
         */
        internal const val ADD = 0
        internal const val REMOVE = 0
        internal const val UPDATE = 0
        internal const val WALKING = 1
        internal const val HEIGHT = 1
        internal const val RUNNING = 2
        internal const val REGION = 2
        internal const val MOVE = 3
    }

}