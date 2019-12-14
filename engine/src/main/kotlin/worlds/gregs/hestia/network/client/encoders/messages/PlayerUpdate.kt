package worlds.gregs.hestia.network.client.encoders.messages

import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.network.update.sync.Update
import worlds.gregs.hestia.network.update.sync.UpdateBlockStage

class PlayerUpdate : Update {

    override val stages = ArrayList<SyncStage>()
    override val blocks = ArrayList<UpdateBlockStage>()

    override fun free() {
        pool.free(this)
    }

    override fun addStage(stage: SyncStage) {
        stages.add(stage)
    }

    override fun addBlock(block: UpdateBlockStage) {
        blocks.add(block)
    }

    companion object {
        private val pool = ConcurrentObjectPool(PlayerUpdate::class.java)

        fun create(): PlayerUpdate {
            val obj = pool.obtain()
            obj.blocks.clear()
            obj.stages.clear()
            return obj
        }
    }
}