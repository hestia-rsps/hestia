package worlds.gregs.hestia.network.update.sync

import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.game.update.SyncStage

interface Update : Message {
    val stages: List<SyncStage>
    val blocks: List<UpdateBlockStage>

    fun free()

    fun addStage(stage: SyncStage)

    fun addBlock(block: UpdateBlockStage)

}