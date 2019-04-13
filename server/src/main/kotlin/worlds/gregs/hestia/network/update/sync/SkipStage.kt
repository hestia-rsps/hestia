package worlds.gregs.hestia.network.update.sync

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.artemis.ConcurrentObjectPool

/**
 * Encodes a number of idle entities to skip
 */
class SkipStage : SyncStage {

    var skip: Int = 0

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 0)//No update needed
            when {
                skip == 0 -> {
                    writeBits(2, 0)
                }
                skip < 32 -> {
                    writeBits(2, 1)
                    writeBits(5, skip)
                }
                skip < 256 -> {
                    writeBits(2, 2)
                    writeBits(8, skip)
                }
                skip < 2048 -> {
                    writeBits(2, 3)
                    writeBits(11, skip)
                }
            }
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(SkipStage::class.java)

        fun create(skip: Int): SkipStage {
            val obj = pool.obtain()
            obj.skip = skip
            return obj
        }
    }

}