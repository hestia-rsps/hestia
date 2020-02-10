package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WorldMap
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WorldMapClose

class WorldMapCloseHandler : MessageHandlerSystem<WorldMapClose>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var interfaces: Interfaces

    override fun handle(entityId: Int, message: WorldMapClose) {
        interfaces.closeInterface(entityId, WorldMap)
    }

}