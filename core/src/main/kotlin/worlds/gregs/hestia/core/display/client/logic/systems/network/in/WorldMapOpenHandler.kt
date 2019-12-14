package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WorldMapOpen

class WorldMapOpenHandler : MessageHandlerSystem<WorldMapOpen>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WorldMapOpen) {
        /*GameServer.eventSystem.send(entityId, WidgetWindowsPane(755, 0))
        val x = 3087
        val y = 3497
        val posHash = x shl 14 or y
        GameServer.eventSystem.send(entityId, ConfigGlobal(622, posHash))
        GameServer.eventSystem.send(entityId, ConfigGlobal(674, posHash))*/
    }

}