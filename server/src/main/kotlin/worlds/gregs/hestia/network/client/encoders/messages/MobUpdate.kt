package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.network.update.sync.UpdateBlockStage

data class MobUpdate(val stages: List<SyncStage>, val blocks: List<UpdateBlockStage>) : Message