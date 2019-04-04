package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.game.events.ButtonClick
import worlds.gregs.hestia.network.client.decoders.messages.WidgetOption

class WidgetOptionHandler : MessageHandlerSystem<WidgetOption>() {

    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WidgetOption) {
        val (hash, fromSlot, toSlot, option) = message
        val interfaceId = hash shr 16
        val componentId = hash - (interfaceId shl 16)
        es.dispatch(ButtonClick(entityId, hash, option))
    }

}