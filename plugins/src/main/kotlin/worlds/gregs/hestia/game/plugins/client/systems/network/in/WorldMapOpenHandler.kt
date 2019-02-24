package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.network.game.decoders.messages.WorldMapOpen

class WorldMapOpenHandler : MessageHandlerSystem<WorldMapOpen>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WorldMapOpen) {
    }

}