package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.network.update.sync.UpdateBlockStage

interface Update : Message {
    val stages: List<SyncStage>
    val blocks: List<UpdateBlockStage>

    fun free()

    fun addStage(stage: SyncStage)

    fun addBlock(block: UpdateBlockStage)

}