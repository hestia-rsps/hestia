package worlds.gregs.hestia.core.display.update.logic.sync.npc.stages

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.update.SyncStage

class NpcSizeStage : SyncStage {

    var npcCount: Int = -1

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(8, npcCount)
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(NpcSizeStage::class.java)

        fun create(npcCount: Int): NpcSizeStage {
            val obj = pool.obtain()
            obj.npcCount = npcCount
            return obj
        }
    }

}