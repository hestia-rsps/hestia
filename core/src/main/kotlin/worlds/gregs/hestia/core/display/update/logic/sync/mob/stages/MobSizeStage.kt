package worlds.gregs.hestia.core.display.update.logic.sync.mob.stages

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.update.SyncStage

class MobSizeStage : SyncStage {

    var mobCount: Int = -1

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(8, mobCount)
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(MobSizeStage::class.java)

        fun create(mobCount: Int): MobSizeStage {
            val obj = pool.obtain()
            obj.mobCount = mobCount
            return obj
        }
    }

}