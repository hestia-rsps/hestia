package worlds.gregs.hestia.core.display.update.logic.sync.player.stages

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.SyncStage.Companion.REMOVE

class RemovePlayerSync : SyncStage {

    var movement: SyncStage? = null

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(1, 0)//Is block update required?
            writeBits(2, REMOVE)//Movement type
            movement?.encode(this) ?: writeBits(1, 0)
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(RemovePlayerSync::class.java)

        fun create(movement: SyncStage?): RemovePlayerSync {
            val obj = pool.obtain()
            obj.movement = movement
            return obj
        }
    }

}